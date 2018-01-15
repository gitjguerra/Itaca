package com.csi.itaca.common.repository;

import com.csi.itaca.common.model.dao.CountryEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CountryRepository extends PagingAndSortingRepository<CountryEntity, Long>
										,JpaSpecificationExecutor<CountryEntity> {

}
