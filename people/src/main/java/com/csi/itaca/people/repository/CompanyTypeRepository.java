package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.CompanyTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyTypeRepository extends PagingAndSortingRepository<CompanyTypeEntity, Long>
										,JpaSpecificationExecutor<CompanyTypeEntity> {

}
