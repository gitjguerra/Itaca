package com.csi.itaca.load.model.dto;

import com.csi.itaca.load.model.PreloadFieldDefinition;
import com.csi.itaca.load.model.PreloadFieldDefinition_OLD;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Setter
@Getter
@EqualsAndHashCode
/**
 * Created by Robert on 19/06/2018.
 */
public class PreloadFieldDefinitionDTO_OLD implements PreloadFieldDefinition_OLD {

    private Long preloadFieldDefinitionId;

    private Long preloadRowTypeId;

    private Long columnNo;

    private Long Length;

    private String name;

    private String description;

    private PreloadFieldTypeDTO preloadFieldTypeId;

    private String regex;

    private String required;

    private Long relType;

    private Long relFieldDefinitionId;

    private String relDbTableName;

    private String relDbFieldName;

    private Long errorSeverity;

    public PreloadFieldDefinitionDTO_OLD() {
    }

    public PreloadFieldDefinitionDTO_OLD(Long preloadFieldDefinitionId, Long preloadRowTypeId, Long columnNo, Long Length, String name,
                                         String description, PreloadFieldTypeDTO preloadFieldTypeId, String regex, String required, Long relType
            , Long relFieldDefinitionId, String relDbTableName, String relDbFieldName, Long errorSeverity) {
        this.preloadFieldDefinitionId = preloadFieldDefinitionId;
        this.preloadRowTypeId = preloadRowTypeId;
        this.columnNo = columnNo;
        this.Length = Length;
        this.name = name;
        this.description = description;
        this.preloadFieldTypeId = preloadFieldTypeId;
        this.regex = regex;
        this.required = required;
        this.relType = relType;
        this.relFieldDefinitionId = relFieldDefinitionId;
        this.relDbTableName = relDbTableName;
        this.relDbFieldName = relDbFieldName;
        this.errorSeverity = errorSeverity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("preloadFieldDefinitionId", this.preloadFieldDefinitionId)
                .append("preloadRowTypeId", this.preloadRowTypeId)
                .append("columnNo", this.columnNo)
                .append("Length", this.Length)
                .append("name", this.name)
                .append("description", this.description)
                .append("preloadFieldTypeId", this.preloadFieldTypeId)
                .append("regex", this.regex)
                .append("required", this.required)
                .append("relFieldDefinitionId", this.relFieldDefinitionId)
                .append("relDbTableName", this.relDbTableName)
                .append("relDbFieldName", this.relDbFieldName)
                .append("errorSeverity", this.errorSeverity)
                .toString();
    }

}
