package com.MyData.Service;

import ch.qos.logback.core.util.StringUtil;
import com.MyData.Dao.NotesDataDao;
import com.MyData.Repository.NotesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {
    @Autowired
    NotesRepo notesRepo;

    @Override
    public List<NotesDataDao> getTileDetailsByUserAndFilter(String userId, String filter){
        List<NotesDataDao> listOfTiles = new ArrayList<>();
        if(StringUtil.notNullNorEmpty(userId) && StringUtil.notNullNorEmpty(filter)){
            List<NotesDataDao> dbResponse = notesRepo.findByUserIdAndCategory(userId, filter);
            if(dbResponse!=null){
                listOfTiles = dbResponse;
            }
            else{
                System.out.println("[WARNING] TileDataServiceImpl.java getTileDetailsByUserAndFilter: No data found for given userID and filter.");
            }
        }
        else{
            System.out.println("[WARNING] TileDataServiceImpl.java getTileDetailsByUserAndFilter: userId or filter is blank.");
        }
        return listOfTiles;
    }

    @Override
    public List<String> getListOfCategories(String userId){
        List<String> listOfCategories = new ArrayList<>();
        if(StringUtil.notNullNorEmpty(userId)){
            List<String> dbResponse = notesRepo.getCategoriesByUserId(userId);
            if(dbResponse!=null && dbResponse.size()>0){
                listOfCategories = dbResponse;
                if(listOfCategories.contains("Default")){
                    listOfCategories.remove("Default");
                }
                Collections.sort(listOfCategories);
                listOfCategories.add(0, "Default");
            }
            else{
                listOfCategories.add("Default");
            }
        }
        else{
            System.out.println("[WARNING] TileDataServiceImpl.java getListOfCategories: userId is blank.");
        }
        return listOfCategories;
    }

    @Override
    public boolean updateTileById(String chatId, String category, String title, String data, List<String> listOfTags){
        NotesDataDao note = getNoteById(chatId);
        if(!StringUtil.isNullOrEmpty(category)) note.setCategory(category);
        if(!StringUtil.isNullOrEmpty(title)) note.setTitle(title);
        if(!StringUtil.isNullOrEmpty(data)) note.setData(data);
        note.setTags(listOfTags);
        return updateNote(note);
    }

    @Override
    public NotesDataDao getNoteById(String noteId){
        return notesRepo.getNoteById(noteId);
    }

    public boolean updateNote(NotesDataDao note){
        return notesRepo.updateNote(note);
    }

    @Override
    public boolean createNewNote(String category, String title, String data, List<String> listOfTags, String userId){
        NotesDataDao note = new NotesDataDao();
        note.setCategory(category);
        note.setTitle(title);
        note.setData(data);
        note.setTags(listOfTags);
        note.setUserId(userId);
        return notesRepo.createNote(note);
    }

    @Override
    public boolean deleteNoteById(String noteId, String userId){
        NotesDataDao note = getNoteById(noteId);
        if(note == null || !note.getUserId().equals(userId)){
            System.out.println("[WARNING] TileDataServiceImpl deleteNoteById: Note not found or userId mismatch.");
            return false;
        }
        if(!note.getUserId().equals(userId)){
            System.out.println("[WARNING] TileDataServiceImpl deleteNoteById: UserId mismatch.");
            return false;
        }
        return notesRepo.deleteNoteById(note);
    }
}
