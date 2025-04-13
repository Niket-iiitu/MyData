package com.MyData.Controller;

import com.MyData.Dto.TileDataDto;
import com.MyData.Service.TileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class HomeController {
    @Autowired
    TileDataService tileDataService;

    @GetMapping(value = "/ping")
    private String pingCheck(){
        return "Server Active";
    }

    @PostMapping(value = "/tileList")
    private List<TileDataDto> fetchTileList(@RequestBody Map<String, String> requestBody){
        String filter = requestBody.get("filter");
        List<TileDataDto> sampelList = new ArrayList<>();

        try{
            sampelList = tileDataService.getTileDetailsByUserAndFilter("1001",filter);
        }
        catch (Exception e){
            System.out.println("[ERROR] HomeController fetchTileList: Error occurred while fetching data");
            e.printStackTrace();
        }

        return sampelList;
    }
}
