package com.MyData.Service;

import com.MyData.Dto.AiResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AIServiceImpl implements AIService {
    @Override
    public AiResponse processResponse(String text){
        // Summarization Model (BART)
        String summaryResponse = callHuggingFace("facebook/bart-large-cnn", "Generate a brief summery: " + text);

        JSONArray summaryArray = new JSONArray(summaryResponse);
        String summary = summaryArray.getJSONObject(0).getString("summary_text");

        String title = "";

        List<String> tags = new ArrayList<>();

        return new AiResponse(summary, title, tags);
    }

    private String callHuggingFace(String model, String inputText){
        String HUGGINGFACE_API_URL = "https://api-inference.huggingface.co/models";
        String API_KEY = "your-hugging-face-api-key-here";
        String responseString = "";
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost postRequest = new HttpPost(HUGGINGFACE_API_URL + "/" + model);
            postRequest.setHeader("Authorization", "Bearer " + API_KEY);

            // Create the JSON payload
            JSONObject payload = new JSONObject();
            payload.put("inputs", inputText);

            postRequest.setEntity(new org.apache.http.entity.StringEntity(payload.toString()));

            // Execute request
            org.apache.http.client.methods.CloseableHttpResponse response = httpClient.execute(postRequest);
            HttpEntity entity = response.getEntity();

            // Parse and return the response as a string
            responseString = EntityUtils.toString(entity);
            response.close();
            httpClient.close();
        }
        catch (Exception e){
            System.out.println("[ERROR] AIServiceImpl callHuggingFace: Error occurred while calling Hugging Face API");
            e.printStackTrace();
        }

        return responseString;
    }
}
