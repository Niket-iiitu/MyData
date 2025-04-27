package com.MyData.Repository;

import com.MyData.Dao.AuditLogsDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class LoggingImpl implements Logging {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void logRequest(AuditLogsDao auditLogsDao) {
        try {
            entityManager.merge(auditLogsDao);
        } catch (Exception e) {
            System.out.println("[ERROR] TileDataRepoImpl createNewNote: Unable to create new note.");
            System.out.println(auditLogsDao.toString());
            e.printStackTrace();
        }
    }
}
