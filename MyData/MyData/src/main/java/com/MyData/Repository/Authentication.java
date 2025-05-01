package com.MyData.Repository;

import com.MyData.Dto.AuthSession;

public interface Authentication {
    boolean emailIdPresent(String emailId);
    boolean registerUser(String name, String emailId, String password);
    AuthSession firstLogin(String emailId, String password);
    boolean checkSession(String uid, String sessionId);
    boolean updateTimeout(String uid);
    boolean logout(String uid, String sessionId);
}
