package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.RelationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "per_relation_type")
public class RelationTypeEntity implements RelationType {
	
	public static final String ID = "id";
	public static final String LITERAL = "literal";
	
	@Id
	@Column(name="relation_type_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "relation_type_seq")
	@SequenceGenerator(name = "relation_type_seq", sequenceName = "relation_type_seq", allocationSize = 1)
	private Long id;

	@Column(name="value")
	private String literal;

}
