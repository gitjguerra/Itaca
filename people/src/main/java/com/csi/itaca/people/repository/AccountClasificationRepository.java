package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.AccountClasificationEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountClasificationRepository extends PagingAndSortingRepository<AccountClasificationEntity, Long>
										,JpaSpecificationExecutor<AccountClasificationEntity> {

}
