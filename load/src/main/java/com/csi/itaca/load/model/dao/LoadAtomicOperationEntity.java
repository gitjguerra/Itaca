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
@Table(name = "LD_LOAD_ATOMIC_OPERATION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LoadAtomicOperationEntity implements LoadAtomicOperation{

    public static final String LOAD_ATOMIC_OPERATION_ID = "LoadAtomicOperationId";
    public static final String LOAD_OPERATION_TYPE_ID = "LoadOperationTypeId";
    public static final String ATOMIC_OP_CODE = "AtomicOpCode";
    public static final String NAME = "Name";
    public static final String DESCRIPTION = "Description";

    @Id
    @Column(name="LOAD_ATOMIC_OPERATION_ID")

    /* //NOTA: EN LOS SCRIPTS NO VEO LAS SECUENCIAS
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LD_ERROR_FIELD")
    @SequenceGenerator(name = "SEQ_LD_ERROR_FIELD", sequenceName = "SEQ_LD_ERROR_FIELD", allocationSize = 1)
    */
    private Long LoadAtomicOperationId;

    @Column(name = "LOAD_OPERATION_TYPE_ID")
    private Long LoadOperationTypeId;

    @Column(name = "ATOMIC_OP_CODE")
    private String AtomicOpCode;


    @Column(name = "NAME")
    private String Name;


    @Column(name = "DESCRIPTION")
    private String Description;



}
