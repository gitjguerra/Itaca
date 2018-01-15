package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.CivilStatusEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CivilStatusRepository extends PagingAndSortingRepository<CivilStatusEntity, Long>
										,JpaSpecificationExecutor<CivilStatusEntity> {

}
