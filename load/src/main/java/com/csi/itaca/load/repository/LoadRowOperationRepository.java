package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.dao.LoadRowOperationEntity;
import com.csi.itaca.load.model.dao.PreloadRowTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Robert on 05/07/2018.
 */

public interface LoadRowOperationRepository extends PagingAndSortingRepository<LoadRowOperationEntity, Long>, JpaSpecificationExecutor<LoadRowOperationEntity> {

  // List<LoadRowOperationEntity> findByPreloadRowTypeIdOrderByOperationOrder (Long PreloadRowTypeId);

}
