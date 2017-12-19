package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.PersonStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonStatusDTO implements PersonStatus {

	private Long id;

	private String name;
	
	private boolean individual;
	
	private boolean company;
}
