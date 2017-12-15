package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.CompanyEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LegalPeopleRepository extends PagingAndSortingRepository<CompanyEntity, Long>
										,JpaSpecificationExecutor<CompanyEntity> {

}
