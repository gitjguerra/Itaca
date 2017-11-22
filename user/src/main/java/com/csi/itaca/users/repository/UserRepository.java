package com.csi.itaca.users.repository;

import com.csi.itaca.users.model.dao.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>
										,JpaSpecificationExecutor<UserEntity> {

	UserEntity findByUsernameAndPassword(String username, String password);

	UserEntity findByUsername(String username);
}
