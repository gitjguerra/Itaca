package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.FiscalRegimeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DetPersonFiscalRegimeRepository extends PagingAndSortingRepository<FiscalRegimeEntity, Long>, JpaSpecificationExecutor<FiscalRegimeEntity>{


}
