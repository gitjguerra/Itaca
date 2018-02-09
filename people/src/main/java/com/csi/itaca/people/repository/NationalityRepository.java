package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.NationalityEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface NationalityRepository extends PagingAndSortingRepository<NationalityEntity, Long>, JpaSpecificationExecutor<NationalityEntity> {

}