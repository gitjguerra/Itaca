package com.csi.itaca.preLoad.repository;

import com.csi.itaca.preLoad.model.dao.LdPreloadDefinitionEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadDefinitionRepository extends PagingAndSortingRepository<PreloadDefinitionEntity, Long>, JpaSpecificationExecutor<PreloadDefinitionEntity> {
}
