package com.csi.itaca.load.model.dao;

import com.csi.itaca.load.model.LoadProcess;
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
public class LoadProcessEntity implements LoadProcess {

    public static final String LOAD_PROCESS_ID = "loadProcessId";
    public static final String USER_ID = "userId";
    public static final String CREATED_TIMESTAMP = "createdTimestamp";
    public static final String PRELOAD_DEFINITION_ID = "preloadDefinitionId";
    public static final String USERNAME_LOAD_CANCEL ="UserNameLoadCancel";


    @Id
    @Column(name="LOAD_PROCESS_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LOAD_PROCESS_ID")
    @SequenceGenerator(name = "SEQ_LOAD_PROCESS_ID", sequenceName = "SEQ_LOAD_PROCESS_ID", allocationSize = 1)
    private Long loadProcessId;

    @Column(name="USER_ID")
    private Long userId;

    @Column(name="CREATED_TIMESTAMP")
    private Date createdTimestamp;

    @Column(name="PRELOAD_DEFINITION_ID")
    private Long preloadDefinitionId;

    @Column(name="USERNAME_LOAD_CANCEL")
    private String UsernameLoadCancel;


}
