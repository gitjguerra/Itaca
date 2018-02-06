package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.ContactType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactTypeDTO implements ContactType {
	
	private Long id;

	private String value;
}
