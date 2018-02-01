package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.AccountEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface AccountRepository extends PagingAndSortingRepository<AccountEntity, Long>
		,JpaSpecificationExecutor<AccountEntity> {

}
