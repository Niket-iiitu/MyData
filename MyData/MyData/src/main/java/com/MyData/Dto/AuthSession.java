package com.MyData.Dto;

import lombok.Data;

@Data
public class AuthSession {
    private String uid;
    private String sessionId;
    private String status;
    private String message;
    public enum State {
        AUTHORISED,
        TIMEOUT,
        FAILED,
        LOGGED_OUT,
        REGISTERED
    }
}
