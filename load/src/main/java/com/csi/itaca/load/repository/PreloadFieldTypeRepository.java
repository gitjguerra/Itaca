package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.dao.PreloadFieldTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Robert on 20/06/2018.
 */
public interface PreloadFieldTypeRepository extends PagingAndSortingRepository<PreloadFieldTypeEntity, Long>, JpaSpecificationExecutor<PreloadFieldTypeEntity> {
}
