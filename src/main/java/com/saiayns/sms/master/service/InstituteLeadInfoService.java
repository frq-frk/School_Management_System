package com.saiayns.sms.master.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.saiayns.sms.dto.InstituteLeadInfoDto;
import com.saiayns.sms.master.model.InstituteLeadInfo;
import com.saiayns.sms.master.repository.InstituteLeadInfoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InstituteLeadInfoService {

	@Autowired
	InstituteLeadInfoRepository leadInfoRepo;
	
	public InstituteLeadInfo addInfo(InstituteLeadInfoDto leadInfoDto) {
		InstituteLeadInfo leadInfoObj = leadInfoDto.toModel();
		return leadInfoRepo.save(leadInfoObj);
	}
	
	public void updateLogo(Long id, MultipartFile logo) throws IOException {
        InstituteLeadInfo leadInfo = leadInfoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InstituteLeadInfo not found with ID: " + id));

        // Convert MultipartFile to byte array
        leadInfo.setLogo(logo.getBytes());

        // Save updated entity
        leadInfoRepo.save(leadInfo);
    }
	
	public InstituteLeadInfoDto getLeadInfoWithoutLogo(Long leadId) {
	    InstituteLeadInfo leadInfo = leadInfoRepo.findById(leadId)
	            .orElseThrow(() -> new RuntimeException("Lead not found with ID: " + leadId));

	    return InstituteLeadInfoDto.toDto(leadInfo); 
	}
	
	public InstituteLeadInfo getLeadLogo(Long leadId) {
		InstituteLeadInfo leadInfo = leadInfoRepo.findById(leadId)
	            .orElseThrow(() -> new RuntimeException("Lead not found with ID: " + leadId));

	    if (leadInfo.getLogo() == null) {
	        throw new EntityNotFoundException();
	    }
		return leadInfo;
	}


}
