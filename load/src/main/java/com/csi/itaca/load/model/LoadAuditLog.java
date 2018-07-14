package com.csi.itaca.load.model;

/**
 * Created by Arturo on 01/07/2018.
 */
public interface LoadAuditLog {


     Long getLoadAuditLogId();

     LoadProcess getLoadProcessId();

     LoadRowOperation getLoadRowOperationId();

     LoadOperationType getLoadOperationTypeId();

     LoadAtomicOperation getAtomicOpCode();

     PreloadData getPreloadDataId();

     String getUserId();

     String getOperationTimestamp();

     String getDefinitiveValuesBeforeJson();

     String getDefinitiveValuesAfterJson();

     String getSqlCommandApplied();

}
