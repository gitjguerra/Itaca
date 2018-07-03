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
@Table(name = "LD_LOAD_OPERATION_TYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LoadOperationTypeEntity implements LoadOperationType{


    public static final String LOAD_OPERATION_TYPE_ID = "LoadOperationTypeId";
    public static final String NAME = "Name";
    public static final String DESCRIPTION = "Description";



    @Id
    @Column(name="LOAD_OPERATION_TYPE_ID")

    /* //NOTA: EN LOS SCRIPTS NO VEO LAS SECUENCIAS
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LD_ERROR_FIELD")
    @SequenceGenerator(name = "SEQ_LD_ERROR_FIELD", sequenceName = "SEQ_LD_ERROR_FIELD", allocationSize = 1)
    */
    private Long LoadOperationTypeId;

    @Column(name = "NAME")
    private Long Name;

    @Column(name = "DESCRIPTION")
    private Long Description;

}
