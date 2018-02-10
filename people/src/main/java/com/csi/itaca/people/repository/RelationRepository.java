package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.PersonRelationEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface RelationRepository extends PagingAndSortingRepository<PersonRelationEntity, Long>
		,JpaSpecificationExecutor<PersonRelationEntity> {

}
