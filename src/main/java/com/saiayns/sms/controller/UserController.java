package com.saiayns.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saiayns.sms.dto.AuthRequestDTO;
import com.saiayns.sms.service.UserService;
import com.saiayns.sms.tenant.model.User;
import com.saiayns.sms.utils.SecurityHelperUtil;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequestDTO loginRequest) {
        String token = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(token);
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody AuthRequestDTO registerRequest) {
        User user = userService.registerAdmin(registerRequest.getEmail(), registerRequest.getPassword());
        return ResponseEntity.ok(user);
    }
    
    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestParam String password, @RequestParam String newPassword){
    	String email = SecurityHelperUtil.getEmailFromSecurityContext();
    	userService.updatePassword(email, password, newPassword);
        return ResponseEntity.ok("Successfully updated the password");
    }
}
