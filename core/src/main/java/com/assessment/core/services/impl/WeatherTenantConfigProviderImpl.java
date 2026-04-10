package com.assessment.core.services.impl;

import com.assessment.core.services.WeatherTenantConfigProvider;
import com.assessment.core.services.WeatherTenantConfigService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component(service = WeatherTenantConfigProvider.class, immediate = true)
public class WeatherTenantConfigProviderImpl implements WeatherTenantConfigProvider {

    @Reference(
            service = WeatherTenantConfigService.class,
            cardinality = ReferenceCardinality.MULTIPLE,
            policy = ReferencePolicy.DYNAMIC,
            bind = "bind", unbind = "unbind"
    )
    private final List<WeatherTenantConfigService> configs = new CopyOnWriteArrayList<>();

    protected void bind(WeatherTenantConfigService weatherConfigService) { 
        configs.add(weatherConfigService); 
    }

    protected void unbind(WeatherTenantConfigService weatherConfigService) { 
        configs.remove(weatherConfigService);
    }

    @Override
    public WeatherTenantConfigService getConfigForSite(String siteName) {
        return configs.stream()
                .filter(c -> c.getSiteName().equalsIgnoreCase(siteName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public WeatherTenantConfigService getConfigForTenantId(String tenantId) {
        return configs.stream()
                .filter(c -> c.getTenantId().equalsIgnoreCase(tenantId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<WeatherTenantConfigService> getAllConfigs() {
        return configs;
    }
}
