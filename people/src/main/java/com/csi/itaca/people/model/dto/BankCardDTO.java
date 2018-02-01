package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.Bank;
import com.csi.itaca.people.model.BankCard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class BankCardDTO implements BankCard {
	
	private Long id;
	private PersonDetailDTO personDetail;
	private LocalDate expirationDate;
	private CardTypeDTO cardType;
	private String card;
	private Boolean principal;	
	private Boolean available;
	private BankDTO bank;
	private Long securityCode;

	@Override
	public String getNumber() {
		return card;
	}

	@Override
	public Bank getBank() {
		return bank;
	}
}
