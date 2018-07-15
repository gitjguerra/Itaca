package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.dao.PreloadFieldDefinitionEntity;
import com.csi.itaca.load.model.dto.PreloadFieldDefinitionDTO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadFieldDefinitionRepository extends PagingAndSortingRepository<PreloadFieldDefinitionEntity, Long>, JpaSpecificationExecutor<PreloadFieldDefinitionEntity> {
    @Query("SELECT p FROM PreloadFieldDefinitionEntity p")
    List<PreloadFieldDefinitionEntity>  findFieldDefinitionEntityList();
}
