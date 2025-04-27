package com.MyData.Service;

public interface LoggingService {
    void logRequest(String request, String response, String transactionType, String userId, String status);
}
