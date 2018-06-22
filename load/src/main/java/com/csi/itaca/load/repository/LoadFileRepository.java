package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.dao.LoadFileEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 19/06/2018.
 */
public interface LoadFileRepository extends PagingAndSortingRepository<LoadFileEntity, Long>, JpaSpecificationExecutor<LoadFileEntity> {
}
