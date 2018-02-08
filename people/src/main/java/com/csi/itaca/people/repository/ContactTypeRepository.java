package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.ContactTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContactTypeRepository extends PagingAndSortingRepository<ContactTypeEntity, Long>
												,JpaSpecificationExecutor<ContactTypeEntity> {

}
