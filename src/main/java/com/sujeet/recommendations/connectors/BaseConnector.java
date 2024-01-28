package com.sujeet.recommendations.connectors;

import org.springframework.web.reactive.function.client.WebClient;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import com.sujeet.recommendations.connectors.models.Connection;
import com.sujeet.recommendations.connectors.models.ConnectionConfig;

@Slf4j
public class BaseConnector {
    @Getter
    protected Connection connection;
    public BaseConnector(Connection connection) {
        this.connection = connection;
    }
    protected WebClient getWebClient() {
        return connection.getWebClient();
    }
    protected ConnectionConfig getConnectionConfig() {
        return connection.getConnectionConfigs();
    }
}
