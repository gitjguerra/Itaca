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
<<<<<<< Updated upstream
	public static final String PERSONDETAIL = "personDetail";
	public static final String ACCOUNT = "account";
	public static final String ACCOUNTCLASIFIED = "accountClasification";
	public static final String ACCOUNTTYPE = "typeAccount";
	public static final String PRINCIPAL = "principal";
	public static final String AVAILABLE = "available";
	public static final String ID_BANK = "IdBank";
=======
	public static final String ACCOUNT = "account";
	public static final String ACCOUNTTYPE = "id_type_account";
	public static final String PERSONDETAIL = "id_person_detail";
	public static final String ACCOUNTCLASIFIED = "id_account_clasification";
	public static final String PRINCIPAL = "principal";
	public static final String AVAILABLE = "available";
	public static final String ID_BANK = "id_bank";
>>>>>>> Stashed changes

	@Id
	@Column(name="ID_BANK_ACCOUNT")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_BANK_ACCOUNT")
	@SequenceGenerator(name = "SEQ_BANK_ACCOUNT", sequenceName = "SEQ_BANK_ACCOUNT", allocationSize = 1)
	private Long id;

	@Column(name="ID_PERSON_DETAIL")
	private Long personDetail;

	@Column(name="ID_CLASIFICATION_ACCOUNT")
	private Long accountClasification;

	@Column(name="ID_TYPE_ACCOUNT")
	private Long typeAccount;

<<<<<<< Updated upstream
=======
	@Column(name="ID_BANK")
	private Long idBank;

>>>>>>> Stashed changes
	@Column
	private String account;
	
	@Column
	private Boolean principal;
	
	@Column
	private Boolean available;

<<<<<<< Updated upstream
	@Column(name="ID_BANK")
	private Long idBank;

=======
>>>>>>> Stashed changes
}
