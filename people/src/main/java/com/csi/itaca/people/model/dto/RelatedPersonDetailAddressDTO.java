package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.RelatedPersonDetailAddress;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class RelatedPersonDetailAddressDTO implements RelatedPersonDetailAddress {

	private Long id;
	
	private AddressTypeDTO AddressType;

	private PersonDetailDTO personDetail;
	
	private AddressDTO address;
	
}
