package com.saiayns.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saiayns.sms.model.User;
import com.saiayns.sms.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        String token = userService.authenticate(email, password);
        return ResponseEntity.ok(token);
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestParam String email, @RequestParam String password) {
        User user = userService.registerAdmin(email, password);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping("/update-password")
    public ResponseEntity<User> updatePassword(@RequestParam String email, @RequestParam String password, @RequestParam String newPassword){
    	User user = userService.updatePassword(email, password, newPassword);
        return ResponseEntity.ok(user);
    }
}
