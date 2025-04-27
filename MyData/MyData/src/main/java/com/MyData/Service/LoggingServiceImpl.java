package com.MyData.Service;

import com.MyData.Dao.AuditLogsDao;
import com.MyData.Repository.Logging;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggingServiceImpl implements LoggingService {
    @Autowired
    Logging logging;

    @Override
    @Transactional
    public void logRequest(String request, String response, String transactionType, String userId, String status){
        try{
            AuditLogsDao auditLogsDao = new AuditLogsDao();
            auditLogsDao.setRequest(request);
            auditLogsDao.setResponse(response);
            auditLogsDao.setTransactionType(transactionType);
            auditLogsDao.setUserId(Integer.parseInt(userId));
            auditLogsDao.setStatus(status);
            auditLogsDao.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
            logging.logRequest(auditLogsDao);
        }
        catch (Exception e){
            System.out.println("Error logging request: " + e.getMessage());
        }
    }
}
