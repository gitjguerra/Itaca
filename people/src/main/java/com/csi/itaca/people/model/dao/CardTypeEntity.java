package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.CardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PER_CARD_TYPE")
public class CardTypeEntity implements CardType{
	
	public static final String ID = "id";
	public static final String LITERAL = "literal";
	
	@Id
	@Column(name="CARD_TYPE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CARD_TYPE")
	@SequenceGenerator(name = "SEQ_CARD_TYPE", sequenceName = "SEQ_CARD_TYPE", allocationSize = 1)
	private Long id;

	@Column(name="LITERAL")
	private String literal;

}
