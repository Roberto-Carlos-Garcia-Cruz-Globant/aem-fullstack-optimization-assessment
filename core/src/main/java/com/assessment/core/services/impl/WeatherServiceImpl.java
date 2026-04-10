package com.assessment.core.services.impl;

import com.assessment.core.constants.WebConstants;
import com.assessment.core.services.WeatherService;
import com.assessment.core.services.WeatherTenantConfigProvider;
import com.assessment.core.services.WeatherTenantConfigService;
import com.day.cq.wcm.api.Page;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = WeatherService.class, immediate = true)
public class WeatherServiceImpl implements WeatherService {

    @Reference
    private WeatherTenantConfigProvider configProvider;

    @Override
    public String getForecast(Page currentPage, String city) {  
    
        try {
            String siteName = "default";

            if (currentPage != null && currentPage.getPath() != null) {
                String[] pathParts = currentPage.getPath().split("/");
                siteName = pathParts[2];
            }

            WeatherTenantConfigService config = configProvider.getConfigForSite(siteName);

            StringBuilder urlBuilder = new StringBuilder();

            urlBuilder.append(config.getWeatherApiEndpoint())
                      .append(URLEncoder.encode(city, StandardCharsets.UTF_8))
                      .append("?apikey=")
                      .append(config.getWeatherApiKey());
            
            String urlString = urlBuilder.toString();
    
            HttpURLConnection connection =
                    (HttpURLConnection) new URL(urlString).openConnection();
    
            connection.setRequestMethod(WebConstants.GET_METHOD);
            connection.setConnectTimeout(WebConstants.DEFAULT_CONNECTION_TIMEOUT_MS);
            connection.setReadTimeout(WebConstants.DEFAULT_CONNECTION_TIMEOUT_MS);
    
            if (connection.getResponseCode() != WebConstants.SUCCESS_RESPONSE_CODE)
                return WebConstants.API_ERROR_RESPONSE;
    
            return new String(
                    connection.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            );
    
        } catch (Exception e) {
            return WebConstants.SERVICE_UNAVAILABLE_RESPONSE;
        }
    }
}
