package com.csi.itaca.load.model.dto;

import com.csi.itaca.load.model.PreloadFile;
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
public class PreloadFileDTO implements PreloadFile {

    private Long preloadFileId;

    private PreloadDefinitionDTO preloadDefinitionId;

    private String name;

    private String description;

    private String filenameFormatRegex;

    private String FileType;

    private String FileSeparator;

    private String FileLoadOrder;
}
