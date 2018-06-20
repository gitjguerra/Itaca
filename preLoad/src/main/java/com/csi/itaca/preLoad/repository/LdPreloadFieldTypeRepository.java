package com.csi.itaca.preLoad.repository;

import com.csi.itaca.preLoad.model.dao.LdPreloadFieldTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 20/06/2018.
 */
public interface LdPreloadFieldTypeRepository extends PagingAndSortingRepository<LdPreloadFieldTypeEntity, Long>, JpaSpecificationExecutor<LdPreloadFieldTypeEntity> {
}
