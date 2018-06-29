package com.csi.itaca.preload.repository;

import com.csi.itaca.preload.model.dao.PreloadRowTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 20/06/2018.
 */
public interface PreloadRowTypeRepository  extends PagingAndSortingRepository<PreloadRowTypeEntity, Long>, JpaSpecificationExecutor<PreloadRowTypeEntity> {
}
