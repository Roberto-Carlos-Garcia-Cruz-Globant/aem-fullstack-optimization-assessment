package com.assessment.core.services;

public interface WeatherTenantConfigService {

    String getTenantId();

    String getSiteName();

    String getWeatherApiEndpoint();

    boolean isEnabled();

    String getWeatherApiKey();
}
