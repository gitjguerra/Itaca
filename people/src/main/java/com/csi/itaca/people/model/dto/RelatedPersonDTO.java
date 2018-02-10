package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.PersonDetail;
import com.csi.itaca.people.model.RelatedPerson;
import com.csi.itaca.people.model.RelationType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class RelatedPersonDTO  implements RelatedPerson {
	
	private Long id;
	private RelationTypeDTO relationtype;
	private PersonDetailDTO personDetail;
	private PersonDetailDTO personRel;

	@Override
	public PersonDetail getPersonRel() {
		return personRel;
	}

	@Override
	public RelationType getRelationType() {
		return relationtype;
	}
}
