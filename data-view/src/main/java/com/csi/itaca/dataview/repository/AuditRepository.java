package com.csi.itaca.dataview.repository;

import com.csi.itaca.dataview.model.dao.AuditEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface AuditRepository extends PagingAndSortingRepository<AuditEntity, Long>
		,JpaSpecificationExecutor<AuditEntity> {

}
