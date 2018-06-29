package com.csi.itaca.preload.model.dao;

import com.csi.itaca.preload.model.PreloadRowType;

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
@Table(name = "LD_PRELOAD_ROW_TYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PreloadRowTypeEntity implements PreloadRowType {

    public static final String PRELOAD_ROW_TYPE_ID = "preloadRowTypeId";
    public static final String PRELOAD_FILE_ID = "preloadFileId";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String IDENTIFIER_COLUMN_NO = "identifierColumnNo";
    public static final String IDENTIFIER_VALUE = "identifierValue";

    @Id
    @Column(name="PRELOAD_ROW_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LD_PRELOAD_ROW_TYPE_SEQ")
    @SequenceGenerator(name = "LD_PRELOAD_ROW_TYPE_SEQ", sequenceName = "LD_PRELOAD_ROW_TYPE_SEQ", allocationSize = 1)
    private Long preloadRowTypeId;

    @Column(name="PRELOAD_FILE_ID")
    private Long preloadFileId;

    @Column(name="NAME")
    private String name;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="IDENTIFIER_COLUMN_NO")
    private Long identifierColumnNo;

    @Column(name="IDENTIFIER_VALUE")
    private String identifierValue;



}
