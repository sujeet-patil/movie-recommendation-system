package com.sujeet.recommendations.connectors.models;

import org.springframework.web.reactive.function.client.WebClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Connection {
    private WebClient webClient;
    private ConnectionConfig connectionConfigs;
}
