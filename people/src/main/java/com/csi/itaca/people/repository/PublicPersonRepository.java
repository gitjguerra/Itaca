package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.AddressFormat1Entity;
import com.csi.itaca.people.model.dao.PublicPersonEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PublicPersonRepository extends PagingAndSortingRepository<PublicPersonEntity, Long>
										,JpaSpecificationExecutor<PublicPersonEntity> {

}
