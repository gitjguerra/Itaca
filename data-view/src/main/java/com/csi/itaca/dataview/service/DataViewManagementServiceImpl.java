package com.csi.itaca.dataview.service;

import com.csi.itaca.common.GlobalErrorConstants;
import com.csi.itaca.dataview.model.dao.AuditEntity;
import com.csi.itaca.dataview.model.dto.AuditDTO;
import com.csi.itaca.dataview.model.filters.AuditSearchFilter;
import com.csi.itaca.dataview.repository.AuditRepository;
import com.csi.itaca.tools.utils.beaner.Beaner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
@Service
public class DataViewManagementServiceImpl implements DataViewManagementService {

    final static Logger logger = Logger.getLogger(DataViewManagementServiceImpl.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AuditRepository repository;

    @Autowired
    private Beaner beaner;

    @Transactional(readOnly = true)
    AuditEntity getAuditEntity(Long id, Errors errTracking) {
        AuditEntity audit = repository.findOne(id);
        if (audit == null && errTracking != null) {
            errTracking.reject(GlobalErrorConstants.DB_ITEM_NOT_FOUND);
        }
        return audit;
    }

    @Override
    public List<AuditDTO> getAllAudit() {
        List<AuditEntity> audit = (List<AuditEntity>) repository.findAll();
        if (audit==null || audit.isEmpty()) {
            return Collections.emptyList();
        }else{
            return beaner.transform(audit, AuditDTO.class);
        }
    }

    @Override
    public List<? extends AuditDTO> findAudit(AuditSearchFilter filter, BindingResult errTracking) {
        Specification<AuditEntity> spec = (root, query, cb) -> {
            Predicate p = null;
            if (filter != null) {
                p = cb.equal(root.get(AuditEntity.ID), filter.getId());
            }
            return p;
        };

        List<? extends AuditDTO> audit = beaner.transform(repository.findAll(spec), AuditDTO.class);
        if (audit==null || audit.isEmpty()) {
            return Collections.emptyList();
        }
        return beaner.transform(audit, AuditDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteAudit(long id, Errors errTracking) {
        AuditEntity auditToDelete = getAuditEntity(id, errTracking);
        repository.delete(auditToDelete);
    }

    @Override
    public AuditDTO getAudit(long id, Errors errTracking) {
        AuditEntity auditEntity = repository.findOne(id);
        if (auditEntity == null && errTracking != null) {
            errTracking.reject(GlobalErrorConstants.DB_ITEM_NOT_FOUND);
        }
        return beaner.transform(auditEntity, AuditDTO.class);
    }

    @Override
    public long countAudit(long id) {
        Specification<AuditEntity> spec = (root, query, cb) -> {
            Predicate p = null;
            if (id != 0) {
                p = cb.equal(root.get(AuditEntity.ID), id);
            }
            return p;
        };
        return repository.count(spec);
    }

    @Override
    public void auditTransaction(AuditDTO dto) {
        AuditEntity auditEntity = new AuditEntity();
        //auditEntity.setId(dto.getId());    sequence on db
        auditEntity.setOperation(dto.getOperation());
        auditEntity.setSqlCommand(dto.getSqlCommand());
        auditEntity.setTimeStamp(dto.getTimeStamp());
        auditEntity.setUserName(dto.getUserName());

        repository.save(auditEntity);
        //entityManager.flush();
        //entityManager.clear();
    }

}
