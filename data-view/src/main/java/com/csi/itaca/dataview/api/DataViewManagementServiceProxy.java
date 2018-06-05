package com.csi.itaca.dataview.api;

import com.csi.itaca.dataview.model.filters.AuditSearchFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Date;

/**
 * Audit core management service proxy interface.
 * @author jguerra
 */
public interface DataViewManagementServiceProxy {

    // Parameters...
    String ID_PARAM                 = "id";

    // End points for audit process...
    String RESOURCE_AUDIT           = "/audit";
    String SAVE_AUDIT               = RESOURCE_AUDIT + "/save";
    String DELETE_AUDIT             = RESOURCE_AUDIT + "/delete";
    String SEARCH_AUDIT             = RESOURCE_AUDIT + "/search";
    String COUNT_AUDIT              = RESOURCE_AUDIT +"/count";
    String GET_AUDIT                = RESOURCE_AUDIT +"/get";
    String GET_AUDIT_ALL            = RESOURCE_AUDIT +"/getAll";

    /**
     * Gets all audit.
     * @return a response body containing the requested audit json object.
     */
    ResponseEntity getAllAudit();

    /**
     * Find audit process based on the supplied key.
     * @param filter the filter to apply.
     * @param errTracking error tracking.
     * @return a list containing 1 more audit process depend upon the configuration. Will return an empty list if
     * no audits were found.
     */
    ResponseEntity findAudit(AuditSearchFilter filter, BindingResult errTracking);

    /**
     * Deletes audit item
     * @param id the id of audit activity to delete.
     * @return status ok response if the delete was successful.
     */
    ResponseEntity deleteAudit(long id);

    /**
     * Gets audit activity.
     * @param id the audit id.
     * @return a response body containing the requested audit json object.
     */
    ResponseEntity getAudit(long id);

    /**
     * counts audits.
     * @param id the filter to find audit activity.
     */
    ResponseEntity<Long> countAudit(long id);

    /**
     * Saves or updates audit activity.
     * @param timeStamp the time stamp th audit.
     * @param userName the user produces activity
     * @param operation type operation (create, update, get or delete)
     * @param sqlCommand sql transact the activity
     * @param errTracking error tracking.
     */
    ResponseEntity auditTransaction(Date timeStamp, String userName, String operation, String sqlCommand, BindingResult errTracking);

}
