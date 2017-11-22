package com.csi.itaca.users.service;

import com.csi.itaca.common.utils.beaner.Beaner;
import com.csi.itaca.users.businessLogic.UserManagementBusinessLogic;
import com.csi.itaca.users.exception.UserNotFoundException;
import com.csi.itaca.users.model.dao.UserEntity;
import com.csi.itaca.users.model.User;
import com.csi.itaca.users.model.dto.UserDTO;
import com.csi.itaca.users.repository.UserRepository;
import com.csi.itaca.users.exception.InvalidCredentialsException;
import com.csi.itaca.users.exception.UserNotAuthorisedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserManagementServiceImpl implements UserManagementService {

   private static final Logger logger = LoggerFactory.getLogger(UserManagementServiceImpl.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserManagementBusinessLogic userManBusiness;

    @Autowired
    private Beaner beaner;

    @Override
    @Transactional(readOnly = true)
    public UserDTO auth(String username, String password) throws InvalidCredentialsException, UserNotAuthorisedException {
        User user = repository.findByUsernameAndPassword(username, password);

        if (user == null) {
            logger.info("User ("+username+"). Login attempt failed - Bad credentials.");
            throw new InvalidCredentialsException();
        }
        else if (!userManBusiness.isUserAuthorisedToLogOn(user)) {
            logger.info("User ("+username+"). Login attempt failed - Not authorised.");
            throw new UserNotAuthorisedException();
        }

        logger.info("User ("+username+"). Login successful.");
        return beaner.transform(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        Iterable<UserEntity> usersResultList = repository.findAll();
        return beaner.transform((List<UserEntity>) usersResultList, UserDTO.class);
    }

    @Override
    public UserDTO getUser(String username) throws UserNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return beaner.transform(user, UserDTO.class);
    }

    @Override
    public void saveUser(User user) {
        UserEntity userToSave = beaner.transform(user, UserEntity.class);
        repository.save(userToSave);
    }

    @Override
    public void deleteUser(String username) throws UserNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        repository.findOne(user.getId());
    }

    @Override
    public List<UserDTO> getAllUsers(int pageSize, int pageNo) {
        return null;
    }

    @Override
    public List<UserDTO> findByNameEmail(String searchText) {
        return null;
    }

    @Override
    public List<UserDTO> findByNameEmail(String searchText, int pageSize, int pageNo) {
        return null;
    }


}
