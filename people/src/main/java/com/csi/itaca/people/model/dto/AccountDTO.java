package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.Account;
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

	private Long personDetailId;
	private String account;
	private Long accountClasificationId;
	private Long typeAccountId;
	private Boolean principal;
	private Boolean available;
	private Long bankId;

}
