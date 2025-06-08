package com.MyData.Controller;

import com.MyData.Dto.AiResponse;
import com.MyData.Dto.AuthSession;
import com.MyData.Dto.DataTransferWrapper;
import com.MyData.Service.AIService;
import com.MyData.Service.AuthenticationService;
import com.MyData.Service.LoggingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@Tag(name = "AI", description = "APIs for AI functionalities")
public class AIController {
    @Autowired
    AIService aiService;

    @Autowired
    LoggingService loggingService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value = "/summarise")
    @Operation(
            summary = "Summarize Data",
            description = "This API summarizes the provided data and returns the result."
    )
    private DataTransferWrapper generateSummery(@RequestHeader Map<String, String> requestHeader, @RequestBody Map<String, String> requestBody){
        String data = requestBody.get("data");
        DataTransferWrapper res = new DataTransferWrapper();
        String userId = requestHeader.get("uid");

        try{
            AiResponse noteData= aiService.processResponse(data);
            res.setStatus("SUCCESS");
            res.setData(noteData);
            loggingService.logRequest(requestBody.toString(), res.toString(), "AI_SUMMERY", userId, "SUCCESS");
        }
        catch (Exception e){
            System.out.println("[ERROR] HomeController fetchTileList: Error occurred while fetching data");
            e.printStackTrace();
            res.setStatus("ERROR");
            res.setErrorMessage("Error occurred while connecting to DB");
            loggingService.logRequest(requestBody.toString(), res.toString(), "AI_SUMMERY", userId, "ERROR");
        }

        return res;
    }
}
