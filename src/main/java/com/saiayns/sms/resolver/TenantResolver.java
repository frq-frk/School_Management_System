package com.saiayns.sms.resolver;

import jakarta.validation.constraints.NotNull;

@FunctionalInterface
public interface TenantResolver<T> {
	String resolveTenant(@NotNull T object);
}
