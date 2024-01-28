package com.sujeet.recommendations.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiUsage {
    @JsonProperty(value = "prompt_tokens")
    private String promptTokens;

    @JsonProperty(value = "total_tokens")
    private String totalTokens;
}
