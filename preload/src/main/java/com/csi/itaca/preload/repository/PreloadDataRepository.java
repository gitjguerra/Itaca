package com.csi.itaca.preload.repository;

import com.csi.itaca.preload.model.dao.PreloadDataEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadDataRepository extends PagingAndSortingRepository<PreloadDataEntity, Long>, JpaSpecificationExecutor<PreloadDataEntity> {
}
