package com.saiayns.sms.context;

public class TenantContext {
	
	private TenantContext() {};
	
    private static final ThreadLocal<String> CURRENT_TENANT = new InheritableThreadLocal<>();

    public static void setTenantId(String tenantId) {
    	System.out.println("Setting Tenant ID: " + tenantId); // Debugging
        CURRENT_TENANT.set(tenantId);
    }

    public static String getTenantId() {
        return CURRENT_TENANT.get();
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}
