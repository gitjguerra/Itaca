package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.AccountClasification;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class AccountClasificationDTO implements AccountClasification {

	private Long id;

	private String value;

	public AccountClasificationDTO(Long id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

}
