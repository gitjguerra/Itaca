package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.RelationDetailPersonTaxRegistry;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class RelationDetailPersonTaxRegistryDTO implements RelationDetailPersonTaxRegistry {
	
	private Long id;
	private PersonDetailDTO personDetail;
	private RelationDetailPersonTaxRegistryDTO relationDetailPersonTaxRegistry;
	private Long annuity;
	private LocalDate effectDate;

	@Override
	public RelationDetailPersonTaxRegistry getTaxRegimeAmounts() {
		return relationDetailPersonTaxRegistry;
	}
}
