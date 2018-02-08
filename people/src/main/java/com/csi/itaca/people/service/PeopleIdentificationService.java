package com.csi.itaca.people.service;

import com.csi.itaca.people.model.dto.IdentificationDTO;
import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import org.springframework.validation.Errors;

import java.util.List;

public interface PeopleIdentificationService {

    /**
     * Gets a list of identifications based on the person details ID
     * @param personDetailId identification associated with this person details ID
     * @return  a list of identifications
     */
    List<IdentificationDTO> listIdentifications(Long personDetailId);


    /**
     * Gets a list of identifications based on the person details ID
     * @param personDetailId identification associated with this person details ID
     * @param pagination page number and number of items per page.
     * @param order by field
     * @return  a list of identifications
     */
    List<IdentificationDTO> listIdentifications(Long personDetailId, Pagination pagination, Order order);

    /**
     * Deletes a indentification.
     * @param identificationId the <code>identificationId</code> to delete.
     * @param errTracking if <code>identificationId</code> was not found an error will be added.
     * @return true if identification was delete, false if the <code>identificationId</code> dose not exists.
     */
    boolean deleteIdentification(Long identificationId, Errors errTracking);


    /**
     * Gets a identification based on the supplied <code>identificationId</code>
     * @param identificationId the ID of the identication to retrieve.
     * @param errTracking if <code>identificationId</code> was not found an error will be added.
     * @return the identification
     */
    IdentificationDTO getIdentification(Long identificationId, Errors errTracking);

    /**
     * saves or updates an identification
     * @param identification the identification to save or update
     * @param errTracking
     * @return the saved or updated IdentificationDTO.
     */
    IdentificationDTO saveOrUpdateIdentification(IdentificationDTO identification, Errors errTracking);

    /**
     * Counts the number of identification items associated to the specified <code>personDetailId</code>.
     * @param personDetailId the person detail id.
     * @return the number of identifications.
     */
    Long countIdentification(Long personDetailId);

}
