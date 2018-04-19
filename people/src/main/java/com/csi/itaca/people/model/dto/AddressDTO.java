package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.Address;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@JsonTypeName("ADDRESS")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY , property = "_formato")
public class AddressDTO implements Address {
	
	private Long id;
	
}
