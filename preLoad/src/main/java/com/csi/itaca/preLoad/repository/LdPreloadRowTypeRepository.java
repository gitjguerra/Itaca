package com.csi.itaca.preLoad.repository;

import com.csi.itaca.preLoad.model.dao.LdPreloadRowTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 20/06/2018.
 */
public interface LdPreloadRowTypeRepository  extends PagingAndSortingRepository<LdPreloadRowTypeEntity, Long>, JpaSpecificationExecutor<LdPreloadRowTypeEntity> {
}
