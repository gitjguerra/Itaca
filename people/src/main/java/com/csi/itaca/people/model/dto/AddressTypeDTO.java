package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.AddressType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class AddressTypeDTO implements AddressType {

	private Long id;
	
	@NotNull
	private String valor;
	
}
