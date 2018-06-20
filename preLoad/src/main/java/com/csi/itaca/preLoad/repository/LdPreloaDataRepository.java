package com.csi.itaca.preLoad.repository;

import com.csi.itaca.preLoad.model.dao.LdPreloaDataEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 19/06/2018.
 */
public interface LdPreloaDataRepository extends PagingAndSortingRepository<LdPreloaDataEntity, Long>, JpaSpecificationExecutor<LdPreloaDataEntity> {
}
