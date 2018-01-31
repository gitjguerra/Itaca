package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.BankEntity;
import com.csi.itaca.people.model.dao.IDTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BankRepository extends PagingAndSortingRepository<BankEntity, Long>
										,JpaSpecificationExecutor<BankEntity> {

}
