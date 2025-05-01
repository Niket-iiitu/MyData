package com.MyData.Dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "td_user_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    private String uid;

    private String name;
    private String password;
    private String sessionId;

    private LocalDateTime expiry;
    private LocalDateTime lastLogin;

    @PrePersist
    public void generateUid() {
        if (this.uid == null) {
            this.uid = UUID.randomUUID().toString();
        }
    }
}
