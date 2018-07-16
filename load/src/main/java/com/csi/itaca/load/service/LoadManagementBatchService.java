package com.csi.itaca.load.service;

import com.csi.itaca.load.model.dto.PreloadDataDTO;

import java.util.List;

public interface LoadManagementBatchService {

    void insert(List<? extends PreloadDataDTO> preloadDatas);

    List<PreloadDataDTO> loadAllPreloadDatas();


}
