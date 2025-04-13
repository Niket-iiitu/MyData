package com.MyData.Controller;

import com.MyData.Dao.TileDataDao;
import com.MyData.Dto.DataTransferWrapper;
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
    private DataTransferWrapper fetchTileList(@RequestBody Map<String, String> requestBody){
        String filter = requestBody.get("filter");
        DataTransferWrapper res = new DataTransferWrapper();
        List<TileDataDao> sampelList = new ArrayList<>();

        try{
            sampelList = tileDataService.getTileDetailsByUserAndFilter("1001",filter);
            res.setStatus("SUCCESS");
            res.setData(sampelList);
        }
        catch (Exception e){
            System.out.println("[ERROR] HomeController fetchTileList: Error occurred while fetching data");
            e.printStackTrace();
            res.setStatus("ERROR");
            res.setErrorMessage("Error occurred while connecting to DB");
        }

        return res;
    }
}
