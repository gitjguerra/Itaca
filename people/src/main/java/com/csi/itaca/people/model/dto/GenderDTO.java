package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GenderDTO implements Gender {

	private Long id;

	private String name;

	private Boolean male;

	private Boolean female;

	private Boolean other;
}
