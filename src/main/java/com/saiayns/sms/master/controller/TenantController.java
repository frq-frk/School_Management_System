package com.saiayns.sms.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saiayns.sms.master.service.TenantService;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {

	@Autowired
	TenantService tenantService;
	
	@GetMapping("/{tenantSubDom}")
	public ResponseEntity<String> getTenandId(@PathVariable("tenantSubDom") String tenantSubDomain){
		return ResponseEntity.ok(String.valueOf(tenantService.getTenantId(tenantSubDomain)));
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> registerTenant(@RequestParam("sub-domain") String subDom){
		tenantService.registerTenant(subDom);
		return ResponseEntity.ok("Successfully registered the tenant with sub domain "+ subDom);
	}
}
