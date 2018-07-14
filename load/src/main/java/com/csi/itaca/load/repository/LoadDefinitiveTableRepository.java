package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.dao.LoadDefinitiveTableEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 11/07/2018.
 */
public interface LoadDefinitiveTableRepository extends PagingAndSortingRepository<LoadDefinitiveTableEntity, Long>, JpaSpecificationExecutor<LoadDefinitiveTableEntity> {

}
