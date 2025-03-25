package com.saiayns.sms.resolver;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;

@Component
public class HttpHeaderTenantResolver implements TenantResolver<HttpServletRequest>{

	@Override
	public String resolveTenant(@NotNull HttpServletRequest request) {
		return request.getHeader("X-Tenant-ID");
	}	
}
