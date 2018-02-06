package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.RelationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RelationTypeDTO implements RelationType {

	private Long id;

	private String literal;

}
