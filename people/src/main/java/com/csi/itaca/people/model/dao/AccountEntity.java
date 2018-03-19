package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PER_BANK_ACCOUNT")
public class AccountEntity implements Account {
	
	public static final String ID = "id";
	public static final String PERSONDETAIL = "personDetailId";
	public static final String ACCOUNT = "account";
	public static final String ACCOUNTCLASIFIED = "accountClasificationId";
	public static final String ACCOUNTTYPE = "typeAccountId";
	public static final String PRINCIPAL = "principal";
	public static final String AVAILABLE = "available";
	public static final String BANK_ID = "bankId";

	@Id
	@Column(name="BANK_ACCOUNT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_BANK_ACCOUNT")
	@SequenceGenerator(name = "SEQ_BANK_ACCOUNT", sequenceName = "SEQ_BANK_ACCOUNT", allocationSize = 1)
	private Long id;

	@Column(name="PERSON_DETAIL_ID")
	private Long personDetailId;

	@Column(name="CLASIFICATION_ACCOUNT_ID")
	private Long accountClasificationId;

	@Column(name="TYPE_ACCOUNT_ID")
	private Long typeAccountId;

	@Column(name="BANK_ID")
	private Long bankId;

	@Column
	private String account;
	
	@Column
	private Boolean principal;
	
	@Column
	private Boolean available;

}
