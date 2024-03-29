package com.sujeet.recommendations.connectors.tmdbConnector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import com.sujeet.recommendations.config.SecretProperties;
import com.sujeet.recommendations.connectors.BaseConnector;
import com.sujeet.recommendations.connectors.models.Connection;
import com.sujeet.recommendations.entity.response.TmdbResponse;

@Component
public class TmdbConnector extends BaseConnector {

    @Autowired
    private SecretProperties secretProperties;

    public TmdbConnector(@Lazy @Qualifier("tmdbConnectionConfig")Connection connection) {
        super(connection);
    }

    public Mono<TmdbResponse> fetchMovies() {
        return getWebClient()
                .get()
                .uri(getConnectionConfig().getPath().get("movies"))
                .accept(MediaType.APPLICATION_JSON)
                .headers(header-> header.setBearerAuth(secretProperties.getTmdbToken()))
                .retrieve()
                .bodyToMono(TmdbResponse.class);
    }

}
