package com.csi.itaca.preload.repository;

import com.csi.itaca.preload.model.dao.PreloadFieldTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 20/06/2018.
 */
public interface PreloadFieldTypeRepository extends PagingAndSortingRepository<PreloadFieldTypeEntity, Long>, JpaSpecificationExecutor<PreloadFieldTypeEntity> {
}
