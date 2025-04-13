package com.MyData.Controller;

import com.MyData.Dto.TileDataDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {
    @GetMapping(value = "/ping")
    private String pingCheck(){
        return "Server Active";
    }

    @PostMapping(value = "/tileList")
    private List<TileDataDto> fetchTileList(String filter){
        List<TileDataDto> sampelList = new ArrayList<>();
        TileDataDto ele1 = new TileDataDto();
        ele1.setTitle("Tittle1");
        ele1.setCategory("Default");
        List<String> tags1 = new ArrayList<>();
        tags1.add("Home");
        tags1.add("Shop");
        ele1.setTags(tags1);
        ele1.setData("Hellow World");
        sampelList.add(ele1);

        TileDataDto ele2 = new TileDataDto();
        ele2.setTitle("Tittle2");
        ele2.setCategory("Default");
        List<String> tags2 = new ArrayList<>();
        tags2.add("Home");
        tags2.add("Shop");
        ele2.setTags(tags2);
        ele2.setData("Hellow World");
        sampelList.add(ele2);

        return sampelList;
    }
}
