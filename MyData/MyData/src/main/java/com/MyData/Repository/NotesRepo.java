package com.MyData.Repository;

import com.MyData.Dao.NotesDataDao;

import java.util.List;

public interface NotesRepo {
    List<NotesDataDao> findByUserIdAndCategory(String userId, String category);
    List<String> getCategoriesByUserId(String userId);
    NotesDataDao getNoteById(String noteId);
    boolean updateNote(NotesDataDao note);
    boolean createNote(NotesDataDao note);
    boolean deleteNoteById(NotesDataDao note);
}
