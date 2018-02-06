package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.CardTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CardTypeRepository extends PagingAndSortingRepository<CardTypeEntity, Long>, JpaSpecificationExecutor<CardTypeEntity>{

}
