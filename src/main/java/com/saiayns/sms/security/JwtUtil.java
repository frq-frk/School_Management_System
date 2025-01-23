package com.saiayns.sms.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	 private static final String SECRET_KEY = "57e0d4c5134a8409c3915f4bb1751a37a793cae2ad1fad0da55a5599844f8e776ebe4ba658754fc85bd64c2570a3c6bc78a6e818d391c7d8473d0f516e3909eb0793e795fa43de51f2aa6e60bae87d9a73584411d641f2989fab33992ab755f5ab395375761164c2a4d2b806fdc990101a45c47dd9c66546adca0325bcafd6f1e61fd525db984e812b63170f5f1ae9c5f108a272538ffd7b59ec4e1624ae34620d4e3e1242a17c249f60f35e1a94b3d8260f788568b7494fcf55b6a5d034d3fb2bd09f1d7532aba3e1ef35e99ea3e51067c18655cd8102838345d25a7b786066dd989e78f472af5f5f7f7e570a95dede59041339fee49b07d868b951fd762fa0"; // Replace with a secure key
	    private static final long EXPIRATION_TIME = 86400000; // 24 hours

	    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	    public String generateToken(String username) {
	        return Jwts.builder()
	                .setSubject(username)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .signWith(key)
	                .compact();
	    }

	    public String validateToken(String token) {
	        try {
	            return Jwts.parserBuilder()
	                    .setSigningKey(key)
	                    .build()
	                    .parseClaimsJws(token)
	                    .getBody()
	                    .getSubject();
	        } catch (JwtException e) {
	            throw new RuntimeException("Invalid or expired token");
	        }
	    }
}
