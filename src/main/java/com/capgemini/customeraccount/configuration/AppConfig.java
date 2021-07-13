package com.capgemini.customeraccount.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.properties")
@Data
public class AppConfig {

    @Value("${tm.transactionUrl}")
    private String transactionUrl;
    @Value("${tm.transactioTypeURL}")
    private String transactioTypeURL;
}