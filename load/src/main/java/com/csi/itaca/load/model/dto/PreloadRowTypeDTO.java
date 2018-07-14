package com.csi.itaca.load.model.dto;

import com.csi.itaca.load.model.PreloadRowType;

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
public class PreloadRowTypeDTO implements PreloadRowType {

    private Long PreloadRowTypeId;

    private PreloadFileDTO preloadFileId;

    private String name;

    private String description;

    private Long identifierColumnNo;

    private String identifierValue;
}
