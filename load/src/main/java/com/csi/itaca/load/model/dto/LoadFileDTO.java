package com.csi.itaca.load.model.dto;

import com.csi.itaca.load.model.LoadFile;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode

/**
 * Created by Robert on 19/06/2018.
 */
public class LoadFileDTO implements LoadFile {

    private Long loadFileId;

    private Long loadProcessId;

    private String fileName;

    private Long fileSize;

    private String checksum;

    private Date preloadStartTime;

    private Date preloadEndTime;

    private Date loadStartTime;

    private Date loadEndTime;

    private Long statusCode;

    private String statusMessage;

    private Date userLoadCancel;

}
