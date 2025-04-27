package com.MyData.Repository;

import com.MyData.Dao.NotesDataDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotesRepoImpl implements NotesRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<NotesDataDao> findByUserIdAndCategory(String userId, String category) {
        String jpql = "FROM NotesDataDao t WHERE t.userId = :userId AND t.category = :category";
        return entityManager.createQuery(jpql, NotesDataDao.class)
                .setParameter("userId", userId)
                .setParameter("category", category)
                .getResultList();
    }

    @Override
    @Transactional
    public List<String> getCategoriesByUserId(String userId) {
        String jpql = "SELECT DISTINCT t.category FROM NotesDataDao t WHERE t.userId = :userId";
        return entityManager.createQuery(jpql, String.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    @Transactional
    public NotesDataDao getNoteById(String noteId){
        String jpql = "FROM NotesDataDao t WHERE t.id = :id";
        return entityManager.createQuery(jpql, NotesDataDao.class)
                .setParameter("id", noteId)
                .getSingleResult();
    }

    @Override
    @Transactional
    public boolean updateNote(NotesDataDao note){
        try {
            entityManager.merge(note);
            return true;
        } catch (Exception e) {
            System.out.println("[ERROR] TileDataRepoImpl updateNote: Unable to update note.");
            System.out.println(note.toString());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean createNote(NotesDataDao note){
        try {
            entityManager.merge(note);
            return true;
        } catch (Exception e) {
            System.out.println("[ERROR] TileDataRepoImpl createNewNote: Unable to create new note.");
            System.out.println(note.toString());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteNoteById(NotesDataDao note){
        try {
            if(note != null){
                entityManager.remove(note);
                return true;
            }
            else{
                System.out.println("[ERROR] TileDataRepoImpl deleteNoteById: Note not found.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("[ERROR] TileDataRepoImpl deleteNoteById: Unable to delete note.");
            e.printStackTrace();
            return false;
        }
    }
}
