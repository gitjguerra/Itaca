package com.csi.itaca.people.model.dto;

import com.csi.itaca.common.model.BaseModelImpl;
import com.csi.itaca.people.model.AddressFormat1;
import com.csi.itaca.people.model.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
public class AddressFormat1DTO implements AddressFormat1 {
	//AG
	private Long AddressId;
	private String Idpoblacion;
	private String Idcodpostal;
	private String Idtypevia;
	private String Nombrevia;
	private String Numerovia;
	private String Complementos;

	@Override
	public String getIdpoblacion() {
		return Idpoblacion;
	}

	@Override
	public String getIdcodpostal() {
		return Idcodpostal;
	}

	@Override
	public String getIdtypevia() {
		return Idtypevia;
	}

	@Override
	public String getNombrevia() {
		return Nombrevia;
	}

	@Override
	public String getNumerovia() {
		return Numerovia;
	}

	@Override
	public String getComplementos() {
		return Complementos;
	}
}
