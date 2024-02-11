package com.sujeet.recommendations.service.impl;

import com.mongodb.client.model.Aggregates;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.sujeet.recommendations.connectors.openApiConnector.OpenApiConnector;
import com.sujeet.recommendations.connectors.tmdbConnector.TmdbConnector;
import com.sujeet.recommendations.entity.Movie;
import com.sujeet.recommendations.entity.request.OpenApiRequest;
import com.sujeet.recommendations.entity.request.SearchRequest;
import com.sujeet.recommendations.entity.response.TmdbResponse;
import com.sujeet.recommendations.repository.MoviesRepository;
import com.sujeet.recommendations.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.mongodb.client.model.search.SearchPath.fieldPath;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {
    @Autowired
    private TmdbConnector connector;
    @Autowired
    private MoviesRepository moviesRepository;
    @Autowired
    private MongoDatabase mongoDatabase;
    @Value("${openApi.model}")
    private String model;
    @Autowired
    private OpenApiConnector openApiConnector;

    @Override
    public Mono<TmdbResponse> getAllMovies(long page){
        return connector.fetchMovies(page);
    }

    @Override
    public Mono<Movie> addMovie(Movie movie) {
        return moviesRepository.save(movie);
    }

    @Override
    public Mono<Movie> addMovieV2(Movie movie) {
        return Mono.just(movie)
                .flatMap(movie1 -> openApiConnector.getEmbeddings(OpenApiRequest.builder()
                                .model(model)
                                .input(movie1.getOverview())
                        .build()))
                .map(openApiResponse -> {
                    movie.setEmbeddings(openApiResponse.getData().get(0).getEmbedding());
                    movie.setTotalTokensConsumed(openApiResponse.getUsage().getTotalTokens());
                    return movie;
                })
                .flatMap(movie1 -> moviesRepository.save(movie1));
    }

    @Override
    public Flux<Movie> addMultiple(List<Movie> movieList) {
        return Flux.fromIterable(movieList)
                .flatMap(movie1 -> openApiConnector.getEmbeddings(OpenApiRequest.builder()
                        .model(model)
                        .input(movie1.getOverview())
                        .build())
                        .map(openApiResponse -> {
                            movie1.setEmbeddings(openApiResponse.getData().get(0).getEmbedding());
                            movie1.setTotalTokensConsumed(openApiResponse.getUsage().getTotalTokens());
                            return movie1;
                        }))
                .flatMap(movie1 -> moviesRepository.save(movie1));

    }

    @Override
    public Flux<Movie> findMovie(SearchRequest searchRequest) {
        return Mono.just(searchRequest)
                .flatMap(searchRequest1 -> openApiConnector.getEmbeddings(OpenApiRequest.builder()
                        .model(model)
                        .input(searchRequest1.getSearchInput())
                        .build()))
                .flatMapMany(openApiResponse -> Flux.from(mongoDatabase.getCollection("movies")
                        .aggregate(getPipeline(openApiResponse.getData().get(0).getEmbedding()), Movie.class)))
                .doOnNext(movie -> movie.setEmbeddings(null));
    }

    private List<Bson> getPipeline(List<Double> embeddings) {
        return List.of(Aggregates.vectorSearch(fieldPath("embeddings"), embeddings, "vectorSearch", 5, 5));
    }



}
