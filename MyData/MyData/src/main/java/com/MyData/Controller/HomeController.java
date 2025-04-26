package com.MyData.Controller;

import com.MyData.Dao.TileDataDao;
import com.MyData.Dto.DataTransferWrapper;
import com.MyData.Service.TileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @PostMapping(value = "/fetchCategories")
    private DataTransferWrapper fetchCategoryList(){
        DataTransferWrapper res = new DataTransferWrapper();
        List<String> sampelList = new ArrayList<>();

        try{
            sampelList = tileDataService.getListOfCategories("1001");
            res.setStatus("SUCCESS");
            res.setData(sampelList);
        }
        catch (Exception e){
            System.out.println("[ERROR] HomeController fetchCategoryList: Error occurred while fetching data");
            e.printStackTrace();
            res.setStatus("ERROR");
            res.setErrorMessage("Some technical error occurred, please reload or retry after some time.");
        }

        return res;
    }

    @PostMapping(value = "/createAndUpdateNote")
    private DataTransferWrapper createAndUpdateNote(@RequestBody Map<String, String> requestBody){
        DataTransferWrapper res = new DataTransferWrapper();
        try{
            String noteId = requestBody.get("noteId");
            String category = requestBody.get("category");
            String title = requestBody.get("title");
            String data = requestBody.get("data");
            String tags = requestBody.get("tags");
            List<String> listOfTags = List.of(tags.split("\\|"));
            String userId = "1001";

            if(Objects.equals(noteId, "")){
                System.out.println("New chat being created");
                if(tileDataService.createNewNote(category, title, data, listOfTags, userId)){
                    res.setStatus("SUCCESS");
                    res.setData("SUCCESS");
                }
                else{
                    res.setStatus("ERROR");
                    res.setErrorMessage("Error occurred while creating new note.");
                }
            }
            else if(tileDataService.updateTileById(noteId, category, title, data, listOfTags)){
                res.setStatus("SUCCESS");
                res.setData("SUCCESS");
            }
            else{
                res.setStatus("ERROR");
                res.setErrorMessage("Error occurred while updating note.");
            }

        }
        catch (Exception e){
            System.out.println("[ERROR] HomeController updateChatById: Error occurred while updating data");
            e.printStackTrace();
            res.setStatus("ERROR");
            res.setErrorMessage("Some technical error occurred, please retry after some time.");
        }

        return res;
    }
}
