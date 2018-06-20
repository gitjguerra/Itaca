package com.csi.itaca.preLoad.model.dao;

import com.csi.itaca.preLoad.model.LdPreloadFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Robert on 20/06/2018.
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LD_PRELOAD_FILE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LdPreloadFileEntity implements LdPreloadFile{

    public static final String PRELOAD_FILE_ID = "preloadFileId";
    public static final String PRELOAD_DEFINITION_ID = "preloadDefinitionId";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String FILENAME_FORMAT_REGEX = "filenameFormatRegex";


    @Id
    @Column(name="PRELOAD_FILE_ID")
    /* //NOTA: EN LOS SCRIPTS NO VEO LAS SECUENCIAS
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LD_PRELOAD_FILE")
    @SequenceGenerator(name = "SEQ_LD_PRELOAD_FILE", sequenceName = "SEQ_LD_PRELOAD_FILE", allocationSize = 1)
    */
    private Long preloadFileId;

    @Column(name="PRELOAD_DEFINITION_ID")
    private Long preloadDefinitionId;

    @Column(name="NAME")
    private String name;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="FILENAME_FORMAT_REGEX")
    private String filenameFormatRegex;


}
