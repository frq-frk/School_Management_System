package com.saiayns.sms.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saiayns.sms.model.User;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
//	ADMIN Auth Related API's
	
	@PostMapping("/login")
	public String loginMethod(@RequestBody User user) {
		return "This api is under construction";
	}
}
