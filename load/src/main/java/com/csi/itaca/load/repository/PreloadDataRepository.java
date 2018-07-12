package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.dao.PreloadDataEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.LinkedHashMap;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadDataRepository extends PagingAndSortingRepository<PreloadDataEntity, Long>, JpaSpecificationExecutor<PreloadDataEntity> {

}
