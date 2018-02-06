package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.BankCardEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BankCardRepository extends PagingAndSortingRepository<BankCardEntity, Long>, JpaSpecificationExecutor<BankCardEntity>{

}
