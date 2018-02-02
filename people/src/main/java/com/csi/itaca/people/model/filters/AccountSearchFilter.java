package com.csi.itaca.people.model.filters;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonTypeName("FILTRO_BUSCADOR_PERSONACUENTAS0")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY , property = "accountSerchFilter")
public class AccountSearchFilter {

	private Long id;
	private String number;

}
