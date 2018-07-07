package com.csi.itaca.load.model.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.csi.itaca.load.model.*;
import javax.persistence.*;
/**
 * Created by Robert on 19/06/2018.
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LD_LOAD_AUDIT_LOG")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LoadAuditLogEntity implements LoadAuditLog{


    public static final String LOAD_AUDIT_LOG_ID = "LoadAuditLogId";
    public static final String LOAD_PROCESS_ID = "LoadProcessId";
    public static final String LOAD_ROW_OPERATION_ID = "LoadRowOperationId";
    public static final String LOAD_OPERATION_TYPE_ID = "LoadOperationTypeId";
    public static final String ATOMIC_OP_CODE = "AtomicOpCode";
    public static final String PRELOAD_DATA_ID = "PreloadDataId";
    public static final String USER_ID = "UserId";
    public static final String OPERATION_TIMESTAMP = "OperationTimestamp";
    public static final String DEFINITIVE_VALUES_BEFORE_JSON = "DefinitiveValuesBeforeJson";
    public static final String DEFINITIVE_VALUES_AFTER_JSON = "DefinitiveValuesAfterJson";
    public static final String SQL_COMMAND_APPLIED = "SqlCommandApplied";

    @Id
    @Column(name="LOAD_AUDIT_LOG_ID")

    /* //NOTA: EN LOS SCRIPTS NO VEO LAS SECUENCIAS
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LD_ERROR_FIELD")
    @SequenceGenerator(name = "SEQ_LD_ERROR_FIELD", sequenceName = "SEQ_LD_ERROR_FIELD", allocationSize = 1)
    */
    private Long LoadAuditLogId;

    @Column(name = "LOAD_PROCESS_ID")
    private Long LoadProcessId;

    @Column(name = "LOAD_ROW_OPERATION_ID")
    private String LoadRowOperationId;


    @Column(name = "LOAD_OPERATION_TYPE_ID")
    private String LoadOperationTypeId;


    @Column(name = "ATOMIC_OP_CODE")
    private String AtomicOpCode;

    @Column(name = "PRELOAD_DATA_ID")
    private String PreloadDataId;

    @Column(name = "USER_ID")
    private String UserId;

    @Column(name = "OPERATION_TIMESTAMP")
    private String OperationTimestamp;

    @Column(name = "DEFINITIVE_VALUES_BEFORE_JSON")
    private String DefinitiveValuesBeforeJson;

    @Column(name = "DEFINITIVE_VALUES_AFTER_JSON")
    private String DefinitiveValuesAfterJson;

    @Column(name = "SQL_COMMAND_APPLIED")
    private String SqlCommandApplied;


}
