package com.MyData.Controller;

import ch.qos.logback.core.util.StringUtil;
import com.MyData.Dto.AuthSession;
import com.MyData.Service.AuthenticationService;
import com.MyData.Service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    LoggingService loggingService;

    @PostMapping(value = "/login")
    public AuthSession login(@RequestBody String email, @RequestBody String password) {
        AuthSession authSession = new AuthSession();
        if(StringUtil.isNullOrEmpty(email) || StringUtil.isNullOrEmpty(password)) {
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("Email or password cannot be empty.");
            loggingService.logRequest("", authSession.toString(), "LOGIN", "", "FAILED");
            return authSession;
        }
        String request = email + "|" + password;
        try{
            authSession = authenticationService.logIn(email, password);
            loggingService.logRequest(request, authSession.toString(), "LOGIN", authSession.getUid(), authSession.getStatus());
        }
        catch (Exception e){
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("Error occurred during login: " + e.getMessage());
            loggingService.logRequest(request, authSession.toString(), "LOGIN", "", "FAILED");

        }
        return authSession;
    }

    @PostMapping(value = "/signUp")
    public AuthSession signUp(@RequestBody String name, @RequestBody String email, @RequestBody String password) {
        AuthSession authSession = new AuthSession();
        if(StringUtil.isNullOrEmpty(name) || StringUtil.isNullOrEmpty(email) || StringUtil.isNullOrEmpty(password)) {
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("Name, email or password cannot be empty.");
            loggingService.logRequest("", authSession.toString(), "SIGNUP", "", "FAILED");
            return authSession;
        }
        String request = name + "|" + email + "|" + password;
        try{
            authSession = authenticationService.signUp(name, email, password);
            loggingService.logRequest(request, authSession.toString(), "SIGNUP", authSession.getUid(), authSession.getStatus());
        }
        catch (Exception e){
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("Error occurred during signup: " + e.getMessage());
            loggingService.logRequest(request, authSession.toString(), "SIGNUP", "", "FAILED");

        }
        return authSession;
    }

    @PostMapping(value = "/logout")
    public AuthSession logout(@RequestBody String uid, @RequestBody String sessionId) {
        AuthSession authSession = new AuthSession();
        if(StringUtil.isNullOrEmpty(uid) || StringUtil.isNullOrEmpty(sessionId)) {
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("User ID or session ID cannot be empty.");
            loggingService.logRequest("", authSession.toString(), "LOGOUT", "", "FAILED");
            return authSession;
        }
        String request = uid + "|" + sessionId;
        try{
            authSession = authenticationService.logOut(uid, sessionId);
            loggingService.logRequest(request, authSession.toString(), "LOGOUT", uid, authSession.getStatus());
        }
        catch (Exception e){
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("Error occurred during logout: " + e.getMessage());
            loggingService.logRequest(request, authSession.toString(), "LOGOUT", "", "FAILED");

        }
        return authSession;
    }
}
