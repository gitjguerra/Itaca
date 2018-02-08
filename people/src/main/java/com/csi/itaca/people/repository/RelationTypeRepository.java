package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.RelationTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RelationTypeRepository extends PagingAndSortingRepository<RelationTypeEntity, Long>
												,JpaSpecificationExecutor<RelationTypeEntity> {

}
