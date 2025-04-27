package com.MyData.Controller;

import com.MyData.Dto.AiResponse;
import com.MyData.Dto.DataTransferWrapper;
import com.MyData.Service.AIService;
import com.MyData.Service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AIController {
    @Autowired
    AIService aiService;

    @Autowired
    LoggingService loggingService;

    @PostMapping(value = "/summarise")
    private DataTransferWrapper generateSummery(@RequestBody Map<String, String> requestBody){
        String data = requestBody.get("data");
        DataTransferWrapper res = new DataTransferWrapper();
        String userId = "1001";

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
