package com.assessment.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
        name = "Assessment Tenant Configuration",
        description = "Tenant-aware configuration for Weather Service"
)
public @interface WeatherTenantConfig {

        
    @AttributeDefinition(
            name = "Tenant ID",
            description = "Unique identifier for the tenant"
    )
    String tenantId() default "";

    @AttributeDefinition(
            name = "Weather API Endpoint",
            description = "Secure weather API endpoint URL"
    )
    String weatherApiEndpoint() default "https://goweather.xyz/weather/";

    @AttributeDefinition(
            name = "Weather API Key",
            description = "Secure API key stored in AEM Secret Manager (do not expose)"
    )
    String weatherApiKey() default "";

    @AttributeDefinition(
            name = "Site Name",
            description = "Site name for current configuration (used for logging and context)"
    )
    String siteName() default "";

    @AttributeDefinition(
            name = "Enabled",
            description = "Enable this tenant configuration"
    )
    boolean enabled() default true;
}
