package com.csi.itaca.users.repository;

import com.csi.itaca.users.model.dao.UserConfigEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserConfigRepository extends PagingAndSortingRepository<UserConfigEntity, Long>,
                                              JpaSpecificationExecutor<UserConfigEntity>{

}
