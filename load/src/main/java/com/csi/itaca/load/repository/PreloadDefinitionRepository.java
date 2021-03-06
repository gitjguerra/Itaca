package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.dao.PreloadDefinitionEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadDefinitionRepository extends PagingAndSortingRepository<PreloadDefinitionEntity, Long>, JpaSpecificationExecutor<PreloadDefinitionEntity> {
    // Create query for find a limit errors
    //PreloadDefinitionEntity findByPreloadDefinitionEntity(Long preloadDefinitionId);
}
