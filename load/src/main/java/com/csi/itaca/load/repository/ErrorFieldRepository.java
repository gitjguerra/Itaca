package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.dao.ErrorFieldEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Robert on 19/06/2018.
 */
public interface ErrorFieldRepository extends PagingAndSortingRepository<ErrorFieldEntity, Long>, JpaSpecificationExecutor<ErrorFieldEntity>  {
    List<ErrorFieldEntity> findAll();
    ErrorFieldEntity saveAndFlush(ErrorFieldEntity entity);
}
