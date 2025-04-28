package com.MyData.Dao;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "MD_APPLICATION_PARAMETER")
@Data
public class ApplicationParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="parameter")
    private String parameter;
    @Column(name="value")
    private String value;
}
