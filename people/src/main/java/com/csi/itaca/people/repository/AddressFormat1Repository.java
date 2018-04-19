package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.AddressFormat1Entity;
import com.csi.itaca.people.model.dao.GenderEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
//address  //AG
public interface AddressFormat1Repository extends PagingAndSortingRepository<AddressFormat1Entity, Long>
										,JpaSpecificationExecutor<AddressFormat1Entity> {

}
