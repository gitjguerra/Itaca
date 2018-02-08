package com.csi.itaca.people.api;

import com.csi.itaca.people.model.dto.IdentificationDTO;
import com.csi.itaca.people.model.filters.IdentificationSearchFilter;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * People identification service proxy interface.
 * @author bboothe
 */
public interface PeopleIdentificationServiceProxy {

    // Parameters...
    String PERSON_DETAIL_ID_PARAM   = "personDetailId";
    String IDENTIFICATION_ID_PARAM  = "identificationId";

    // End points for identification...
    String IDENTIFICATIONS_RESOURCE         = PeopleManagementServiceProxy.RESOURCE + "/id";
    String SEARCH_IDENTIFICATIONS           = IDENTIFICATIONS_RESOURCE + "/find";
    String COUNT_IDENTIFICATIONS            = IDENTIFICATIONS_RESOURCE + "/count";
    String GET_IDENTIFICATIONS              = IDENTIFICATIONS_RESOURCE + "/get";
    String SAVE_UPDATE_IDENTIFICATIONS      = IDENTIFICATIONS_RESOURCE + "/save";
    String DELETE_IDENTIFICATIONS           = IDENTIFICATIONS_RESOURCE + "/delete";

    /**
     * Get a list identifications.
     * @param personDetailId person detail id. Overridden by <code>filter</code> if provided
     * @param filter the search filter to be applied. (optional)
     * @return a list identifications.
     */
    ResponseEntity<List<IdentificationDTO>> listIdentifications(Long personDetailId, IdentificationSearchFilter filter);

    /**
     * Counts the number of identifications associated the given person detail id.
     * @param personDetailId person detail id
     * @return the number of identifications
     */
    ResponseEntity<Long> countIdentifications(Long personDetailId);

    /**
     * Deletes the specified identification.
     * @param identificationId the id to delete.
     * @return
     */
    ResponseEntity deleteIdentifications(Long identificationId);

    /**
     * Saves or updated the specified identification
     * @param identification the identification to save or update.
     * @return the updated identification
     */
    ResponseEntity<IdentificationDTO> saveOrUpdateIdentifications(IdentificationDTO identification);

    /**
     * Gets the identification associated with the specified ID.
     * @param identificationId if of the identification to retrieve.
     * @return the retrieved identification.
     */
    ResponseEntity<IdentificationDTO> getIdentifications(Long identificationId);


}
