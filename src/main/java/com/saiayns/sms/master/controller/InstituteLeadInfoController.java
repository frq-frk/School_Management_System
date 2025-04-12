package com.saiayns.sms.master.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saiayns.sms.dto.InstituteLeadInfoDto;
import com.saiayns.sms.master.model.InstituteLeadInfo;
import com.saiayns.sms.master.service.InstituteLeadInfoService;


@RestController
@RequestMapping("/api/leadInfo")
public class InstituteLeadInfoController {
	
	@Autowired
	InstituteLeadInfoService leadInfoService;

	@PostMapping(path = "/add")
	public ResponseEntity<String> addLeadInfo(@RequestBody InstituteLeadInfoDto leadInfoDto) {
	    InstituteLeadInfo savedInfo = leadInfoService.addInfo(leadInfoDto);
	    return ResponseEntity.ok("Lead added with ID: " + savedInfo.getId());
	}
	
	@PostMapping(path = "/upload-logo/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadLogo(@PathVariable Long id, @RequestParam("logo") MultipartFile logo) throws IOException {
	    leadInfoService.updateLogo(id, logo);
	    return ResponseEntity.ok("Logo uploaded successfully for ID: " + id);
	}
	
	@GetMapping("/{leadId}")
	public ResponseEntity<InstituteLeadInfoDto> getLeadInfo(@PathVariable Long leadId) {
	    InstituteLeadInfoDto leadInfoDto = leadInfoService.getLeadInfoWithoutLogo(leadId);
	    
	    if (leadInfoDto == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	    
	    return ResponseEntity.ok(leadInfoDto);
	}
	
	@GetMapping("/logo/{leadId}")
	public ResponseEntity<byte[]> getLeadLogo(@PathVariable Long leadId) {
	    InstituteLeadInfo leadInfo = leadInfoService.getLeadLogo(leadId);

	    return ResponseEntity.ok()
	            .contentType(MediaType.IMAGE_PNG) // Change MIME type based on stored image type
	            .body(leadInfo.getLogo());
	}

}
