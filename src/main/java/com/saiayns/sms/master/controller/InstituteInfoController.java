package com.saiayns.sms.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saiayns.sms.dto.InstituteLeadInfoDto;
import com.saiayns.sms.master.model.InstitutionInfo;
import com.saiayns.sms.master.service.InstituteInfoService;

@RestController
@RequestMapping("/api/institute")
public class InstituteInfoController {

	@Autowired
	InstituteInfoService infoService;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerInstitute(@RequestParam("instituteId") Long instituteId){
		infoService.registerInstitute(instituteId);
		return ResponseEntity.ok("Successfully registered the institute");
	}
	
	@GetMapping("/{leadId}")
	public ResponseEntity<InstituteLeadInfoDto> getLeadInfo(@PathVariable Long leadId) {
	    InstituteLeadInfoDto leadInfoDto = infoService.getInfoWithoutLogo(leadId);
	    
	    if (leadInfoDto == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	    
	    return ResponseEntity.ok(leadInfoDto);
	}
	
	@GetMapping("/logo/{leadId}")
	public ResponseEntity<byte[]> getLeadLogo(@PathVariable Long leadId) {
	    InstitutionInfo leadInfo = infoService.getLogo(leadId);

	    return ResponseEntity.ok()
	            .contentType(MediaType.IMAGE_PNG) // Change MIME type based on stored image type
	            .body(leadInfo.getLogo());
	}
}
