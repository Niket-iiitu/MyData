package com.MyData.Service;

import com.MyData.Dto.AuthSession;

public interface AuthenticationService {
    AuthSession signUp(String name, String emailId, String password);
    AuthSession logIn(String emailId, String password);
    AuthSession validateAndUpdateSession(String uid, String sessionId);
    AuthSession logOut(String uid, String sessionId);
}
