package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.PerRegimeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FiscalRegimeRepository extends PagingAndSortingRepository<PerRegimeEntity, Long>, JpaSpecificationExecutor<PerRegimeEntity>{


}
