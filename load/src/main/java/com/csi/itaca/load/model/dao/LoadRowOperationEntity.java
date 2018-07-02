package com.csi.itaca.load.model.dao;

import com.csi.itaca.load.model.ErrorField;

/**
 * Created by Robert on 19/06/2018.
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LD_LOAD_ROW_OPERATION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LoadRowOperationEntity implements LoadRowOperation{


    public static final String LOAD_ROW_OPERATION_ID = "LoadRowOperationId";
    public static final String LOAD_OPERATION_TYPE_ID = "LoadOperationTypeId";
    public static final String PRELOAD_ROW_TYPE_ID = "PreloadRowTypeId";
    public static final String LOAD_DEFINITIVE_TABLE_ID = "LoadDefinitiveTableId";
    public static final String KEY_COLUMN_NO = "KeyColumnNo";
    public static final String OPERATION_ORDER = "OperationOrder";


    @Id
    @Column(name="LOAD_ROW_OPERATION_ID")

    /* //NOTA: EN LOS SCRIPTS NO VEO LAS SECUENCIAS
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LD_ERROR_FIELD")
    @SequenceGenerator(name = "SEQ_LD_ERROR_FIELD", sequenceName = "SEQ_LD_ERROR_FIELD", allocationSize = 1)
    */
    private Long LoadRowOperationId;

    @Column(name = "LOAD_OPERATION_TYPE_ID")
    private Long LoadOperationTypeId;

    @Column(name = "PRELOAD_ROW_TYPE_ID")
    private Long PreloadRowTypeId;

    @Column(name = "LOAD_DEFINITIVE_TABLE_ID")
    private Long LoadDefinitiveTableId;

    @Column(name = "KEY_COLUMN_NO")
    private Long KeyColumnNo;

    @Column(name = "OPERATION_ORDER")
    private Long OperationOrder;

}
