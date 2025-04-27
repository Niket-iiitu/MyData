package com.MyData.Dao;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "TD_AUDIT_LOGS")
@Data
public class AuditLogsDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "request", columnDefinition = "TEXT")
    private String request;
    @Column(name = "response", columnDefinition = "TEXT")
    private String response;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "transaction_type")
    private String transactionType;
    @Column(name = "status")
    private String status;
    @Column(name = "created_at")
    private Timestamp createdAt;
}
