package com.csi.itaca.preLoad.model.dto;

import com.csi.itaca.preLoad.model.LdPreloadFile;
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
public class LdPreloadFileDTO implements LdPreloadFile {

    private Long preloadFileId;

    private Long preloadDefinitionId;

    private String name;

    private String description;

    private String filenameFormatRegex;
}
