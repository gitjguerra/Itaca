package com.csi.itaca.load.model;

import com.csi.itaca.load.model.dto.PreloadDataDTO;

import java.util.List;

/**
 * Created by jguerra on 03/07/2018.
 */
public interface PreloadDataDao {
    void insert(List<? extends PreloadDataDTO> preloadDatas);
    List<PreloadDataDTO> loadAllPreloadDatas();
}
