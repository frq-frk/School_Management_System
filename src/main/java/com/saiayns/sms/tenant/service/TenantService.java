package com.saiayns.sms.tenant.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Service;

import com.saiayns.sms.config.DynamicRoutingDataSource;
import com.saiayns.sms.master.model.Tenant;
import com.saiayns.sms.master.repository.TenantRepository;

import jakarta.persistence.EntityManagerFactory;

@Service
public class TenantService {
	
	@Autowired
	TenantRepository tenantRepo;
	
	@Autowired
	DynamicRoutingDataSource tenantDataSource;
	
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	public void registerTenant(String subDom) {
        String dbName = "school_" + subDom;
        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;

        // Create Tenant Database
        createDatabase(dbName);

        // Store tenant details in Master DB
        Tenant tenant = new Tenant();
        tenant.setDbUrl(dbUrl);
        tenant.setDbUsername("devuser");
        tenant.setDbPassword("Nightwatch@007");
        tenant.setSubDom(subDom);
        tenantRepo.save(tenant);

        // Dynamically Add to Tenant DataSource
        tenantDataSource.addTenantDataSource(tenant);
        
        initializeTenantDatabase(dbUrl, "devuser", "Nightwatch@007");
    }

    private void createDatabase(String dbName) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "devuser", "Nightwatch@007");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE " + dbName);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create database", e);
        }
    }
    
    private void initializeTenantDatabase(String dbUrl, String username, String password) {
        try {
            // 🔹 Create new DataSource for tenant DB
            LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();
            emfBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            emfBean.setPackagesToScan("com.saiayns.sms.tenant.model"); // 🔹 Scans tenant models
            emfBean.setPersistenceUnitName("tenantPU");

            // 🔹 Set up DataSource for the new tenant
            Map<String, Object> jpaProperties = new HashMap<>();
            jpaProperties.put("hibernate.hbm2ddl.auto", "update"); // ✅ Auto-create tables
            jpaProperties.put("javax.persistence.jdbc.url", dbUrl);
            jpaProperties.put("javax.persistence.jdbc.user", username);
            jpaProperties.put("javax.persistence.jdbc.password", password);
            jpaProperties.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");

            emfBean.setJpaPropertyMap(jpaProperties);
            emfBean.afterPropertiesSet(); // 🔥 Triggers Hibernate schema creation

            System.out.println("✅ Tables created dynamically in tenant DB: " + dbUrl);
        } catch (Exception e) {
            throw new RuntimeException("❌ Hibernate schema generation failed for tenant: " + dbUrl, e);
        }
    }
    
    public UUID getTenantId(String tenantSubDom) {
    	Tenant tenantObj = tenantRepo.findBySubDom(tenantSubDom).orElseThrow(NoSuchElementException::new);
    	return tenantObj.getId();
    }
}
