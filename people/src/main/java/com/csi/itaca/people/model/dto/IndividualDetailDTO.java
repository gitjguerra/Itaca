package com.csi.itaca.people.model.dto;


import com.csi.itaca.people.model.IndividualDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IndividualDetailDTO extends PersonDetailDTO implements IndividualDetail {

	private String name1;
	
	private String name2;
	
	private String surname1;
	
	private String surname2;
	
	private CivilStatusDTO civilStatus;
	
	private PersonStatusDTO personStatus;
	
}
