package com.csi.itaca.preLoad.model.dao;

import com.csi.itaca.preLoad.model.LdLoadProcess;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LD_LOAD_PROCESS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

/**
 * Created by Robert on 19/06/2018.
 */
public class LdLoadProcessEntity implements LdLoadProcess {

    public static final String LOAD_PROCESS_ID = "loadProcessId";
    public static final String USER_ID = "userId";
    public static final String CREATED_TIMESTAMP = "createdTimestamp";
    public static final String PRELOAD_DEFINITION_ID = "preloadDefinitionId";


    @Id
    @Column(name="LOAD_PROCESS_ID")
    /* //NOTA: EN LOS SCRIPTS NO VEO LAS SECUENCIAS
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LD_LOAD_PROCESS")
    @SequenceGenerator(name = "SEQ_LD_LOAD_PROCESS", sequenceName = "SEQ_LD_LOAD_PROCESS", allocationSize = 1)
    */
    private Long loadProcessId;

    @Column(name="USER_ID")
    private Long userId;

    @Column(name="CREATED_TIMESTAMP")
    private Date createdTimestamp;

    @Column(name="PRELOAD_DEFINITION_ID")
    private Long preloadDefinitionId;
}
