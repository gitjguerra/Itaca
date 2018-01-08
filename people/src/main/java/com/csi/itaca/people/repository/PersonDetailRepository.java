package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.PersonDetailEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonDetailRepository extends PagingAndSortingRepository<PersonDetailEntity, Long>
													,JpaSpecificationExecutor<PersonDetailEntity> {

}
