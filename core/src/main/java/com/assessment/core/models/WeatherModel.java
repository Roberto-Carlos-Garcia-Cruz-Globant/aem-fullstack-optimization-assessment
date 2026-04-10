package com.assessment.core.models;

import com.assessment.core.services.WeatherService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = SlingHttpServletRequest.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class WeatherModel {

    private final String DEFAULT_CITY = "Bogota";

    @Inject
    private SlingHttpServletRequest request;

    @Inject
    private WeatherService weatherService;

    @Inject
    private Page currentPage;

    @Inject
    private String city;

    private String weatherJson;

    @PostConstruct
    protected void init() {

        String requestedCity = city != null ? city : DEFAULT_CITY;

        weatherJson = weatherService.getForecast(currentPage,
                requestedCity);
    }

    public String getCity() {
        return city != null ? city : DEFAULT_CITY;
    }

    public String getWeatherJson() {
        return weatherJson;
    }
}
