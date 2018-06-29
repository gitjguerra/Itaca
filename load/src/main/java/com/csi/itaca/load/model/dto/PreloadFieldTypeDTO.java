package com.csi.itaca.load.model.dto;

import com.csi.itaca.load.model.PreloadFieldType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Robert on 20/06/2018.
 */

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class PreloadFieldTypeDTO implements PreloadFieldType {

    private Long preloadFieldTypeId;

    private String name;

    private String description;

    private Long minLength;

    private Long maxLength;

    private String regex;
}
