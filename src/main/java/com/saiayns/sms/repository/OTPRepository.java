package com.saiayns.sms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saiayns.sms.model.OTP;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByPhoneNumberAndOtpAndUsed(String phoneNumber, String otp, boolean used);
}