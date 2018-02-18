package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.PersonEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PeopleRepository extends PagingAndSortingRepository<PersonEntity, Long>
										,JpaSpecificationExecutor<PersonEntity> {
}
