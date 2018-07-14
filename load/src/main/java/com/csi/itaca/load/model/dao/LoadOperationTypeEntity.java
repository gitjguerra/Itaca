package com.csi.itaca.load.model.dao;

import com.csi.itaca.load.model.LoadOperationType;
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
@Table(name = "LD_LOAD_OPERATION_TYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LoadOperationTypeEntity implements LoadOperationType {

    private static final String LOAD_OPERATION_TYPE_ID = "LoadOperationTypeId";
    private static final String NAME  = "Name";
    private static final String DESCRIPTION  = "Description";

    @Id
    @Column(name="LOAD_OPERATION_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LD_LOAD_OPERATION_TYPE_SEQ")
    @SequenceGenerator(name = "LD_LOAD_OPERATION_TYPE_SEQ", sequenceName = "LD_LOAD_OPERATION_TYPE_SEQ", allocationSize = 1)
    private Long LoadOperationTypeId;

    @Column(name = "NAME")
    private String Name;

    @Column(name = "DESCRIPTION")
    private String Description;

}
