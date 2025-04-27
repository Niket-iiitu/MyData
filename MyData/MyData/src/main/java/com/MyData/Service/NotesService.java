package com.MyData.Service;

import com.MyData.Dao.NotesDataDao;

import java.util.List;

public interface NotesService {
    List<NotesDataDao> getTileDetailsByUserAndFilter(String userId, String filter);
    List<String> getListOfCategories(String userId);
    boolean updateTileById(String chatId, String category, String title, String data, List<String> listOfTags);
    NotesDataDao getNoteById(String chatId);
    boolean createNewNote(String category, String title, String data, List<String> listOfTags, String userId);
    boolean deleteNoteById(String chatId, String userId);
}
