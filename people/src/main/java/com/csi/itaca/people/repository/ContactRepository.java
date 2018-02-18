package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.ContactEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContactRepository extends PagingAndSortingRepository<ContactEntity, Long>
										,JpaSpecificationExecutor<ContactEntity> {

}
