package com.saiayns.sms.master.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="institute_info")
public class InstitutionInfo {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; 

    @OneToOne
    @JoinColumn(name="tenant_sub_dom")
    private Tenant tenantInfo;

    private String address;
    private String city;
    private String state;
    @Column(name="postal_code")
    private String postalCode;
    
    private String country;
    
    @Column(name="contact_person")
    private String contactPerson;
    
    @Column(name="contact_person_id")
    private String contactPersonId;
    
    @Column(name="contact_email")
    private String contactEmail;
    
    @Column(name="contact_phone")
    private String contactPhone;

    @Lob
    @Column(columnDefinition = "LONGBLOB") // Stores image as a BLOB
    private byte[] logo;
    
	public InstitutionInfo() {
		super();
	}

	public InstitutionInfo(String name, Tenant tenant, String address, String city, String state, String postalCode,
			String country, String contactPerson, String contactPersonId, String contactEmail, String contactPhone, byte[] logo) {
		super();
		this.name = name;
		this.tenantInfo = tenant;
		this.address = address;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.country = country;
		this.contactPerson = contactPerson;
		this.contactPersonId = contactPersonId;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
		this.logo = logo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Tenant getTenantInfo() {
		return tenantInfo;
	}

	public void setTenantInfo(Tenant tenantInfo) {
		this.tenantInfo = tenantInfo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	
	public String getContactPersonId() {
		return contactPersonId;
	}

	public void setContactPersonId(String contactPersonId) {
		this.contactPersonId = contactPersonId;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
}
