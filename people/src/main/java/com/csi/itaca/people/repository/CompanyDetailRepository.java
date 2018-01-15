package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.CompanyDetailEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyDetailRepository extends PagingAndSortingRepository<CompanyDetailEntity, Long>
													,JpaSpecificationExecutor<CompanyDetailEntity> {

}
