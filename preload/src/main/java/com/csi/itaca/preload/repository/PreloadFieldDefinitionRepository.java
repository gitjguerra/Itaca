package com.csi.itaca.preload.repository;

import com.csi.itaca.preload.model.dao.PreloadFieldDefinitionEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadFieldDefinitionRepository extends PagingAndSortingRepository<PreloadFieldDefinitionEntity, Long>, JpaSpecificationExecutor<PreloadFieldDefinitionEntity> {
}
