package com.csi.itaca.load.model.dto;

import com.csi.itaca.load.model.PreloadRowType;
import com.csi.itaca.load.model.PreloadRowType_OLD;
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
public class PreloadRowTypeDTO_OLD implements PreloadRowType_OLD {

    private Long PreloadRowTypeId;

    private Long preloadFileId;

    private String name;

    private String description;

    private Long identifierColumnNo;

    private String identifierValue;
}
