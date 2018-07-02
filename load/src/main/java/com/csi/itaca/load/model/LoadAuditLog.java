package com.csi.itaca.load.model;

/**
 * Created by Arturo on 01/07/2018.
 */
public interface LoadAuditLog {


     Long LoadAuditLogId;

     Long LoadProcessId;

     String LoadRowOperationId;

     String LoadOperationTypeId;

     String AtomicOpCode;

     String PreloadDataId;

     String UserId;

     String OperationTimestamp;

     String DefinitiveValuesBeforeJson;

     String DefinitiveValuesAfterJson;

     String SqlCommandApplied;

}
