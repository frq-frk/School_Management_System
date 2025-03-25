package com.saiayns.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.saiayns.sms.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // Disable CSRF
				.cors(cors -> cors.configurationSource(request -> {
					org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
					config.addAllowedOrigin("*"); // Allow all origins. Change to specific origins in production for
													// security.
					config.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
					config.addAllowedHeader("*"); // Allow all headers
					config.setAllowCredentials(false); // Set to true if you allow cookies
					return config;
				})).authorizeHttpRequests(auth -> auth
						// Permit these endpoints without authentication
						.requestMatchers("/api/user/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**",
								"/api/marks/download-request", "/api/marks/download-validate", "/api/terms/get", "/api/tenant/**")
						.permitAll()
						// Authenticate all other requests
						.anyRequest().authenticated())
				// Add the JWT authentication filter before username-password auth
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
