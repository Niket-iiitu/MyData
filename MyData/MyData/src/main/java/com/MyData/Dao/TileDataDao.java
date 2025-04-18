package com.MyData.Dao;

import lombok.Data;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "TD_TILE_DATA")
@Data
public class TileDataDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long id;
    @Column(name="user_id")
    private String userId;
    @Column(name="title")
    private String title;
    @Column(name="category")
    private String category;
    @Column(name="data")
    private String data;
    @Column(name="tags")
    private List<String> tags;
}
