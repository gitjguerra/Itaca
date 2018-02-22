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

	private Long addressId;

	private String idpoblacion;

	private String idcodpostal;

	private String idtypevia;

	private String nombrevia;

	private String numerovia;

	private String complementos;

	@Override
	public String getidpoblacion() {
		return idpoblacion;
	}

	@Override
	public String getidcodpostal() {
		return idcodpostal;
	}

	@Override
	public String getidtypevia() {
		return idtypevia;
	}

	@Override
	public String getnombrevia() {
		return nombrevia;
	}

	@Override
	public String getnumerovia() {
		return numerovia;
	}

	@Override
	public String getcomplementos() {
		return complementos;
	}
}
