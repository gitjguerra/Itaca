package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.CompanyPersonTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyPersonTypeRepository extends PagingAndSortingRepository<CompanyPersonTypeEntity, Long>
												     ,JpaSpecificationExecutor<CompanyPersonTypeEntity> {

}
