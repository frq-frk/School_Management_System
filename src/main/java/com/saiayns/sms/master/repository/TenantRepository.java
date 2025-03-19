package com.saiayns.sms.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saiayns.sms.master.model.Tenant;


public interface TenantRepository extends JpaRepository<Tenant, Long>{
	public Tenant findByTenantId(String tenantId);
}
