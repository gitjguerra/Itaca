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

   private LoadProcessDTO LoadProcessId;

   private LoadRowOperationDTO LoadRowOperationId;

   private LoadOperationTypeDTO LoadOperationTypeId;

   private LoadAtomicOperationDTO AtomicOpCode;

   private PreloadDataDTO PreloadDataId;

   private String UserId;

   private String OperationTimestamp;

   private String DefinitiveValuesBeforeJson;

   private String DefinitiveValuesAfterJson;

   private String SqlCommandApplied;


}
