package com.MyData.Controller;

import ch.qos.logback.core.util.StringUtil;
import com.MyData.Dto.AuthSession;
import com.MyData.Dto.DataTransferWrapper;
import com.MyData.Service.AuthenticationService;
import com.MyData.Service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    LoggingService loggingService;

    @PostMapping(value = "/logIn")
    public DataTransferWrapper login(@RequestBody Map<String, String> requestBody) {
        AuthSession authSession = new AuthSession();
        DataTransferWrapper dataTransferWrapper = new DataTransferWrapper();
        String email = requestBody.get("email");
        String password = requestBody.get("password");
        if(StringUtil.isNullOrEmpty(email) || StringUtil.isNullOrEmpty(password)) {
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("Email or password cannot be empty.");
            loggingService.logRequest("", authSession.toString(), "LOGIN", "", "FAILED");
            dataTransferWrapper.setStatus("SUCCESS");
            dataTransferWrapper.setData(authSession);
            return dataTransferWrapper;
        }
        String request = email + "|" + password;
        try{
            authSession = authenticationService.logIn(email, password);
            loggingService.logRequest(request, authSession.toString(), "LOGIN", authSession.getUid(), authSession.getStatus());
            dataTransferWrapper.setStatus("SUCCESS");
            dataTransferWrapper.setData(authSession);
        }
        catch (Exception e){
            e.printStackTrace();
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("Error occurred during login: " + e.getMessage());
            loggingService.logRequest(request, authSession.toString(), "LOGIN", "", "FAILED");
            dataTransferWrapper.setStatus("ERROR");
            dataTransferWrapper.setData(authSession);
        }
        return dataTransferWrapper;
    }

    @PostMapping(value = "/signUp")
    public DataTransferWrapper signUp(@RequestBody Map<String, String> requestBody) {
        AuthSession authSession = new AuthSession();
        DataTransferWrapper dataTransferWrapper = new DataTransferWrapper();
        String name = requestBody.get("name");
        String email = requestBody.get("email");
        String password = requestBody.get("password");
        if(StringUtil.isNullOrEmpty(name) || StringUtil.isNullOrEmpty(email) || StringUtil.isNullOrEmpty(password)) {
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("Name, email or password cannot be empty.");
            loggingService.logRequest("", authSession.toString(), "SIGNUP", "", "FAILED");
            dataTransferWrapper.setStatus("SUCCESS");
            dataTransferWrapper.setData(authSession);
            return dataTransferWrapper;
        }
        String request = name + "|" + email + "|" + password;
        try{
            authSession = authenticationService.signUp(name, email, password);
            loggingService.logRequest(request, authSession.toString(), "SIGNUP", authSession.getUid(), authSession.getStatus());
            dataTransferWrapper.setStatus("SUCCESS");
            dataTransferWrapper.setData(authSession);
        }
        catch (Exception e){
            e.printStackTrace();
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("Error occurred during signup: " + e.getMessage());
            loggingService.logRequest(request, authSession.toString(), "SIGNUP", "", "FAILED");
            dataTransferWrapper.setStatus("ERROR");
            dataTransferWrapper.setData(authSession);
        }
        return dataTransferWrapper;
    }

    @PostMapping(value = "/logout")
    public DataTransferWrapper logout(@RequestBody String uid, @RequestBody String sessionId) {
        AuthSession authSession = new AuthSession();
        DataTransferWrapper dataTransferWrapper = new DataTransferWrapper();
        if(StringUtil.isNullOrEmpty(uid) || StringUtil.isNullOrEmpty(sessionId)) {
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("User ID or session ID cannot be empty.");
            loggingService.logRequest("", authSession.toString(), "LOGOUT", "", "FAILED");
            dataTransferWrapper.setStatus("SUCCESS");
            dataTransferWrapper.setData(authSession);
            return dataTransferWrapper;
        }
        String request = uid + "|" + sessionId;
        try{
            authSession = authenticationService.logOut(uid, sessionId);
            loggingService.logRequest(request, authSession.toString(), "LOGOUT", uid, authSession.getStatus());
            dataTransferWrapper.setStatus("SUCCESS");
            dataTransferWrapper.setData(authSession);
        }
        catch (Exception e){
            e.printStackTrace();
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("Error occurred during logout: " + e.getMessage());
            loggingService.logRequest(request, authSession.toString(), "LOGOUT", "", "FAILED");
            dataTransferWrapper.setStatus("ERROR");
            dataTransferWrapper.setData(authSession);
        }
        return dataTransferWrapper;
    }

    @PostMapping(value = "/autoLogin")
    public DataTransferWrapper autoLogin(@RequestBody Map<String, String> requestBody) {
        AuthSession authSession = new AuthSession();
        DataTransferWrapper dataTransferWrapper = new DataTransferWrapper();
        String uid = requestBody.get("uid");
        String sessionId = requestBody.get("sessionId");
        if(StringUtil.isNullOrEmpty(uid) || StringUtil.isNullOrEmpty(sessionId)) {
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("uid or sessionId cannot be empty.");
            loggingService.logRequest("", authSession.toString(), "AUTO_LOGIN", "", "FAILED");
            dataTransferWrapper.setStatus("SUCCESS");
            dataTransferWrapper.setData(authSession);
            return dataTransferWrapper;
        }
        String request = uid + "|" + sessionId;
        try{
            authSession = authenticationService.validateAndUpdateSession(uid, sessionId);
            loggingService.logRequest(request, authSession.toString(), "AUTO_LOGIN", authSession.getUid(), authSession.getStatus());
            dataTransferWrapper.setStatus("SUCCESS");
            dataTransferWrapper.setData(authSession);
        }
        catch (Exception e){
            e.printStackTrace();
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("Error occurred during signup: " + e.getMessage());
            loggingService.logRequest(request, authSession.toString(), "AUTO_LOGIN", "", "FAILED");
            dataTransferWrapper.setStatus("ERROR");
            dataTransferWrapper.setData(authSession);
        }
        return dataTransferWrapper;
    }
}
