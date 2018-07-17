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
@Table(name = "LD_ERROR_FIELD")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ErrorFieldEntity implements ErrorField{

    public static final String ERR_FIELDS_ID = "errFieldsId";
    public static final String PRELOAD_DATA_ID = "preloaDataId";
    public static final String PRELOAD_FIELD_DEFINITION_ID = "preloadFieldDefinitionId";
    public static final String VALIDATION_ERR_MSG = "validationErrMsg";

    @Id
    @Column(name="ERR_FIELDS_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_Pre_Def_Id")
    @SequenceGenerator(name = "SEQ_Pre_Def_Id", sequenceName = "SEQ_Pre_Def_Id", allocationSize = 1)
    private Long errFieldsId;

    @Column(name = "PRELOAD_DATA_ID")
    private Long preloaDataId;

    @Column(name = "PRELOAD_FIELD_DEFINITION_ID")
    private String preloadFieldDefinitionId;


    @Column(name = "VALIDATION_ERR_MSG")
    private String validationErrMsg;






}
