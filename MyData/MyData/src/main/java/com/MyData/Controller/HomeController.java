package com.MyData.Controller;

import com.MyData.Dao.TileDataDao;
import com.MyData.Dto.DataTransferWrapper;
import com.MyData.Service.LoggingService;
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

    @Autowired
    LoggingService loggingService;

    @GetMapping(value = "/ping")
    private String pingCheck(){
        return "Server Active";
    }

    @PostMapping(value = "/tileList")
    private DataTransferWrapper fetchTileList(@RequestBody Map<String, String> requestBody){
        String filter = requestBody.get("filter");
        DataTransferWrapper res = new DataTransferWrapper();
        List<TileDataDao> sampelList = new ArrayList<>();
        String userId = "1001";

        try{
            sampelList = tileDataService.getTileDetailsByUserAndFilter(userId,filter);
            res.setStatus("SUCCESS");
            res.setData(sampelList);
            loggingService.logRequest(requestBody.toString(), res.toString(), "FETCH_NOTE_LIST", userId, "SUCCESS");
        }
        catch (Exception e){
            System.out.println("[ERROR] HomeController fetchTileList: Error occurred while fetching data");
            e.printStackTrace();
            res.setStatus("ERROR");
            res.setErrorMessage("Error occurred while connecting to DB");
            loggingService.logRequest(requestBody.toString(), res.toString(), "FETCH_NOTE_LIST", userId, "ERROR");
        }

        return res;
    }

    @PostMapping(value = "/fetchCategories")
    private DataTransferWrapper fetchCategoryList(){
        DataTransferWrapper res = new DataTransferWrapper();
        List<String> sampelList = new ArrayList<>();
        String userId = "1001";

        try{
            sampelList = tileDataService.getListOfCategories(userId);
            res.setStatus("SUCCESS");
            res.setData(sampelList);
            loggingService.logRequest("{}", res.toString(), "FETCH_CATEGORIES", userId, "SUCCESS");
        }
        catch (Exception e){
            System.out.println("[ERROR] HomeController fetchCategoryList: Error occurred while fetching data");
            e.printStackTrace();
            res.setStatus("ERROR");
            res.setErrorMessage("Some technical error occurred, please reload or retry after some time.");
            loggingService.logRequest("{}", res.toString(), "FETCH_CATEGORIES", userId, "ERROR");
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
                    loggingService.logRequest(requestBody.toString(), res.toString(), "CREATE_NOTE", userId, "SUCCESS");
                }
                else{
                    res.setStatus("ERROR");
                    res.setErrorMessage("Error occurred while creating new note.");
                    loggingService.logRequest(requestBody.toString(), res.toString(), "CREATE_NOTE", userId, "ERROR");
                }
            }
            else if(tileDataService.updateTileById(noteId, category, title, data, listOfTags)){
                res.setStatus("SUCCESS");
                res.setData("SUCCESS");
                loggingService.logRequest(requestBody.toString(), res.toString(), "UPDATE_NOTE", userId, "SUCCESS");
            }
            else{
                res.setStatus("ERROR");
                res.setErrorMessage("Error occurred while updating note.");
                loggingService.logRequest(requestBody.toString(), res.toString(), "UPDATE_NOTE", userId, "ERROR");
            }

        }
        catch (Exception e){
            System.out.println("[ERROR] HomeController createAndUpdateNote: Error occurred while updating data");
            e.printStackTrace();
            res.setStatus("ERROR");
            res.setErrorMessage("Some technical error occurred, please retry after some time.");
            loggingService.logRequest(requestBody.toString(), res.toString(), "CREATE_UPDATE_NOTE", "1001", "ERROR");
        }

        return res;
    }

    @PostMapping(value = "/deleteNote")
    private DataTransferWrapper deleteNote(@RequestBody Map<String, String> requestBody){
        DataTransferWrapper res = new DataTransferWrapper();
        String noteId = requestBody.get("noteId");
        try{
            String userId = "1001";

            if(Objects.equals(noteId, null)){
                res.setStatus("ERROR");
                res.setErrorMessage("Invalid Note ID");
                loggingService.logRequest(requestBody.toString(), res.toString(), "DELETE_NOTE", userId, "ERROR");
            }
            else if(tileDataService.deleteNoteById(noteId, userId)){
                res.setStatus("SUCCESS");
                res.setData("SUCCESS");
                loggingService.logRequest(requestBody.toString(), res.toString(), "DELETE_NOTE", userId, "SUCCESS");
            }
            else{
                res.setStatus("ERROR");
                res.setErrorMessage("Error occurred while updating note.");
                loggingService.logRequest(requestBody.toString(), res.toString(), "DELETE_NOTE", userId, "ERROR");
            }

        }
        catch (Exception e){
            System.out.println("[ERROR] HomeController updateChatById: Error occurred while updating data");
            e.printStackTrace();
            res.setStatus("ERROR");
            res.setErrorMessage("Some technical error occurred, please retry after some time.");
            loggingService.logRequest(requestBody.toString(), res.toString(), "DELETE_NOTE", "1001", "ERROR");
        }

        return res;
    }
}
