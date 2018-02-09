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

	private Long personDetail;
<<<<<<< Updated upstream
	private String account;
	private Long accountClasification;
	private Long typeAccount;
	private Boolean principal;
=======
	private Long accountClasification;
	private Long typeAccount;
	private String account;
	private Boolean principal;	
>>>>>>> Stashed changes
	private Boolean available;
	private Long IdBank;

}
