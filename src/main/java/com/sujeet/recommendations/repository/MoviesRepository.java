package com.sujeet.recommendations.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.sujeet.recommendations.entity.Movie;

@Repository
public interface MoviesRepository extends ReactiveMongoRepository<Movie, Integer> {
}
