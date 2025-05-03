package com.MyData.Repository;

import com.MyData.Dao.UserInfo;
import com.MyData.Dto.AuthSession;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Repository
public class AuthenticationImpl implements Authentication{
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    ApplicationParameterRepository applicationParameterRepository;

    @Override
    @Transactional
    public boolean emailIdPresent(String email) {
        String query = "SELECT u FROM UserInfo u WHERE u.email = :email";
        UserInfo userInfo = getUserByEmailId(email);
        return userInfo != null;
    }

    @Override
    @Transactional
    public boolean registerUser(String name, String emailId, String password) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setEmail(emailId);
        userInfo.setPassword(password);
        entityManager.persist(userInfo);
        return userInfo.getId() != null;
    }

    private String generateAlphaNumericString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder result = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }

    private UserInfo getUserByEmailId(String emailId) {
        String query = "SELECT u FROM UserInfo u WHERE u.email = :email";
        return entityManager.createQuery(query, UserInfo.class)
                .setParameter("email", emailId)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    @Transactional
    public AuthSession firstLogin(String emailId, String password) {
        UserInfo userInfo = getUserByEmailId(emailId);
        AuthSession authSession = new AuthSession();
        if(userInfo != null && userInfo.getPassword().equals(password)) {
            userInfo.setLastLogin(LocalDateTime.now());
            int expiryTime = Integer.getInteger(applicationParameterRepository.findValueByParameter("SESSION_TIMEOUT"), 5);
            userInfo.setExpiry(LocalDateTime.now().plusMinutes(expiryTime));
            userInfo.setSessionId(generateAlphaNumericString());
            entityManager.merge(userInfo);
            authSession.setStatus(AuthSession.State.AUTHORISED.toString());
            authSession.setMessage("Login successful.");
            authSession.setSessionId(userInfo.getSessionId());
            authSession.setUid(userInfo.getUid());
        } else {
            //Logout on entering wrong password
            userInfo.setExpiry(LocalDateTime.now().minusMinutes(5));
            entityManager.merge(userInfo);
            authSession.setStatus(AuthSession.State.FAILED.toString());
            authSession.setMessage("Invalid credentials.");
        }
        return authSession;
    }

    @Override
    @Transactional
    public boolean checkSession(String uid, String sessionId) {
        UserInfo userInfo = entityManager.find(UserInfo.class, uid);
        if(userInfo != null && userInfo.getSessionId().equals(sessionId) && userInfo.getExpiry().isAfter(LocalDateTime.now()) && userInfo.getSessionId()!=null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateTimeout(String uid) {
        UserInfo userInfo = entityManager.find(UserInfo.class, uid);
        if(userInfo != null) {
            int expiryTime = Integer.getInteger(applicationParameterRepository.findValueByParameter("SESSION_TIMEOUT"), 5);
            userInfo.setExpiry(LocalDateTime.now().plusMinutes(expiryTime));
            entityManager.merge(userInfo);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean logout(String uid, String sessionId) {
        UserInfo userInfo = entityManager.find(UserInfo.class, uid);
        if(userInfo != null && userInfo.getSessionId().equals(sessionId)) {
            userInfo.setExpiry(LocalDateTime.now().minusMinutes(5));
            userInfo.setSessionId(null);
            entityManager.merge(userInfo);
            return true;
        } else {
            return false;
        }
    }
}
