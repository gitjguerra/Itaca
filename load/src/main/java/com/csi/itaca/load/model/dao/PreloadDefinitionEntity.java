package com.csi.itaca.load.model.dao;

import com.csi.itaca.load.model.PreloadDefinition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LD_PRELOAD_DEFINITION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

/**
 * Created by Robert on 19/06/2018.
 */
public class PreloadDefinitionEntity implements PreloadDefinition{

    public static final String PRELOAD_DEFINITION_ID = "preloadDefinitionId";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String MAX_PRELOAD_LOW_ERRORS = "maxPreloadLowErrors";
    public static final String MAX_PRELOAD_MEDIUM_ERRORS = "maxPreloadMediumErrors";
    public static final String MAX_PRELOAD_HIGH_ERRORS = "maxPreloadHighErrors";
    public static final String LOAD_IF_PRELOAD_OK = "loadIfPreloadOk";
    public static final String AUTO_LOAD_DIRECTORY = "autoLoadDirectory";
    public static final String AUTO_CRON_SCHEDULING = "autoCronScheduling";
    public static final String AUTO_ENABLED = "autoEnable";

    @Id
    @Column(name="PRELOAD_DEFINITION_ID")
    /* //NOTA: EN LOS SCRIPTS NO VEO LAS SECUENCIAS
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LD_PRELOAD_DEFINITION")
    @SequenceGenerator(name = "SEQ_LD_PRELOAD_DEFINITION", sequenceName = "SEQ_LD_PRELOAD_DEFINITION", allocationSize = 1)
    */
    private Long preloadDefinitionId;

    @Column(name="NAME")
    private String name;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="MAX_PRELOAD_LOW_ERRORS")
    private Long maxPreloadLowErrors;

    @Column(name="MAX_PRELOAD_MEDIUM_ERRORS")
    private Long maxPreloadMediumErrors;

    @Column(name="MAX_PRELOAD_HIGH_ERRORS")
    private Long maxPreloadHighErrors;

    @Column(name="LOAD_IF_PRELOAD_OK")
    private String loadIfPreloadOk;

    @Column(name="AUTO_LOAD_DIRECTORY")
    private String autoLoadDirectory;

    @Column(name="AUTO_CRON_SCHEDULING")
    private String autoCronScheduling;

    @Column(name="AUTO_ENABLED")
    private Boolean autoEnable;

}
