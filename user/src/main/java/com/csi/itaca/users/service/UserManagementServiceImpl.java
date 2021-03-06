package com.csi.itaca.users.service;

import com.csi.itaca.tools.utils.jpa.JpaUtils;
import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import com.csi.itaca.tools.utils.beaner.Beaner;
import com.csi.itaca.users.api.ErrorConstants;
import com.csi.itaca.users.businessLogic.UserManagementBusinessLogic;
import com.csi.itaca.users.model.UserConfig;
import com.csi.itaca.users.model.dao.UserConfigEntity;
import com.csi.itaca.users.model.dao.UserLanguageEntity;
import com.csi.itaca.users.model.dto.*;
import com.csi.itaca.users.model.dao.UserEntity;
import com.csi.itaca.users.model.User;
import com.csi.itaca.users.model.filters.UserSearchFilterDTO;
import com.csi.itaca.users.repository.UserConfigRepository;
import com.csi.itaca.users.repository.UserLanguageRepository;
import com.csi.itaca.users.repository.UserRepository;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.validation.Errors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service(value = "userService")
public class UserManagementServiceImpl implements UserManagementService, UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserManagementServiceImpl.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserConfigRepository userConfigRepository;

    @Autowired
    private UserLanguageRepository userLanguageRepository;

    @Autowired
    private UserManagementBusinessLogic userManBusiness;

    @Autowired
    private Beaner beaner;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public UserDTO auth(String username, String password, Errors errTracking) {
        UserEntity user = repository.findByUsernameAndPassword(username, password);

        if (user == null) {
            logger.info("User (" + username + "). Login attempt failed - Invalid credentials.");
            errTracking.reject(ErrorConstants.VALIDATION_INVALID_CREDENTIALS);
        } else if (!userManBusiness.isUserAuthorisedToLogOn(user)) {
            logger.info("User (" + username + "). Login attempt failed - Not authorised.");
            errTracking.reject(ErrorConstants.VALIDATION_USER_NOT_AUTHORISED);
        }

        if (!errTracking.hasErrors()) {
            logger.info("User (" + username + "). Login successful.");
        }
        return beaner.transform(user, UserDTO.class);
    }

    @Override
    public UserDTO getUser(String username, Errors errTracking) {
        User user = getUserEntity(username, errTracking);
        return beaner.transform(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Long id, Errors errTracking) {
        User user = getUserEntity(id, errTracking);
        return beaner.transform(user, UserDTO.class);
    }

    @Transactional(readOnly = true)
    private UserEntity getUserEntity(String username, Errors errTracking) {
        UserEntity user = repository.findByUsername(username);
        if (user == null && errTracking != null) {
            errTracking.reject(ErrorConstants.VALIDATION_USER_NOT_FOUND);
        }
        return user;
    }

    @Transactional(readOnly = true)
    private UserEntity getUserEntity(Long id, Errors errTracking) {
        UserEntity user = repository.findById(id);
        if (user == null && errTracking != null) {
            errTracking.reject(ErrorConstants.VALIDATION_USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    @Transactional
    public UserDTO createUpdateUser(UserDTO userToSave, Errors errTracking) {

        if (userToSave.isActiveUser()) {
            userToSave.setCompanyEndDate(null);
        } else {
            userToSave.setCompanyEndDate(LocalDate.now());
            userToSave.setBlockedUser(true);
        }
        // TODO: better to implement a create user & update user methods.
        UserEntity userToSaveEntity = beaner.transform(userToSave, UserEntity.class);

        if (userToSaveEntity.isBlockedUser()) {
            userToSaveEntity.setBlockedDate(LocalDate.now());
        } else {
            userToSaveEntity.setBlockedDate(null);
        }

        if (userToSave.getId() == null) {
            // create
            userToSaveEntity.setCompanyStartDate(LocalDate.now());
            entityManager.persist(userToSaveEntity);
        } else {
            //update the entered fields
            userToSaveEntity.setUsername(userToSave.getUsername());
            userToSaveEntity.setFirstName(userToSave.getFirstName());
            userToSaveEntity.setLastName(userToSave.getLastName());
            userToSaveEntity.setEmail(userToSave.getEmail());
            userToSaveEntity.setDescription(userToSave.getDescription());
            userToSaveEntity.setCompanyCode(userToSave.getCompanyCode());
            UserLanguageEntity userLanguageEntity = beaner.transform(userToSave.getUserLanguage(), UserLanguageEntity.class);
            userToSaveEntity.setUserLanguage(userLanguageEntity);
            repository.save(userToSaveEntity);
        }

        return beaner.transform(userToSaveEntity, UserDTO.class);
    }

    @Override
    public void deleteUser(String username, Errors errTracking) {
        User user = getUserEntity(username, errTracking);
        if (user != null) {
            repository.delete(user.getId());
        }
    }

    @Override
    public void deleteUserById(Long id, Errors errTracking) {
        User user = getUserEntity(id, errTracking);
        if (user != null) {
            repository.delete(user.getId());
        }
    }

    @Override
    @Transactional
    public Boolean changePassword(ChangePasswordDTO passwordChange, Errors errTracking) {

        UserEntity user = getUserEntity(passwordChange.getUsername(), errTracking);
        if (!userManBusiness.canChangeUserPassword(user, passwordChange, errTracking)) {

            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaUpdate<UserEntity> update = cb.createCriteriaUpdate(UserEntity.class);
            Root root = update.from(UserEntity.class);
            update.set(UserEntity.PASSWORD, passwordChange.getNewPassword());
            update.where(cb.equal(root.get(UserEntity.USERNAME), passwordChange.getUsername()));
            this.entityManager.createQuery(update).executeUpdate();

            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean updatePersonalPreferences(PersonalPreferencesDTO preferences, Errors errTracking) {

        UserEntity user = repository.findOne(preferences.getUserId());
        if (!userManBusiness.canChangeUserPreferences(user, preferences, errTracking)) {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaUpdate<UserEntity> update = cb.createCriteriaUpdate(UserEntity.class);
            Root root = update.from(UserEntity.class);
            update.set(UserEntity.USER_LANGUAGE, beaner.transform(preferences.getUserLanguage(), UserLanguageEntity.class));
            update.where(cb.equal(root.get(UserEntity.ID), preferences.getUserId()));
            entityManager.createQuery(update).executeUpdate();

            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public void saveUserConfig(UserConfig userConfigToSave, Errors errTracking) {
        userConfigRepository.save(beaner.transform(userConfigToSave, UserConfigEntity.class));
    }

    @Override
    public List<UserConfigDTO> getUserConfig(String username, Errors errTracking) {
        return getUserConfig(username, null, errTracking);
    }

    @Override
    public List<UserConfigDTO> getUserConfig(String username, Pagination pagination, Errors errTracking) {

        UserEntity user = getUserEntity(username, errTracking);

        if (user != null) {
            // where config.user_id = userId
            Specification<UserConfigEntity> spec = (root, query, cb) -> {
                return cb.equal(root.get(UserConfigEntity.USER_TABLE).get(UserEntity.ID), user.getId());
            };

            // run query with pagination if we have it...
            List<? extends UserConfigEntity> usersResultList;
            if (pagination != null) {
                PageRequest pageRequest = new PageRequest(pagination.getPageNo() - 1, pagination.getItemsPerPage());
                usersResultList = userConfigRepository.findAll(spec, pageRequest).getContent();
            } else {
                usersResultList = userConfigRepository.findAll(spec);
            }

            return beaner.transform(usersResultList, UserConfigDTO.class);
        }

        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public Long countUsers(UserSearchFilterDTO userFilter) {
        return repository.count(buildUserFilterSpec(userFilter));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getUsers() {
        return getUsers(null, null, null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getUsers(UserSearchFilterDTO userFilter, Pagination pagination, Order order) {

        // Configure filtering and ordering...
        Specification<UserEntity> spec = buildUserFilterSpec(userFilter);
        spec = JpaUtils.applyOrder(UserEntity.class, order, spec);

        Iterable<UserEntity> usersResultList;
        PageRequest pageRequest = JpaUtils.buildPageRequest(pagination);
        if (pageRequest != null) {
            usersResultList = repository.findAll(spec, pageRequest).getContent();
        } else {
            usersResultList = repository.findAll(spec);
        }

        return beaner.transform((List<UserEntity>) usersResultList, UserDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserLanguageDTO> getUserLanguages() {
        Iterable<UserLanguageEntity> userLanguages = userLanguageRepository.findAll();
        return beaner.transform((List<UserLanguageEntity>) userLanguages, UserLanguageDTO.class);
    }

    /**
     * Constructs a filter specification based on the supplied filter.
     *
     * @param userFilter filter to apply.
     * @return Specification of the user filter.
     */
    private static Specification<UserEntity> buildUserFilterSpec(UserSearchFilterDTO userFilter) {
        Specification<UserEntity> spec = (root, query, cb) -> {
            Predicate p = null;
            if (userFilter != null) {
                p = cb.like(cb.lower(root.get(UserEntity.USERNAME)), "%" + userFilter.getUsername().toLowerCase() + "%");
                p = cb.and(p, cb.like(cb.lower(root.get(UserEntity.DESCRIPTION)), "%" + userFilter.getDescription().toLowerCase() + "%"));
                p = cb.and(p, cb.equal(root.get(UserEntity.BLOCKED), userFilter.getBlockedUser()));
            }
            return p;
        };
        return spec;
    }

    /**
     * Service is used for take information of user for login (Role)
     * @param userId
     * @return new org.springframework.security.core.userdetails.User(user.getUsername(), ((UserEntity) user).getPassword(), ROLE_AUTHORITY)
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = repository.findByUsername(userId);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        String ROLE = user.getRoleDesc();
        List<SimpleGrantedAuthority> ROLE_AUTHORITY = Arrays.asList(new SimpleGrantedAuthority(ROLE));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), ((UserEntity) user).getPassword(), ROLE_AUTHORITY);
    }

    // ************* Deprecated ***********************
    private List<SimpleGrantedAuthority> getAuthority() {
        // Extract the user role on the database
        //*******************************************************************************
        String ROLE = "ROLE_ADMIN"; //"ROLE_ADMIN"; //"ROLE_X";
        return Arrays.asList(new SimpleGrantedAuthority(ROLE));
    }

}
