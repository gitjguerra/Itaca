package com.csi.itaca.load.model;

/**
 * Created by Arturo on 01/07/2018.
 */
public interface LoadAuditLog {


     Long getLoadAuditLogId();

     Long getLoadProcessId();

     String getLoadRowOperationId();

     String getLoadOperationTypeId();

     String getAtomicOpCode();

     String getPreloadDataId();

     String getUserId();

     String getOperationTimestamp();

     String getDefinitiveValuesBeforeJson();

     String getDefinitiveValuesAfterJson();

     String getSqlCommandApplied();

}
