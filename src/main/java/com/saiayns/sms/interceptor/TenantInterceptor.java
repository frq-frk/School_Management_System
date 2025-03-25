package com.saiayns.sms.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.saiayns.sms.context.TenantContext;
import com.saiayns.sms.resolver.HttpHeaderTenantResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TenantInterceptor implements HandlerInterceptor{
	
	@Autowired
	HttpHeaderTenantResolver tenantResolver;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String tenantId = tenantResolver.resolveTenant(request);
		System.out.println("Received Tenant ID: " + tenantId); // Debugging
		TenantContext.setTenantId(tenantId);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		clearTenant();
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		clearTenant();
	}
	
	private void clearTenant() {
		TenantContext.clear();
	}
	
}
