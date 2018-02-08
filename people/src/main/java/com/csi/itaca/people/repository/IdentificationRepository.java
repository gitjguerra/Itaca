package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.IdentificationEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IdentificationRepository extends PagingAndSortingRepository<IdentificationEntity, Long>
										,JpaSpecificationExecutor<IdentificationEntity> {

}
