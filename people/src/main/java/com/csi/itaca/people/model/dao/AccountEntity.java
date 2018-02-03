package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.Account;
import com.csi.itaca.people.model.AccountClasification;
import com.csi.itaca.people.model.AccountType;
import com.csi.itaca.people.model.PersonDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PER_BANK_ACCOUNT")
public abstract class AccountEntity implements Account {
	
	public static final String ID = "id";
	public static final String ACCOUNT = "account";
	public static final String ACCOUNTTYPE = "id_account_type";
	public static final String PERSONDETAIL = "id_person_detail";
	public static final String ACCOUNTCLASIFIED = "id_account_clasification";
	public static final String PRINCIPAL = "principal";
	public static final String AVAILABLE = "available";
	public static final String ID_BANK = "id_bank";

	@Id
	@Column(name="ID_BANK_ACCOUNT")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_BANK_ACCOUNT")
	@SequenceGenerator(name = "SEQ_BANK_ACCOUNT", sequenceName = "SEQ_BANK_ACCOUNT", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERSON_DETAIL")
	private PersonDetailEntity persondetail;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CLASIFICATION_ACCOUNT")
	private AccountClasificationEntity accountclasification;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TYPE_ACCOUNT")
	private AccountTypeEntity accounttype;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_BANK")
	private BankEntity bank;

	@Column
	private String account;
	
	@Column
	private Boolean principal;
	
	@Column
	private Boolean available;

}
