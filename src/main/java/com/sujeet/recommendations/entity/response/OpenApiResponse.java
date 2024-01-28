package com.sujeet.recommendations.entity.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenApiResponse {
    private List<OpenApiData> data;
    private String model;
    private String object;
    private OpenApiUsage usage;
}
