package com.sujeet.recommendations.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "movies")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    @Id
    private Integer id;
    private String title;
    private String overview;
    @JsonProperty(value = "release_date")
    private Date releaseDate;
    @JsonProperty(value = "poster_path")
    private String posterPath;
    @JsonProperty(value = "original_language")
    private String language;
    @JsonProperty(value = "vote_average")
    private String voteAverage;
    @JsonProperty(value = "vote_count")
    private String voteCount;
    private List<Double> embeddings;
    private String totalTokensConsumed;
}
