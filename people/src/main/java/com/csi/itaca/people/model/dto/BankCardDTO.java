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

	private Long idBankCard;
	private Long idPersonDetail;
	private Long idCardType;
	private String card;
	private Boolean principal;
	private Boolean available;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
	private LocalDate expirationDate;
	private Long securityCode;
	private Long idBank;
=======
=======
>>>>>>> Stashed changes
	private Long idBank;
	private LocalDate expirationDate;
	private Long securityCode;

<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

}
