package com.saiayns.sms.master.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saiayns.sms.master.model.Tenant;


public interface TenantRepository extends JpaRepository<Tenant, UUID>{
	public Optional<Tenant> findById(UUID tenantId);
	
	public Optional<Tenant> findBySubDom(String subDom);
}
