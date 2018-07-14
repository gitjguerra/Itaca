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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LD_LOAD_ATOMIC_OPERATION_SEQ")
    @SequenceGenerator(name = "LD_LOAD_ATOMIC_OPERATION_SEQ", sequenceName = "LD_LOAD_ATOMIC_OPERATION_SEQ", allocationSize = 1)
    private Long LoadAtomicOperationId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LOAD_OPERATION_TYPE_ID")
    private LoadOperationTypeEntity LoadOperationTypeId;

    @Column(name = "ATOMIC_OP_CODE")
    private String AtomicOpCode;

    @Column(name = "NAME")
    private String Name;

    @Column(name = "DESCRIPTION")
    private String Description;


}
