package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.IndividualDetailEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IndividualDetailRepository extends PagingAndSortingRepository<IndividualDetailEntity, Long>
													,JpaSpecificationExecutor<IndividualDetailEntity> {

}
