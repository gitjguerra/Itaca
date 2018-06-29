package com.csi.itaca.preload.model.dto;

import com.csi.itaca.preload.model.PreloadFile;
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

    private Long preloadDefinitionId;

    private String name;

    private String description;

    private String filenameFormatRegex;
}
