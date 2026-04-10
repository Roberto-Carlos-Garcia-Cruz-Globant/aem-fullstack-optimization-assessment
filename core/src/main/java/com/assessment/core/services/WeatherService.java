package com.assessment.core.services;

import com.day.cq.wcm.api.Page;

public interface WeatherService {

    String getForecast(Page currentPage, String city);
}

