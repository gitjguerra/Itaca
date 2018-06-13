package com.csi.itaca.users.model.dao;

import com.csi.itaca.users.model.User2;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User2, Long> {
    User2 findByUsername(String username);
}
