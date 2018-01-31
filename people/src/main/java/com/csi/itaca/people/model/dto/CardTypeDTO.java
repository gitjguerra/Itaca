package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.CardType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class CardTypeDTO implements CardType{

	private Long id;
	private String literal;

	public CardTypeDTO(Long id, String literal) {
		super();
		this.id = id;
		this.literal = literal;
	}

}
