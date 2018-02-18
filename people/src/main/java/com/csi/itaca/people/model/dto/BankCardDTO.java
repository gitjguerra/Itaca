package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.BankCard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class BankCardDTO implements BankCard {

	private Long bankCardId;
	private Long personDetailId;
	private Long cardTypeId;
	private String card;
	private Boolean principal;
	private Boolean available;
	private LocalDate expirationDate;
	private Long securityCode;
	private Long bankId;

}
