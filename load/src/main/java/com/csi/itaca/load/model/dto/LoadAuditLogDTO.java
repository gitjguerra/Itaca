package com.csi.itaca.load.model.dto;

import com.csi.itaca.load.model.LoadAuditLog;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode

public class LoadAuditLogDTO implements LoadAuditLog {

   private Long LoadAuditLogId;

   private Long LoadProcessId;

   private String LoadRowOperationId;

   private String LoadOperationTypeId;

   private String AtomicOpCode;

   private String PreloadDataId;

   private String UserId;

   private String OperationTimestamp;

   private String DefinitiveValuesBeforeJson;

   private String DefinitiveValuesAfterJson;

   private String SqlCommandApplied;


}
