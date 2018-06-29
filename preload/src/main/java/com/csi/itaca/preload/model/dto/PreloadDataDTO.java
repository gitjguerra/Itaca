package com.csi.itaca.preload.model.dto;

import com.csi.itaca.preload.model.PreloadData;
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
public class PreloadDataDTO implements PreloadData {

    private Long preloadDataId;

    private Long loadFileId;

    private String loadedSuccessfully;

    private Long rowType;

    private Long lineNumber;

    private String dataCol1;

    private String dataCol2;

    private String dataCol3;

    public PreloadDataDTO() {
    }

    public PreloadDataDTO(Long preloadDataId, Long loadFileId, String loadedSuccessfully, Long rowType, Long lineNumber, String dataCol1, String dataCol2, String dataCol3) {
        this.preloadDataId = preloadDataId;
        this.loadFileId = loadFileId;
        this.loadedSuccessfully = loadedSuccessfully;
        this.rowType = rowType;
        this.lineNumber = lineNumber;
        this.dataCol1 = dataCol1;
        this.dataCol2 = dataCol2;
        this.dataCol3 = dataCol3;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("preloadDataId", this.preloadDataId)
                .append("loadFileId", this.loadFileId = loadFileId)
                .append("loadedSuccessfully", this.loadedSuccessfully)
                .append("rowType", this.rowType)
                .append("lineNumber", this.lineNumber)
                .append("dataCol1", this.dataCol1)
                .append("dataCol2", this.dataCol2)
                .append("dataCol3", this.dataCol3)
                .toString();
    }


}
