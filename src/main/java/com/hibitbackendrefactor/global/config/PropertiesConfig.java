package com.hibitbackendrefactor.global.config;

import com.hibitbackendrefactor.global.config.properties.GoogleProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GoogleProperties.class)
public class PropertiesConfig {
}
