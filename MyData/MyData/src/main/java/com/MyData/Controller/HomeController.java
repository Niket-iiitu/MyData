package com.MyData.Controller;

import com.MyData.Dao.NotesDataDao;
import com.MyData.Dto.AuthSession;
import com.MyData.Dto.DataTransferWrapper;
import com.MyData.Service.AuthenticationService;
import com.MyData.Service.LoggingService;
import com.MyData.Service.NotesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Tag(name = "Notes", description = "APIs for notes functionalities.")
public class HomeController {
    @Autowired
    NotesService notesService;

    @Autowired
    LoggingService loggingService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping(value = "/ping")
    @Operation(
            summary = "Ping Check",
            description = "This API to check if UI is able to connect with server."
    )
    private String pingCheck(){
        return "Server Active";
    }

    @PostMapping(value = "/tileList")
    @Operation(
            summary = "Fetch List Of Notes",
            description = "This API fetches the list of notes by user and categories."
    )
    private DataTransferWrapper fetchTileList(@RequestHeader Map<String, String> requestHeader, @RequestBody Map<String, String> requestBody){
        String filter = requestBody.get("filter");
        DataTransferWrapper res = new DataTransferWrapper();
        List<NotesDataDao> sampelList = new ArrayList<>();
        String userId = requestHeader.get("uid");

        try{
            sampelList = notesService.getTileDetailsByUserAndFilter(userId,filter);
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
    @Operation(
            summary = "Fetch List Of Categories",
            description = "This API fetches the list of categories by user."
    )
    private DataTransferWrapper fetchCategoryList(@RequestHeader Map<String, String> requestHeader){
        DataTransferWrapper res = new DataTransferWrapper();
        List<String> sampelList = new ArrayList<>();
        String userId = requestHeader.get("uid");

        try{
            sampelList = notesService.getListOfCategories(userId);
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
    @Operation(
            summary = "Create or Update Note",
            description = "This API creates a new note or updates an existing note based on the provided data."
    )
    private DataTransferWrapper createAndUpdateNote(@RequestHeader Map<String, String> requestHeader, @RequestBody Map<String, String> requestBody){
        DataTransferWrapper res = new DataTransferWrapper();
        try{
            String noteId = requestBody.get("noteId");
            String category = requestBody.get("category");
            String title = requestBody.get("title");
            String data = requestBody.get("data");
            String tags = requestBody.get("tags");
            List<String> listOfTags = List.of(tags.split("\\|"));
            String userId = requestHeader.get("uid");

            if(Objects.equals(noteId, "")){
                System.out.println("New chat being created");
                if(notesService.createNewNote(category, title, data, listOfTags, userId)){
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
            else if(notesService.updateTileById(noteId, category, title, data, listOfTags)){
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
    @Operation(
            summary = "Delete Note",
            description = "This API deletes a note based on the provided note ID."
    )
    private DataTransferWrapper deleteNote(@RequestHeader Map<String, String> requestHeader, @RequestBody Map<String, String> requestBody){
        DataTransferWrapper res = new DataTransferWrapper();
        String noteId = requestBody.get("noteId");
        String userId = requestHeader.get("uid");

        try{
            if(Objects.equals(noteId, null)){
                res.setStatus("ERROR");
                res.setErrorMessage("Invalid Note ID");
                loggingService.logRequest(requestBody.toString(), res.toString(), "DELETE_NOTE", userId, "ERROR");
            }
            else if(notesService.deleteNoteById(noteId, userId)){
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
