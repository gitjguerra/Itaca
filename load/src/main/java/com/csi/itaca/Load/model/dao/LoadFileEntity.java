package com.csi.itaca.preLoad.model.dao;

/**
 * Created by Robert on 19/06/2018.
 */

import com.csi.itaca.preLoad.model.LdLoadFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LD_LOAD_FILE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LdLoadFileEntity implements LdLoadFile{

    public static final String LOAD_FILE_ID = "loadFileId";
    public static final String LOAD_PROCESS_ID = "loadProcessId";
    public static final String FILENAME = "fileName";
    public static final String FILE_SIZE = "fileSize";
    public static final String CHECKSUM = "checksum";
    public static final String PRELOAD_START_TIME = "preloadStartTime";
    public static final String PRELOAD_END_TIME = "preloadEndTime";
    public static final String LOAD_START_TIME = "loadStartTime";
    public static final String LOAD_END_TIME = "loadEndTime";
    public static final String STATUS_CODE = "statusCode";
    public static final String STATUS_MESSAGE = "statusMessage";
    public static final String USER_LOAD_CANCEL = "userLoadCancel";

    @Id
    @Column(name="LOAD_FILE_ID")
    /* //NOTA: EN LOS SCRIPTS NO VEO LAS SECUENCIAS
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LD_LOAD_FILE")
    @SequenceGenerator(name = "SEQ_LD_LOAD_FILE", sequenceName = "SEQ_LD_LOAD_FILE", allocationSize = 1)
    */
    private Long loadFileId;

    @Column(name = "LOAD_PROCESS_ID")
    private Long loadProcessId;

    @Column(name = "FILENAME")
    private String fileName;

    @Column(name = "FILE_SIZE")
    private Long fileSize;

    @Column(name = "CHECKSUM")
    private String checksum;

    @Column(name = "PRELOAD_START_TIME")
    private Date preloadStartTime;

    @Column(name = "PRELOAD_END_TIME")
    private Date preloadEndTime;

    @Column(name = "LOAD_START_TIME")
    private Date loadStartTime;

    @Column(name = "LOAD_END_TIME")
    private Date loadEndTime;

    @Column(name = "STATUS_CODE")
    private Long statusCode;

    @Column(name = "STATUS_MESSAGE")
    private String statusMessage;

    @Column(name = "USER_LOAD_CANCEL")
    private Date userLoadCancel;

}
