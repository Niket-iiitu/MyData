package com.MyData.Repository;

import com.MyData.Dao.TileDataDao;

import java.util.List;

public interface TileDataRepo {
    List<TileDataDao> findByUserIdAndCategory(String userId, String category);
    List<String> getCategoriesByUserId(String userId);
    TileDataDao getNoteById(String noteId);
    boolean updateNote(TileDataDao note);
}
