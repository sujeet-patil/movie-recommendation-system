package com.sujeet.recommendations.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;
import com.sujeet.recommendations.connectors.models.ConnectionConfig;

@Data
@Configuration
@ConfigurationProperties(prefix = "connectors")
public class ConnectionRegistry {
    private Map<String, ConnectionConfig> clientConfigs;
}
