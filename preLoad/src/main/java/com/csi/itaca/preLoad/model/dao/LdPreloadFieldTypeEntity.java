package com.csi.itaca.preLoad.model.dao;

/**
 * Created by Robert on 20/06/2018.
 */
import com.csi.itaca.preLoad.model.LdPreloadFieldType;
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
public class LdPreloadFieldTypeEntity implements LdPreloadFieldType {

    public static final String PRELOAD_FIELD_TYPE_ID = "preloadFieldTypeId";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String MIN_LENGTH = "minLength";
    public static final String MAX_LENGTH = "maxLength";
    public static final String REGEX = "regex";

    @Id
    @Column(name="PRELOAD_FIELD_TYPE_ID")
    /* //NOTA: EN LOS SCRIPTS NO VEO LAS SECUENCIAS
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LD_PRELOAD_FIELD_TYPE")
    @SequenceGenerator(name = "SEQ_LD_PRELOAD_FIELD_TYPE", sequenceName = "SEQ_LD_PRELOAD_FIELD_TYPE", allocationSize = 1)
    */
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
