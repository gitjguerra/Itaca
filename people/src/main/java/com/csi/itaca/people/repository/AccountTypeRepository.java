package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.AccountTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountTypeRepository extends PagingAndSortingRepository<AccountTypeEntity, Long>
										,JpaSpecificationExecutor<AccountTypeEntity> {

}
