package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.PersonStatusEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonStatusRepository extends PagingAndSortingRepository<PersonStatusEntity, Long>
										,JpaSpecificationExecutor<PersonStatusEntity> {

}
