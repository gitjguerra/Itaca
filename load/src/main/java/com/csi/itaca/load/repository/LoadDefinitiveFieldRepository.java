package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.dao.LoadDefinitiveFieldEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 11/07/2018.
 */
public interface LoadDefinitiveFieldRepository extends PagingAndSortingRepository<LoadDefinitiveFieldEntity, Long>, JpaSpecificationExecutor<LoadDefinitiveFieldEntity> {

}
