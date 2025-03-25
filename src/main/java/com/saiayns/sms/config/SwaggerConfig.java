package com.saiayns.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@io.swagger.v3.oas.annotations.security.SecurityScheme(name = "BearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class SwaggerConfig {

	@Bean
	public OpenAPI customizeOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("School Management API").version("1.0")
						.description("Multi-Tenant School Management API with Security")
						.license(new License().name("Apache 2.0").url("https://springdoc.org")))
				.components(new Components()
						.addSecuritySchemes("BearerAuth",
								new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
										.bearerFormat("JWT"))
						.addSecuritySchemes("X-Tenant-ID",
								new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER)
										.name("X-Tenant-ID")))
				.addSecurityItem(new SecurityRequirement().addList("BearerAuth").addList("X-Tenant-ID"));
	}
}