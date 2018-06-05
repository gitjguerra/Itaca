package com.csi.itaca.dataview.model.dao;

import com.csi.itaca.dataview.model.Audit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "DAT_AUDIT")
public class AuditEntity implements Audit {
	
	public static final String ID = "id";
	public static final String TIMESTAMP = "timeStamp";
	public static final String USERNAME = "userName";
	public static final String OPERATION = "operation";
	public static final String SQLCOMMAND = "sqlCommand";

	@Id
	@Column(name="AUDIT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "DAT_AUDIT_SEQ")
	@SequenceGenerator(name = "DAT_AUDIT_SEQ", sequenceName = "DAT_AUDIT_SEQ", allocationSize = 1)
	private long id;

	@Column(name="AUDIT_TIMESTAMP")
	private Date timeStamp;

	@Column(name="AUDIT_USERNAME")
	private String userName;

	@Column(name="AUDIT_OPERATION")
	private String operation;

	@Column(name="AUDIT_SQL_COMMAND")
	private Long sqlCommand;

}
