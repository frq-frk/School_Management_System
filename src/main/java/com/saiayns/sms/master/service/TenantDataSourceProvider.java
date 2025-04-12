package com.saiayns.sms.master.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

import com.saiayns.sms.master.model.Tenant;
import com.saiayns.sms.master.repository.TenantRepository;


@Service
public class TenantDataSourceProvider {

    @Autowired
    private TenantRepository tenantRepository;

    private final Map<UUID, DataSource> tenantDataSources = new ConcurrentHashMap<>();

    public DataSource getTenantDataSource(UUID tenantId) {
        return tenantDataSources.computeIfAbsent(tenantId, this::createDataSource);
    }
    
    public Map<Object, Object> getAllTenantDataSources() {
        Map<Object, Object> tenantDataSources = new HashMap<>();
        
        List<Tenant> tenants = tenantRepository.findAll();
        for (Tenant tenant : tenants) {
            DataSource tenantDataSource = createDataSource(tenant.getId());
            tenantDataSources.put(tenant.getId(), tenantDataSource);
        }

        return tenantDataSources;
    }

    private DataSource createDataSource(UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(NoSuchElementException::new);

        return DataSourceBuilder.create()
                .url(tenant.getDbUrl())
                .username(tenant.getDbUsername())
                .password(tenant.getDbPassword())
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    public DataSource getDefaultDataSource() {
        return tenantDataSources.get("master");
    }
}
