package com.sujeet.recommendations.controller;

import com.sujeet.recommendations.entity.request.SearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.sujeet.recommendations.entity.Movie;
import com.sujeet.recommendations.entity.response.TmdbResponse;
import com.sujeet.recommendations.service.MovieService;

import java.util.List;

@RestController
@CrossOrigin("*")
@Slf4j
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping(value = "/movies")
    public Mono<TmdbResponse> getMovies(){
        return movieService.getAllMovies();
    }

    @PostMapping(value = "/add")
    public Mono<Movie> addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

    @PostMapping(value = "/add/movie")
    public Mono<Movie> addMovieV2(@RequestBody Movie movie) {
        return movieService.addMovieV2(movie);
    }

    @PostMapping(value = "/add/movies")
    public Flux<Movie> addMovies(@RequestBody List<Movie> movieList) {
        return movieService.addMultiple(movieList);
    }

    @PostMapping(value = "/find")
    public Flux<Movie> findMovie(@RequestBody SearchRequest searchRequest) {
        return movieService.findMovie(searchRequest);
    }
}
