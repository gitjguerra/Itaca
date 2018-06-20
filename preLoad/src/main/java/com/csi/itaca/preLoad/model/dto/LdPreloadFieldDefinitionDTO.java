package com.csi.itaca.preLoad.model.dto;

import com.csi.itaca.preLoad.model.LdPreloadFieldDefinition;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode

/**
 * Created by Robert on 19/06/2018.
 */
public class LdPreloadFieldDefinitionDTO implements LdPreloadFieldDefinition {

    private Long preloadFieldDefinitionId;

    private Long preloadRowTypeId;

    private Long columnNo;

    private String name;

    private String description;

    private Long preloadFieldTypeId;

    private String regex;

    private String required;

    private Long relType;

    private Long relFieldDefinitionId;

    private String relDbTableName;

    private String relDbFieldName;

    private Long errorSeverity;

}
