package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.PerRegimeAmountsEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FiscalRegimeAmountsRepository extends PagingAndSortingRepository<PerRegimeAmountsEntity, Long>, JpaSpecificationExecutor<PerRegimeAmountsEntity>{

}
