package com.assessment.core.services.impl;

import com.assessment.core.config.WeatherTenantConfig;
import com.assessment.core.services.WeatherTenantConfigService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

@Component(
    service = WeatherTenantConfigService.class,
    configurationPolicy = ConfigurationPolicy.REQUIRE,
    immediate = true
)
@Designate(ocd = WeatherTenantConfig.class, factory = true)
public class WeatherTenantConfigServiceImpl implements WeatherTenantConfigService {

    private String tenantId;
    private String weatherApiEndpoint;
    private String weatherApiKey;
    private String siteName;
    private boolean enabled;

    @Activate
    @Modified
    protected void activate(WeatherTenantConfig config) {
        this.tenantId = config.tenantId();
        this.weatherApiEndpoint = config.weatherApiEndpoint();
        this.weatherApiKey = config.weatherApiKey();
        this.siteName = config.siteName();
        this.enabled = config.enabled();
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    @Override
    public String getWeatherApiEndpoint() {
        return weatherApiEndpoint;
    }

    @Override
    public String getWeatherApiKey() {
        return weatherApiKey;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getSiteName() {
        return siteName;
    }
}