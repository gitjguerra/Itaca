package com.csi.itaca.preLoad.model.dto;

import com.csi.itaca.preLoad.model.LdPreloaData;
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
public class LdPreloaDataDTO implements LdPreloaData {

    private Long preloadDataId;

    private Long loadFileId;

    private String loadedSuccessfully;

    private Long rowType;

    private Long lineNumber;

    private String dataCol1;

    private String dataCol2;

    private String dataCol3;
}
