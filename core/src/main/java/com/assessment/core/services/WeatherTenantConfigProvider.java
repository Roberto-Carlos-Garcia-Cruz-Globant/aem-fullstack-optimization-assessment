package com.assessment.core.services;

import java.util.List;

public interface WeatherTenantConfigProvider {

    WeatherTenantConfigService getConfigForTenantId(String tenantId);
    
    WeatherTenantConfigService getConfigForSite(String siteName);

    List<WeatherTenantConfigService> getAllConfigs();
}