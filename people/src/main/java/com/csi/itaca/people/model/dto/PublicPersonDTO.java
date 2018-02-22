package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.AddressFormat1;
import com.csi.itaca.people.model.PublicPerson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PublicPersonDTO implements PublicPerson {

	private Long IdPerPublicPerson;

	private String IdTypePublicPerson;

	private String IdPerson;
}
