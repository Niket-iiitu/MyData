package com.MyData.Repository;

import com.MyData.Dao.TileDataDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TileDataRepoImpl implements TileDataRepo{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<TileDataDao> findByUserIdAndCategory(String userId, String category) {
        String jpql = "FROM TileDataDao t WHERE t.userId = :userId AND t.category = :category";
        return entityManager.createQuery(jpql, TileDataDao.class)
                .setParameter("userId", userId)
                .setParameter("category", category)
                .getResultList();
    }

    @Override
    @Transactional
    public List<String> getCategoriesByUserId(String userId) {
        String jpql = "SELECT DISTINCT t.category FROM TileDataDao t WHERE t.userId = :userId";
        return entityManager.createQuery(jpql, String.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
