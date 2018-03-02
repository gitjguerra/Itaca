package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.PersonDetail;
import com.csi.itaca.people.model.RelatedPerson;
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
	private Long personDetailId;
	private Long personRelId;
	private Long relationTypeId;

}
