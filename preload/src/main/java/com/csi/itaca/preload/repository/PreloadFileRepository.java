package com.csi.itaca.preload.repository;

import com.csi.itaca.preload.model.dao.PreloadFileEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 20/06/2018.
 */
public interface PreloadFileRepository extends PagingAndSortingRepository<PreloadFileEntity, Long>, JpaSpecificationExecutor<PreloadFileEntity> {
}
