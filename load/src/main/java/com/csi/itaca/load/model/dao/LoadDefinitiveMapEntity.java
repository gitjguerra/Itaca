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
@Table(name = "LD_LOAD_DEFINITIVE_MAP")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LoadDefinitiveMapEntity implements LoadDefinitiveMap{



    public static final String LOAD_DEFINITIVE_MAP_ID = "LoadDefinitiveMapId";
    public static final String LOAD_ROW_OPERATION_ID = "LoadRowOperationId";
    public static final String ATOMIC_OP_CODE = "AtomicOpCode";
    public static final String COLUMN_NO = "ColumnNo";
    public static final String LOAD_DEFINITIVE_FIELD_ID = "LoadDefinitiveFieldId";


    @Id
    @Column(name="LOAD_DEFINITIVE_MAP_ID")

    /* //NOTA: EN LOS SCRIPTS NO VEO LAS SECUENCIAS
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LD_ERROR_FIELD")
    @SequenceGenerator(name = "SEQ_LD_ERROR_FIELD", sequenceName = "SEQ_LD_ERROR_FIELD", allocationSize = 1)
    */
    private Long LoadDefinitiveMapId;

    @Column(name = "LOAD_ROW_OPERATION_ID")
    private Long LoadRowOperationId;

    @Column(name = "ATOMIC_OP_CODE")
    private String AtomicOpCode;


    @Column(name = "COLUMN_NO")
    private String ColumnNo;


    @Column(name = "LOAD_DEFINITIVE_FIELD_ID")
    private String LoadDefinitiveFieldId;




}
