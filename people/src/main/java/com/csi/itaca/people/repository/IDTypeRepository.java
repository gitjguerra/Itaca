package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.IDTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IDTypeRepository extends PagingAndSortingRepository<IDTypeEntity, Long>
										,JpaSpecificationExecutor<IDTypeEntity> {

}
