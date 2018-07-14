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
@Table(name = "LD_LOAD_DEFINITIVE_FIELD")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LoadDefinitiveFieldEntity implements LoadDefinitiveField{


    public static final String LOAD_DEFINITIVE_FIELD_ID = "LoadDefinitiveFieldId";
    public static final String LOAD_DEFINITIVE_TABLE_ID = "LoadDefinitiveTableId";
    public static final String FIELD_NAME = "FieldName";
    public static final String IS_KEY_FIELD = "IsKeyField";


    @Id
    @Column(name="LOAD_DEFINITIVE_FIELD_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LD_LOAD_DEFINITIVE_FIELD_SEQ")
    @SequenceGenerator(name = "LD_LOAD_DEFINITIVE_FIELD_SEQ", sequenceName = "LD_LOAD_DEFINITIVE_FIELD_SEQ", allocationSize = 1)
     private Long LoadDefinitiveFieldId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "LOAD_DEFINITIVE_TABLE_ID")
    private LoadDefinitiveTableEntity LoadDefinitiveTableId;

    @Column(name = "FIELD_NAME")
    private String FieldName;


    @Column(name = "IS_KEY_FIELD")
    private String IsKeyField;



}
