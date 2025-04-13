package com.MyData.Dto;

import lombok.Data;

import java.util.List;

@Data
public class TileDataDto {
    private String title;
    private List<String> tags;
    private String category;
    private String data;
}
