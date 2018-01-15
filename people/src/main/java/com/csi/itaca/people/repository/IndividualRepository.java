package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.IndividualEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IndividualRepository extends PagingAndSortingRepository<IndividualEntity, Long>
										,JpaSpecificationExecutor<IndividualEntity> {

}
