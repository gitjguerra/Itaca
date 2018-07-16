package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.dao.PreloadFieldDefinitionEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadFieldDefinitionRepository extends PagingAndSortingRepository<PreloadFieldDefinitionEntity, Long>, JpaSpecificationExecutor<PreloadFieldDefinitionEntity> {
    List<PreloadFieldDefinitionEntity> findByPreloadRowTypeId (Long preloadRowTypeId);
}
