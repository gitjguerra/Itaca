package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.dao.PreloadDefinitionEntity;
import com.csi.itaca.load.model.dao.PreloadFileEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Robert on 20/06/2018.
 */
public interface PreloadFileRepository extends PagingAndSortingRepository<PreloadFileEntity, Long>, JpaSpecificationExecutor<PreloadFileEntity> {
    List<PreloadFileEntity> findByPreloadDefinitionId(PreloadDefinitionEntity preloadDefinitionId);
}
