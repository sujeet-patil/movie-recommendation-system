package com.sujeet.recommendations.connectors.openApiConnector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import com.sujeet.recommendations.config.SecretProperties;
import com.sujeet.recommendations.connectors.BaseConnector;
import com.sujeet.recommendations.connectors.models.Connection;
import com.sujeet.recommendations.entity.request.OpenApiRequest;
import com.sujeet.recommendations.entity.response.OpenApiResponse;
@Component
public class OpenApiConnector extends BaseConnector {

    @Autowired
    private SecretProperties secretProperties;
    public OpenApiConnector(@Lazy @Qualifier(value = "openApiConnectionConfig")Connection connection) {
        super(connection);
    }

    public Mono<OpenApiResponse> getEmbeddings(OpenApiRequest openApiRequest) {
        return getWebClient()
                .post()
                .uri(getConnectionConfig().getPath().get("embeddings"))
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(secretProperties.getApiKey()))
                .body(BodyInserters.fromValue(openApiRequest))
                .retrieve()
                .bodyToMono(OpenApiResponse.class);
    }
}
