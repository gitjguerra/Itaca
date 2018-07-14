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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LD_LOAD_DEFINITIVE_MAP_SEQ")
    @SequenceGenerator(name = "LD_LOAD_DEFINITIVE_MAP_SEQ", sequenceName = "LD_LOAD_DEFINITIVE_MAP_SEQ", allocationSize = 1)
    private Long LoadDefinitiveMapId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LOAD_ROW_OPERATION_ID")
    private LoadRowOperationEntity LoadRowOperationId;


    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ATOMIC_OP_CODE")
    private LoadAtomicOperationEntity AtomicOpCode;

    @Column(name = "COLUMN_NO")
    private Long ColumnNo;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LOAD_DEFINITIVE_FIELD_ID")
    private LoadDefinitiveFieldEntity LoadDefinitiveFieldId;




}
