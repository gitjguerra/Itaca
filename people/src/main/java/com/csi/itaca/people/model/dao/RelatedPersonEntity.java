package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.RelatedPerson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PER_RELATION_PER")
public class RelatedPersonEntity implements RelatedPerson {
	public static final String ID = "id";
	public static final String ID_PERSON_DETAIL = "personDetail";
	public static final String ID_PERSON_DETAIL_REL = "personRelId";
	public static final String RELATION_TYPE = "relationTypeId";

	@Id
	@Column(name="RELATION_PER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_RELATION_PER")
	@SequenceGenerator(name = "SEQ_RELATION_PER", sequenceName = "SEQ_RELATION_PER", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PERSON_DETAIL_ID")
	private PersonDetailEntity personDetail;

	//@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name="PERSON_DETAIL2_ID")
	@Column(name="PERSON_DETAIL2_ID")
	private Long personRelId;

	//@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name="RELATION_TYPE_ID")
	@Column(name="RELATION_TYPE_ID")
	private Long relationTypeId;

}
