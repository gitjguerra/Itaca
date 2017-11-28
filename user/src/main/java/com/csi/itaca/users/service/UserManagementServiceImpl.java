package com.csi.itaca.users.service;

import com.csi.itaca.common.utils.jpa.JpaUtils;
import com.csi.itaca.common.utils.jpa.Order;
import com.csi.itaca.common.utils.jpa.Pagination;
import com.csi.itaca.common.utils.beaner.Beaner;
import com.csi.itaca.users.businessLogic.UserManagementBusinessLogic;
import com.csi.itaca.users.exception.UserNotFoundException;
import com.csi.itaca.users.model.UserConfig;
import com.csi.itaca.users.model.dao.UserConfigEntity;
import com.csi.itaca.users.model.dao.UserLanguageEntity;
import com.csi.itaca.users.model.dto.ChangePasswordDTO;
import com.csi.itaca.users.model.dao.UserEntity;
import com.csi.itaca.users.model.User;
import com.csi.itaca.users.model.dto.PersonalPreferencesDTO;
import com.csi.itaca.users.model.dto.UserConfigDTO;
import com.csi.itaca.users.model.dto.UserDTO;
import com.csi.itaca.users.model.filters.UserSearchFilterDTO;
import com.csi.itaca.users.repository.UserConfigRepository;
import com.csi.itaca.users.repository.UserRepository;
import com.csi.itaca.users.exception.InvalidCredentialsException;
import com.csi.itaca.users.exception.UserNotAuthorisedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.validation.Errors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.List;

@Service
public class UserManagementServiceImpl implements UserManagementService {

   private static final Logger logger = LoggerFactory.getLogger(UserManagementServiceImpl.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserConfigRepository userConfigRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserManagementBusinessLogic userManBusiness;

    @Autowired
    private Beaner beaner;

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public UserDTO auth(String username, String password) throws InvalidCredentialsException, UserNotAuthorisedException {
        UserEntity user = repository.findByUsernameAndPassword(username, password);

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
    @Transactional
    public Boolean updatePassword(ChangePasswordDTO passwordChange, Errors result) {

        UserEntity user = repository.findOne(passwordChange.getUserId());
        if (!userManBusiness.canChangeUserPassword(user, passwordChange, result)) {

            CriteriaBuilder cb = this.em.getCriteriaBuilder();
            CriteriaUpdate<UserEntity> update = cb.createCriteriaUpdate(UserEntity.class);
            Root root = update.from(UserEntity.class);
            update.set(UserEntity.PASSWORD, passwordChange.getNewPassword());
            update.where(cb.equal(root.get(UserEntity.ID), passwordChange.getUserId()));
            this.em.createQuery(update).executeUpdate();

            return true;
        }
        else {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean updatePersonalPreferences(PersonalPreferencesDTO preferences, Errors result) {

        UserEntity user = repository.findOne(preferences.getUserId());
        if (!userManBusiness.canChangeUserPreferences(user, preferences, result)) {

            CriteriaBuilder cb = this.em.getCriteriaBuilder();
            CriteriaUpdate<UserEntity> update = cb.createCriteriaUpdate(UserEntity.class);
            Root root = update.from(UserEntity.class);
            update.set(UserEntity.USER_LANGUAGE, beaner.transform(preferences.getUserLanguage(), UserLanguageEntity.class));
            update.where(cb.equal(root.get(UserEntity.ID), preferences.getUserId()));
            this.em.createQuery(update).executeUpdate();

            return true;
        }
        else {
            return false;
        }
    }

    @Override
    @Transactional
    public void saveUserConfig(UserConfig userConfigToSave) {
        userConfigRepository.save(beaner.transform(userConfigToSave, UserConfigEntity.class));
    }

    @Override
    public List<UserConfigDTO> getUserConfig(Long userId) {
        return getUserConfig(userId, null);
    }

    @Override
    public List<UserConfigDTO> getUserConfig(Long userId, Pagination pagination) {

        // where config.user_id = userId
        Specification<UserConfigEntity> spec = (root, query, cb) -> {
            return cb.equal(root.get(UserConfigEntity.USER).get(UserEntity.ID), userId);
        };

        // run query with pagination if we have it...
        List<? extends UserConfigEntity> usersResultList;
        if (pagination != null) {
            PageRequest pageRequest = new PageRequest(pagination.getPageNo() - 1, pagination.getItemsPerPage());
            usersResultList = userConfigRepository.findAll(spec, pageRequest).getContent();
        }
        else {
            usersResultList = userConfigRepository.findAll(spec);
        }

        return beaner.transform((List<UserConfigEntity>) usersResultList, UserConfigDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countUsers(UserSearchFilterDTO userFilter) {
        return repository.count(buildUserFilterSpec(userFilter));
    }


    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getUsers(UserSearchFilterDTO userFilter, Pagination pagination, Order order) {

        // configure filtering and ordering.
        Specification<UserEntity> spec = buildUserFilterSpec(userFilter);
        spec = JpaUtils.applyOrder(UserEntity.class, order, spec);

        Iterable<UserEntity> usersResultList;
        PageRequest pageRequest = JpaUtils.buildPageRequest(pagination);
        if (pageRequest != null) {
            usersResultList = repository.findAll(spec, pageRequest).getContent();
        }
        else {
            usersResultList = repository.findAll(spec);
        }

        return beaner.transform((List<UserEntity>) usersResultList, UserDTO.class);
    }

    /**
     * Constructs a filter specification based on the supplied filter.
     * @param userFilter
     * @return
     */
    private static Specification<UserEntity> buildUserFilterSpec(UserSearchFilterDTO userFilter) {
        Specification<UserEntity> spec = (root, query, cb) -> {
            Predicate p = null;
            p = cb.like(cb.lower(root.get(UserEntity.USERNAME)), "%" + userFilter.getUsername().toLowerCase() + "%");
            p = cb.and(p, cb.like(cb.lower(root.get(UserEntity.DESCRIPTION)), "%" + userFilter.getDescription().toLowerCase() + "%"));
            p = cb.and(p, cb.equal(root.get(UserEntity.BLOCKED), userFilter.getBlocked()));
            return p;
        };
        return spec;
    }

}
