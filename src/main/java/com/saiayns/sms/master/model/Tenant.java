package com.saiayns.sms.master.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false, name="sub_dom")
    private String subDom;

    @Column(nullable = false, name="db_url")
    private String dbUrl;

    @Column(nullable = false, name="db_username")
    private String dbUsername;

    @Column(nullable = false, name="db_password")
    private String dbPassword;
    
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private TenantStatus status = TenantStatus.ACTIVE; // Active, Pending, Suspended, etc.
//
//    public enum TenantStatus {
//        PENDING, ACTIVE, SUSPENDED, DEACTIVATED
//    }
    
	public String getSubDom() {
		return subDom;
	}

	public void setSubDom(String subDom) {
		this.subDom = subDom;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
}