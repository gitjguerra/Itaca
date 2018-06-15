package com.csi.itaca.dataview.model.dto;

import com.csi.itaca.dataview.model.Audit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class AuditDTO implements Audit {

    private Long id;
    private Date timeStamp;
    private String userName;
    private String operation;
    private String sqlCommand;
}
