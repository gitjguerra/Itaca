package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.Bank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class BankDTO implements Bank {
	
	private Long id;
	
	private String bankName;
	
	private Long draftBank;
	
	private String bic;
	
	private String code;

}
