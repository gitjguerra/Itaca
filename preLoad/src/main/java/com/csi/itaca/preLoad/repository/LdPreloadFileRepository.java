package com.csi.itaca.preLoad.repository;

import com.csi.itaca.preLoad.model.dao.LdPreloadFileEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 20/06/2018.
 */
public interface LdPreloadFileRepository extends PagingAndSortingRepository<LdPreloadFileEntity, Long>, JpaSpecificationExecutor<LdPreloadFileEntity> {
}
