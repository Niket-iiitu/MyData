package com.MyData.Service;

import com.MyData.Dto.AuthSession;
import com.MyData.Repository.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    Authentication authentication;

    @Override
    public AuthSession signUp(String name, String emailId, String password) {
        AuthSession authSession = new AuthSession();
        if(authentication.emailIdPresent(emailId)){
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("User already registered.");
            return authSession;
        }
        if(authentication.registerUser(name, emailId, password)){;
            authSession.setStatus(AuthSession.State.REGISTERED.toString());
            authSession.setMessage("User registered successfully.");
        } else {
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("User registration failed.");
        }
        return authSession;
    }

    @Override
    public AuthSession logIn(String emailId, String password) {
        AuthSession authSession = new AuthSession();
        if(!authentication.emailIdPresent(emailId)){
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("ID not found. Please Sign Up");
            authSession.setUid("");
            return authSession;
        }
        return authentication.firstLogin(emailId, password);
    }

    @Override
    public AuthSession validateAndUpdateSession(String uid, String sessionId) {
        AuthSession authSession = new AuthSession();
        if(!authentication.checkSession(uid, sessionId)){
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("Invalid request. Please login again.");
            return authSession;
        }
        if(authentication.updateTimeout(uid)){
            authSession.setStatus(AuthSession.State.AUTHORISED.toString());
            authSession.setMessage("Session updated successfully.");
        } else {
            authSession.setStatus(AuthSession.State.TIMEOUT.toString());
            authSession.setMessage("Session expired. Please login again.");
        }
        return authSession;
    }

    @Override
    public AuthSession logOut(String uid, String sessionId) {
        AuthSession authSession = new AuthSession();
        if(!authentication.checkSession(uid, sessionId)){
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("Invalid request. Please login again.");
            return authSession;
        }
        if(authentication.logout(uid, sessionId)){
            authSession.setStatus(AuthSession.State.LOGGED_OUT.toString());
            authSession.setMessage("Logged out successfully.");
        } else {
            authSession.setStatus(AuthSession.State.TIMEOUT.toString());
            authSession.setMessage("Session expired. Please login again.");
        }
        return authSession;
    }
}
