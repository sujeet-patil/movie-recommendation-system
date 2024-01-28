package com.sujeet.recommendations.connectors.models;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionConfig {
    private String host;
    private HashMap<String, String> path;
}
