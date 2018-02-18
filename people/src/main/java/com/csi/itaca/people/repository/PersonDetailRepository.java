package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.PersonDetailEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PersonDetailRepository extends PagingAndSortingRepository<PersonDetailEntity, Long>
													,JpaSpecificationExecutor<PersonDetailEntity> {
}
