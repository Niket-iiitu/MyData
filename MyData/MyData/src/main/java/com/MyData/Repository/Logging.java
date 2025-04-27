package com.MyData.Repository;

import com.MyData.Dao.AuditLogsDao;

public interface Logging {
    void logRequest(AuditLogsDao auditLogsDao);
}
