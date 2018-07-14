package com.csi.itaca.load.model.dao;

/**
 * Created by Robert on 20/06/2018.
 */
import com.csi.itaca.load.model.PreloadFieldType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LD_PRELOAD_FIELD_TYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PreloadFieldTypeEntity implements PreloadFieldType {

    public static final String PRELOAD_FIELD_TYPE_ID = "preloadFieldTypeId";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String MIN_LENGTH = "minLength";
    public static final String MAX_LENGTH = "maxLength";
    public static final String REGEX = "regex";

    @Id
    @Column(name="PRELOAD_FIELD_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_PRELOAD_FIELD_TYPE_ID")
    @SequenceGenerator(name = "SEQ_PRELOAD_FIELD_TYPE_ID", sequenceName = "SEQ_PRELOAD_FIELD_TYPE_ID", allocationSize = 1)
    private Long preloadFieldTypeId;

    @Column(name="NAME")
    private String name;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="MIN_LENGTH")
    private Long minLength;

    @Column(name="MAX_LENGTH")
    private Long maxLength;

    @Column(name="REGEX")
    private String regex;



}
