package com.nutmeg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:stock_calculator_app.properties")
public class StockCalculatorConfig {

    @Value("${test_url}")
    private String testUrl;

    public String getTestUrl() {
        return testUrl;
    }
}
