package com.sujeet.recommendations.service;

import com.sujeet.recommendations.entity.request.SearchRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.sujeet.recommendations.entity.Movie;
import com.sujeet.recommendations.entity.response.TmdbResponse;

import java.util.List;

public interface MovieService {
    Mono<TmdbResponse> getAllMovies();
    Mono<Movie> addMovie(Movie movie);
    Mono<Movie> addMovieV2(Movie movie);
    Flux<Movie> addMultiple(List<Movie> movieList);
    Flux<Movie> findMovie(SearchRequest searchRequest);
}
