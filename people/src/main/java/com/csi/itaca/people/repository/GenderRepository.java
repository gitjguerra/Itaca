package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.GenderEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GenderRepository extends PagingAndSortingRepository<GenderEntity, Long>
										,JpaSpecificationExecutor<GenderEntity> {

}
