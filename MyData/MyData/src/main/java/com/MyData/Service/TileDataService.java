package com.MyData.Service;

import com.MyData.Dao.TileDataDao;

import java.util.List;

public interface TileDataService {
    List<TileDataDao> getTileDetailsByUserAndFilter(String userId, String filter);
    List<String> getListOfCategories(String userId);
    boolean updateTileById(String chatId, String category, String title, String data, List<String> listOfTags);
    TileDataDao getNoteById(String chatId);
    boolean createNewNote(String category, String title, String data, List<String> listOfTags, String userId);
}
