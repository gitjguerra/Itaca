package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.Account;
import com.csi.itaca.people.model.Bank;
import com.csi.itaca.people.model.PersonDetail;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class AccountDTO implements Account {
	
	private Long id;

	private PersonDetailDTO personDetail;
	private AccountClasificationDTO accountClasification;
	private AccountTypeDTO accountType;
	private String account;
	private Boolean principal;	
	private Boolean available;
	private Bank bank;

	public AccountDTO(Long id, PersonDetailDTO personDetail, AccountClasificationDTO accountClasification, AccountTypeDTO accountType, String account, Boolean principal, Boolean available, Bank bank) {
		super();
		this.id = id;
		this.personDetail = personDetail;
		this.accountClasification = accountClasification;
		this.accountType = accountType;
		this.account = account;
		this.principal = principal;
		this.available = available;
		this.bank = bank;
	}

	@Override
	public PersonDetail getPersonDetail() {
		return null;
	}
}
