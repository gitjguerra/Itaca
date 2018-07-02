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
@Table(name = "LD_LOAD_DEFINITIVE_TABLE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LoadDefinitiveTableEntity implements LoadDefinitiveTable{


    public static final String LOAD_DEFINITIVE_TABLE_ID = "LoadDefinitiveTableId";
    public static final String TABLE_NAME = "TableName";



    @Id
    @Column(name="LOAD_DEFINITIVE_TABLE_ID")

    /* //NOTA: EN LOS SCRIPTS NO VEO LAS SECUENCIAS
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LD_ERROR_FIELD")
    @SequenceGenerator(name = "SEQ_LD_ERROR_FIELD", sequenceName = "SEQ_LD_ERROR_FIELD", allocationSize = 1)
    */
    private Long LoadDefinitiveTableId;

    @Column(name = "TABLE_NAME")
    private Long TableName;

}
