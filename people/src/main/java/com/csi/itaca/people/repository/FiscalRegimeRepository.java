package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.FisFiscalRegimeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FiscalRegimeRepository extends PagingAndSortingRepository<FisFiscalRegimeEntity, Long>, JpaSpecificationExecutor<FisFiscalRegimeEntity>{


}
