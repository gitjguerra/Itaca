package com.csi.itaca.users.repository;

import com.csi.itaca.users.model.dao.UserLanguageEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserLanguageRepository extends PagingAndSortingRepository<UserLanguageEntity, Long>
											    ,JpaSpecificationExecutor<UserLanguageEntity> {


}
