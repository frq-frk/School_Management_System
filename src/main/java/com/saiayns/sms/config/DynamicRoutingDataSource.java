package com.saiayns.sms.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import com.saiayns.sms.context.TenantContext;
import com.saiayns.sms.master.model.Tenant;
import com.saiayns.sms.master.repository.TenantRepository;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

	private final Map<Object, Object> tenantDataSources = new ConcurrentHashMap<>();

    @Autowired
    @Lazy
    private TenantRepository tenantRepository;  // Fetch tenants from master DB

    private DataSource defaultDataSource;

    public DynamicRoutingDataSource() {
        this.defaultDataSource = createDefaultDataSource();
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(tenantDataSources);
        super.afterPropertiesSet(); // Ensure initialization
    }

    private DataSource createDefaultDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/sms_db") // Change to a valid DB
                .username("devuser")
                .password("Nightwatch@007")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
    
    public void initializeTenants() {
        List<Tenant> tenants = tenantRepository.findAll();
        System.out.println("🔵 Loading Tenants at Startup: " + tenants.size());
        for (Tenant tenant : tenants) {
            addTenantDataSource(tenant);
            System.out.println("Initialized tenant "+tenant.getSubDom()+" with id "+tenant.getId());
        }
        super.initialize();
     // Debug: Check the map after initialization
        System.out.println("🔵 Total Loaded Tenants: " + tenantDataSources.keySet()+" - "+tenantDataSources.values());
    }

    public synchronized void addTenantDataSource(Tenant tenant) {
        DataSource tenantDataSource = DataSourceBuilder.create()
                .url(tenant.getDbUrl())
                .username(tenant.getDbUsername())
                .password(tenant.getDbPassword())
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();

     // Debugging: Test Connection
        try (Connection conn = tenantDataSource.getConnection()) {
            System.out.println("✅ Successfully connected to " + tenant.getDbUrl());
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to " + tenant.getDbUrl());
            e.printStackTrace();
        }
        
        DataSource oldDataSource = (DataSource) tenantDataSources.put(String.valueOf(tenant.getId()), tenantDataSource);
        if (oldDataSource != null) {
            ((HikariDataSource) oldDataSource).close();
        }
        
        tenantDataSources.put(String.valueOf(tenant.getId()), tenantDataSource);

        // Apply new map to AbstractRoutingDataSource
        super.setTargetDataSources(tenantDataSources);
        super.afterPropertiesSet();  // Refresh DataSource
        System.out.println("Added Tenant DB: " + tenant.getId() + " - " + tenant.getDbUrl()); // Debugging
    }
	
    @Override
    protected Object determineCurrentLookupKey() {
    	String tenantId = TenantContext.getTenantId(); 
        System.out.println("Current Tenant ID: " + tenantId); // Debugging
        return tenantId; // Fetch tenant ID from context
    }
}
