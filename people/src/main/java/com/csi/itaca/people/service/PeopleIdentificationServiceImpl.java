package com.csi.itaca.people.service;

import com.csi.itaca.people.api.ErrorConstants;
import com.csi.itaca.people.businesslogic.PeopleManagementBusinessLogic;
import com.csi.itaca.people.model.dao.IdentificationEntity;
import com.csi.itaca.people.model.dto.IdentificationDTO;
import com.csi.itaca.people.repository.IdentificationRepository;
import com.csi.itaca.tools.utils.beaner.Beaner;
import com.csi.itaca.tools.utils.jpa.JpaUtils;
import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
public class PeopleIdentificationServiceImpl implements PeopleIdentificationService {

    @Autowired
    private IdentificationRepository identificationRepository;

    @Autowired
    private PeopleManagementBusinessLogic peopleManagementBusinessLogic;

    @Autowired
    private Beaner beaner;

    @Override
    @Transactional(readOnly = true)
    public List<IdentificationDTO> listIdentifications(Long personDetailId) {
        return listIdentifications(personDetailId,null,null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IdentificationDTO> listIdentifications(Long personDetailId, Pagination pagination, Order order) {

        Specification<IdentificationEntity> spec = (root, query, cb) -> {
            Predicate p = cb.equal(root.get(IdentificationEntity.DETAIL_PERSON_ID), personDetailId);
            if (order != null && order.getField() != null) {
                if(order.isAscending()){
                    query.orderBy(cb.asc(root.get(JpaUtils.getField(IdentificationEntity.class, order))));
                } else {
                    query.orderBy(cb.desc(root.get(JpaUtils.getField(IdentificationEntity.class, order))));
                }
            }

            return p;
        };

        List<? extends IdentificationEntity> identifications = null;
        if (pagination != null) {
            PageRequest pr = new PageRequest(pagination.getPageNo() - 1, pagination.getItemsPerPage());
            identifications = identificationRepository.findAll(spec, pr).getContent();
        }
        else {
            identifications = identificationRepository.findAll(spec);
        }

        return beaner.transform(identifications, IdentificationDTO.class);
    }

    @Override
    @Transactional
    public boolean deleteIdentification(Long identificationId, Errors errTracking) {

        IdentificationEntity identificationEntity = identificationRepository.findOne(identificationId);
        if (identificationEntity != null) {
            identificationRepository.delete(identificationEntity);
            return true;
        } else {
            errTracking.reject(ErrorConstants.DB_ITEM_NOT_FOUND);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public IdentificationDTO getIdentification(Long identificationId, Errors errTracking) {

        IdentificationEntity foundIdentification = identificationRepository.findOne(identificationId);
        if (foundIdentification!=null) {
            return beaner.transform(foundIdentification, IdentificationDTO.class);
        }
        else {
            errTracking.reject(ErrorConstants.DB_ITEM_NOT_FOUND);
            return null;
        }
    }

    @Override
    @Transactional
    public IdentificationDTO saveOrUpdateIdentification(IdentificationDTO identification, Errors errTracking)  {

        if (!peopleManagementBusinessLogic.isDuplicateIdentification(identification, errTracking)) {
            IdentificationEntity identificationEntityToSave = beaner.transform(identification, IdentificationEntity.class);
            IdentificationEntity identificationSavedEntity = identificationRepository.save(identificationEntityToSave);
            return beaner.transform(identificationSavedEntity, IdentificationDTO.class);
        }

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Long countIdentification(Long personDetailId) {

        Specification<IdentificationEntity> spec = (root, query, cb) -> {
            Predicate p = null;
            if (personDetailId != null) {
                p = cb.equal(root.get(IdentificationEntity.DETAIL_PERSON_ID), personDetailId);
            }
            return p;
        };
        return identificationRepository.count(spec);
    }

}
