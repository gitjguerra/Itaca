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
@Table(name = "LD_LOAD_DEFINITIVE_FIELD")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LoadDefinitiveFieldEntity implements LoadDefinitiveField{


    public static final String LOAD_DEFINITIVE_FIELD_ID = "LoadDefinitiveFieldId";
    public static final String LOAD_DEFINITIVE_TABLE_ID = "LoadDefinitiveTableId";
    public static final String FIELD_NAME = "FieldName";
    public static final String IS_KEY_FIELD = "IsKeyField";


    @Id
    @Column(name="LOAD_DEFINITIVE_FIELD_ID")

    /* //NOTA: EN LOS SCRIPTS NO VEO LAS SECUENCIAS
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LD_ERROR_FIELD")
    @SequenceGenerator(name = "SEQ_LD_ERROR_FIELD", sequenceName = "SEQ_LD_ERROR_FIELD", allocationSize = 1)
    */
    private Long LoadDefinitiveFieldId;

    @Column(name = "LOAD_DEFINITIVE_TABLE_ID")
    private Long LoadDefinitiveTableId;

    @Column(name = "FIELD_NAME")
    private String FieldName;


    @Column(name = "IS_KEY_FIELD")
    private String IsKeyField;



}
