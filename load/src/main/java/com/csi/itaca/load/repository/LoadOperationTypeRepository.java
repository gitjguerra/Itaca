package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.dao.LoadOperationTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 05/07/2018.
 */
public interface LoadOperationTypeRepository extends PagingAndSortingRepository<LoadOperationTypeEntity, Long>, JpaSpecificationExecutor<LoadOperationTypeEntity> {

}