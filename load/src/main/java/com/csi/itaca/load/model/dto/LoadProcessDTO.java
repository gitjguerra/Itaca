package com.csi.itaca.load.model.dto;

import com.csi.itaca.load.model.LoadProcess;
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
public class LoadProcessDTO implements LoadProcess {

    private Long loadProcessId;

    private Long userId;

    private Date createdTimestamp;

    private Long preloadDefinitionId;

    private String UsernameLoadCancel;


}
