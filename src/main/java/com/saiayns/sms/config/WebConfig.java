package com.saiayns.sms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.saiayns.sms.interceptor.TenantInterceptor;

public class WebConfig implements WebMvcConfigurer{

	@Autowired
	TenantInterceptor tenantInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tenantInterceptor);
		WebMvcConfigurer.super.addInterceptors(registry);
	}

}
