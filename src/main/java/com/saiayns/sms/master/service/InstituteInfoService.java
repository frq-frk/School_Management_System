package com.saiayns.sms.master.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saiayns.sms.dto.InstituteLeadInfoDto;
import com.saiayns.sms.master.model.InstituteLeadInfo;
import com.saiayns.sms.master.model.InstituteLeadInfo.RegistrationStatus;
import com.saiayns.sms.master.model.InstitutionInfo;
import com.saiayns.sms.master.repository.InstituteInfoRepository;
import com.saiayns.sms.master.repository.InstituteLeadInfoRepository;
import com.saiayns.sms.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class InstituteInfoService {
	@Autowired
	TenantService tenantService;
	
	@Autowired
	InstituteLeadInfoRepository instituteLeadInfoRepo;
	
	@Autowired
	InstituteInfoRepository instituteInfoRepo;
	
	@Autowired
	UserService userService;
	
	@Transactional
	public void registerInstitute(Long instituteLeadId) {
        InstituteLeadInfo registration = instituteLeadInfoRepo.findById(instituteLeadId)
                .orElseThrow(() -> new RuntimeException("Institute Registration not found"));

        if (!registration.getStatus().equals(RegistrationStatus.PENDING)) {
            throw new RuntimeException("Institute is already processed");
        }

        tenantService.registerTenant(registration.getCode());

        // Create Approved Institute Entry
        InstitutionInfo instituteInfo = new InstitutionInfo(registration.getName(), tenantService.getTenant(registration.getCode()), registration.getAddress(), registration.getCity(), registration.getState(), registration.getPostalCode(), registration.getCountry(), registration.getContactPerson(), registration.getContactPersonId(), registration.getContactEmail(), registration.getContactPhone(), registration.getLogo());
        instituteInfoRepo.save(instituteInfo);
        // Mark Registration as Approved
        registration.setStatus(RegistrationStatus.APPROVED);
        userService.registerAdmin(registration.getContactEmail(), "TestPassword@123");
        instituteLeadInfoRepo.save(registration);
    }
	
	public InstituteLeadInfoDto getInfoWithoutLogo(Long leadId) {
	    InstitutionInfo leadInfo = instituteInfoRepo.findById(leadId)
	            .orElseThrow(() -> new RuntimeException("Lead not found with ID: " + leadId));

	    return InstituteLeadInfoDto.toDto(leadInfo); 
	}
	
	public InstitutionInfo getLogo(Long leadId) {
		InstitutionInfo leadInfo = instituteInfoRepo.findById(leadId)
	            .orElseThrow(() -> new RuntimeException("Lead not found with ID: " + leadId));

	    if (leadInfo.getLogo() == null) {
	        throw new EntityNotFoundException();
	    }
		return leadInfo;
	}
}
