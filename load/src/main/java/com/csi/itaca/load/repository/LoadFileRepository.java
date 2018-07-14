package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.LoadFile;
import com.csi.itaca.load.model.dao.LoadFileEntity;
import com.csi.itaca.load.model.dao.LoadProcessEntity;
import com.csi.itaca.load.model.dto.LoadFileDTO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Robert on 19/06/2018.
 */


public interface LoadFileRepository extends PagingAndSortingRepository<LoadFileEntity, Long>, JpaSpecificationExecutor<LoadFileEntity> {

    List<LoadFileEntity> findByloadProcessIdAndLoadFileId(LoadProcessEntity loadProcessId, Long loadFileId);


}
