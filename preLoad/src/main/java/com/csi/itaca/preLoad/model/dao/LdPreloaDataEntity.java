package com.csi.itaca.preLoad.model.dao;

/**
 * Created by Robert on 19/06/2018.
 */

import com.csi.itaca.preLoad.model.LdPreloaData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LD_PRELOAD_DATA")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LdPreloaDataEntity implements LdPreloaData {

    public static final String PRELOAD_DATA_ID = "preloadDataId";
    public static final String LOAD_FILE_ID = "loadFileId";
    public static final String LOADED_SUCCESSFULLY = "loadedSuccessfully";
    public static final String ROW_TYPE = "rowType";
    public static final String LINE_NUMBER = "lineNumber";
    public static final String DATA_COL1 = "dataCol1";
    public static final String DATA_COL2 = "dataCol2";
    public static final String DATA_COL3 = "dataCol3";


    @Id
    @Column(name="PRELOAD_DATA_ID")
    /* //NOTA: EN LOS SCRIPTS NO VEO LAS SECUENCIAS
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LD_PRELOAD_DATA")
    @SequenceGenerator(name = "SEQ_LD_PRELOAD_DATA", sequenceName = "SEQ_LD_PRELOAD_DATA", allocationSize = 1)
    */
    private Long preloadDataId;

    @Column(name="LOAD_FILE_ID")
    private Long loadFileId;

    @Column(name="LOADED_SUCCESSFULLY")
    private String loadedSuccessfully;

    @Column(name="ROW_TYPE")
    private Long rowType;

    @Column(name="LINE_NUMBER")
    private Long lineNumber;

    @Column(name="DATA_COL1")
    private String dataCol1;

    @Column(name="DATA_COL2")
    private String dataCol2;

    @Column(name="DATA_COL3")
    private String dataCol3;

}
