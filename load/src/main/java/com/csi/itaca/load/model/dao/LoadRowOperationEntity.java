package com.csi.itaca.load.model.dao;

import com.csi.itaca.load.model.LoadRowOperation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Robert on 05/07/2018.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LD_LOAD_ROW_OPERATION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LoadRowOperationEntity implements LoadRowOperation {

   private static final String LOAD_ROW_OPERATION_ID = "LoadRowOperationId";
   private static final String LOAD_OPERATION_TYPE_ID = "loadOperationTypeId";
   private static final String PRELOAD_ROW_TYPE_ID = "PreloadRowTypeId";
   private static final String LOAD_DEFINITIVE_TABLE_ID = "LoadDefinitiveTableId";
   private static final String KEY_COLUMN_NO = "KeyColumnNo";
   private static final String OPERATION_ORDER = "OperationOrder";


    @Id
    @Column(name="LOAD_ROW_OPERATION_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LD_LOAD_ROW_OPERATION_SEQ")
    @SequenceGenerator(name = "LD_LOAD_ROW_OPERATION_SEQ", sequenceName = "LD_LOAD_ROW_OPERATION_SEQ", allocationSize = 1)
    private Long LoadRowOperationId;

    //@OneToOne(fetch=FetchType.LAZY)
    @Column(name="LOAD_OPERATION_TYPE_ID")
    //private LoadOperationTypeEntity LoadOperationTypeId;
    private Long LoadOperationTypeId;

    //@OneToOne(fetch=FetchType.LAZY)
    @Column(name="PRELOAD_ROW_TYPE_ID")
    //private PreloadRowTypeEntity PreloadRowTypeId;
    private Long PreloadRowTypeId;

   // @OneToOne(fetch=FetchType.LAZY)
    @Column(name="LOAD_DEFINITIVE_TABLE_ID")
    //private LoadDefinitiveTableEntity loadDefinitiveTableId;
    private Long loadDefinitiveTableId;

    @Column(name="KEY_COLUMN_NO")
    private Long KeyColumnNo;

    @Column(name="OPERATION_ORDER")
    private Long OperationOrder;


}
