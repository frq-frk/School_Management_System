package com.saiayns.sms.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saiayns.sms.model.OTP;
import com.saiayns.sms.repository.OTPRepository;

@Service
public class OTPService {

	@Autowired
	private OTPRepository otpRepository;

	public String generateOTP(String phoneNumber) {
		// Generate a random 6-digit OTP
		String otp = String.format("%06d", new Random().nextInt(999999));

		// Save OTP in the database
		OTP otpEntity = new OTP(phoneNumber, otp, LocalDateTime.now(), false);
		otpRepository.save(otpEntity);

		return otp;
	}

	public boolean validateOTP(String phoneNumber, String otp) {
		// Find OTP in the database
		return otpRepository.findByPhoneNumberAndOtpAndUsed(phoneNumber, otp, false).map(existingOTP -> {
			// Check if OTP is expired (valid for 5 minutes)
			if (existingOTP.getGeneratedAt().isAfter(LocalDateTime.now().minusMinutes(5))) {
				// Mark OTP as used
				existingOTP.setUsed(true);
				otpRepository.save(existingOTP);
				return true;
			}
			return false;
		}).orElse(false);
	}
}
