package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.CivilStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CivilStatusDTO implements CivilStatus {

	private Long id;

	private String name;
}
