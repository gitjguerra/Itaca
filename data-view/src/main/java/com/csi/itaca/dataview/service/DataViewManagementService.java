package com.csi.itaca.dataview.service;

import com.csi.itaca.dataview.model.dto.AuditDTO;
import com.csi.itaca.dataview.model.filters.AuditSearchFilter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.List;

/**
 * People management service.
 * @author jguerra
 */
public interface DataViewManagementService {

    /**
     * Gets all audit.
     * @return a response body containing the requested audit json object.
     */
    List<? extends AuditDTO> getAllAudit();

    /**
     * Find audit process based on the supplied key.
     * @param filter the filter to apply.
     * @param errTracking error tracking.
     * @return a list containing 1 more audit process depend upon the configuration. Will return an empty list if
     * no audits were found.
     */
    List<? extends AuditDTO> findAudit(AuditSearchFilter filter, BindingResult errTracking);

    /**
     * Deletes audit item
     * @param id the id of audit activity to delete.
     * @return status ok response if the delete was successful.
     */
    void  deleteAudit(long id, Errors errTracking);

    /**
     * Gets audit activity.
     * @param id the audit id.
     * @return a response body containing the requested audit json object.
     */
    AuditDTO getAudit(long id, Errors errTracking);

    /**
     * counts audits.
     * @param id the filter to find audit activity.
     */
    long countAudit(long id);

    /**
     * Saves or updates audit activity.
     * @param dto auditDTO data to create or update
     */
    void auditTransaction(AuditDTO dto);

}
