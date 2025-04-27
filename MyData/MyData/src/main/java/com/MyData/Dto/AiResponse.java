package com.MyData.Dto;

import lombok.Data;

import java.util.List;

@Data
public class AiResponse {
    private String summary;
    private String title;
    private List<String> tags;

    public AiResponse(String summary, String title, List<String> tags) {
        this.summary = summary;
        this.title = title;
        this.tags = tags;
    }
}
