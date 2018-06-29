package com.csi.itaca.load.model.dao;

/**
 * Created by Robert on 19/06/2018.
 */
import com.csi.itaca.load.model.PreloadFieldDefinition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LD_PRELOAD_FIELD_DEFINITION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PreloadFieldDefinitionEntity implements PreloadFieldDefinition {

    public static final String PRELOAD_FIELD_DEFINITION_ID = "preloadFieldDefinitionId";
    public static final String PRELOAD_ROW_TYPE_ID = "preloadRowTypeId";
    public static final String COLUMN_NO = "columnNo";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRELOAD_FIELD_TYPE_ID = "preloadFieldTypeId";
    public static final String REGEX = "regex";
    public static final String REQUIRED = "required";
    public static final String REL_TYPE = "relType";
    public static final String REL_FIELD_DEFINITION_ID = "relFieldDefinitionId";
    public static final String REL_DB_TABLE_NAME = "relDbTableName";
    public static final String REL_DB_FIELD_NAME = "relDbFieldName";
    public static final String ERROR_SEVERITY = "errorSeverity";

    @Id
    @Column(name="PRELOAD_FIELD_DEFINITION_ID")
    /* //NOTA: EN LOS SCRIPTS NO VEO LAS SECUENCIAS
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LD_PRELOAD_FIELD_DEFINITION")
    @SequenceGenerator(name = "SEQ_LD_PRELOAD_FIELD_DEFINITION", sequenceName = "SEQ_LD_PRELOAD_FIELD_DEFINITION", allocationSize = 1)
    */
    private Long preloadFieldDefinitionId;

    @Column(name="PRELOAD_ROW_TYPE_ID")
    private Long preloadRowTypeId;

    @Column(name="COLUMN_NO")
    private Long columnNo;

    @Column(name="NAME")
    private String name;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="PRELOAD_FIELD_TYPE_ID")
    private Long preloadFieldTypeId;

    @Column(name="REGEX")
    private String regex;

    @Column(name="REQUIRED")
    private String required;

    @Column(name="REL_TYPE")
    private Long relType;

    @Column(name="REL_FIELD_DEFINITION_ID")
    private Long relFieldDefinitionId;

    @Column(name="REL_DB_TABLE_NAME")
    private String relDbTableName;

    @Column(name="REL_DB_FIELD_NAME")
    private String relDbFieldName;

    @Column(name="ERROR_SEVERITY")
    private Long errorSeverity;

}
