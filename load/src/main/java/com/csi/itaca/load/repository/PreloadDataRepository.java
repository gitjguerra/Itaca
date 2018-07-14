package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.dao.LoadFileEntity;
import com.csi.itaca.load.model.dao.LoadProcessEntity;
import com.csi.itaca.load.model.dao.PreloadDataEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadDataRepository extends PagingAndSortingRepository<PreloadDataEntity, Long>, JpaSpecificationExecutor<PreloadDataEntity> {

    List<PreloadDataEntity> findByLoadFileId(LoadFileEntity loadFileId);

   List<PreloadDataEntity> findByLoadFileId_LoadProcessId_LoadProcessIdAndLoadFileIdOrderByLineNumber(Long loadProcessId, LoadFileEntity loadFileId);



    List<PreloadDataEntity> findByLoadFileId_FileSize (Long LoadFileId);

}
