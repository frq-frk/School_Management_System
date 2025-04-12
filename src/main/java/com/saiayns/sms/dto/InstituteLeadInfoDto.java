package com.saiayns.sms.dto;

import com.saiayns.sms.master.model.InstituteLeadInfo;
import com.saiayns.sms.master.model.InstitutionInfo;

public class InstituteLeadInfoDto {

	private String name;

	private String code;
	private String address;
	private String city;
	private String state;
	private String postalCode;

	private String country;

	private String contactPerson;

	private String contactPersonId;

	private String contactEmail;

	private String contactPhone;

	public InstituteLeadInfoDto(String name, String code, String address, String city, String state, String postalCode,
			String country, String contactPerson, String contactPersonId, String contactEmail, String contactPhone) {
		super();
		this.name = name;
		this.code = code;
		this.address = address;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.country = country;
		this.contactPerson = contactPerson;
		this.contactPersonId = contactPersonId;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public InstituteLeadInfo toModel() {
		return new InstituteLeadInfo(name, code, address, city, state, postalCode, country, contactPerson,
				contactPersonId, contactEmail, contactPhone, null);
	}

	public static InstituteLeadInfoDto toDto(InstituteLeadInfo model) {
		return new InstituteLeadInfoDto(model.getName(), model.getCode(), model.getAddress(), model.getCity(),
				model.getState(), model.getPostalCode(), model.getCountry(), model.getContactPerson(),
				model.getContactPersonId(), model.getContactEmail(), model.getContactPhone());

	}
	
	public static InstituteLeadInfoDto toDto(InstitutionInfo model) {
		return new InstituteLeadInfoDto(model.getName(), model.getTenantInfo().getSubDom(), model.getAddress(), model.getCity(),
				model.getState(), model.getPostalCode(), model.getCountry(), model.getContactPerson(),
				model.getContactPersonId(), model.getContactEmail(), model.getContactPhone());

	}
}