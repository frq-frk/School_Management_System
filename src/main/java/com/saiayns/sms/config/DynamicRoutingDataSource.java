package com.saiayns.sms.config;

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
        for (Tenant tenant : tenants) {
            addTenantDataSource(tenant);
        }
    }

    public synchronized void addTenantDataSource(Tenant tenant) {
        DataSource tenantDataSource = DataSourceBuilder.create()
                .url(tenant.getDbUrl())
                .username(tenant.getDbUsername())
                .password(tenant.getDbPassword())
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();

        // Update map with a NEW instance
        Map<Object, Object> newTargetDataSources = new ConcurrentHashMap<>(tenantDataSources);
        newTargetDataSources.put(tenant.getTenantId(), tenantDataSource);

        // Apply new map to AbstractRoutingDataSource
        super.setTargetDataSources(newTargetDataSources);
        super.afterPropertiesSet();  // Refresh DataSource
    }
	
    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getTenantId(); // Fetch tenant ID from context
    }
}
