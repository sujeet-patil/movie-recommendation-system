package com.sujeet.recommendations.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import lombok.Getter;


@Configuration
@Getter
@PropertySource(value = "${secrets.filePath}", factory = YamlPropertySourceFactory.class)
public class SecretProperties {
    @Value("${mongodb.userName}")
    private String username;

    @Value("${mongodb.password}")
    private String password;

    @Value("${openApi.apiKey}")
    private String apiKey;

    @Value("${tmdb.accessToken}")
    private String tmdbToken;

}
