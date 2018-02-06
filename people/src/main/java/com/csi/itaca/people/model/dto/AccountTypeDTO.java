package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.AccountType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class AccountTypeDTO implements AccountType {

	private Long id;

	private String valor;
}
