package com.saiayns.sms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.saiayns.sms.security.JwtUtil;
import com.saiayns.sms.tenant.model.User;
import com.saiayns.sms.tenant.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public String authenticate(String email, String password) {
        Optional<User> admin = userRepository.findByEmail(email);

        if (admin.isPresent() && passwordEncoder.matches(password, admin.get().getPassword())) {
            return jwtUtil.generateToken(email);
        }
        throw new BadCredentialsException("Invalid credentials");
    }
	
	public User registerAdmin(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }
	
	public User updatePassword(String email, String currentPassword, String updatedPassword) {
		Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && passwordEncoder.matches(currentPassword, user.get().getPassword())) {
        	user.get().setPassword(passwordEncoder.encode(updatedPassword));
        	return userRepository.save(user.get());
        }
        throw new BadCredentialsException("Invalid credentials");
	}
}
