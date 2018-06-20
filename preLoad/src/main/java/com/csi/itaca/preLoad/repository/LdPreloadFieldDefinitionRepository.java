package com.csi.itaca.preLoad.repository;

import com.csi.itaca.preLoad.model.dao.LdPreloadFieldDefinitionEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 19/06/2018.
 */
public interface LdPreloadFieldDefinitionRepository extends PagingAndSortingRepository<LdPreloadFieldDefinitionEntity, Long>, JpaSpecificationExecutor<LdPreloadFieldDefinitionEntity> {
}
