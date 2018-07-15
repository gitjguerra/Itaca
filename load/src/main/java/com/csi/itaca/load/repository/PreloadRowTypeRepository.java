package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.dao.PreloadRowTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Robert on 20/06/2018.
 */
public interface PreloadRowTypeRepository  extends PagingAndSortingRepository<PreloadRowTypeEntity, Long>, JpaSpecificationExecutor<PreloadRowTypeEntity> {
    @Query("SELECT p FROM PreloadRowTypeEntity p")
    List<PreloadRowTypeEntity> findPreloadRowTypeEntityList();
}
