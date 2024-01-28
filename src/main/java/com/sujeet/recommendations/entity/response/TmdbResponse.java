package com.sujeet.recommendations.entity.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sujeet.recommendations.entity.Movie;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TmdbResponse {
    private Integer page;
    private List<Movie> results;
    @JsonProperty(value = "total_pages")
    private Integer totalPages;
    @JsonProperty(value = "total_results")
    private Integer totalResults;
}
