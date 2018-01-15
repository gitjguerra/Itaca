package com.csi.itaca.people.model;

import com.csi.itaca.common.model.BaseModel;

/**
 * Represents a single person.
 * @author bboothe
 */
public interface Person extends BaseModel {

    /**
     * @return the identification type.
     */
    IDType getIdType();

    /**
     * @return the identification code.
     */
    String getIdentificationCode();

    /**
     * @return the external reference code.
     */
    String getExternalReferenceCode();
}
