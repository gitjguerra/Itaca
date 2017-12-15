package com.csi.itaca.people.model;

import com.csi.itaca.common.model.BaseModel;

/**
 * Identification type for person.
 * @author bboothe
 */
public interface IDType extends BaseModel {

    /** The name of the ID Type */
    String getName();

    /**
     * @return Indicates if this type is an individual.
     */
    boolean isIndividual();

    /**
     * @return Indicates if this type is a company.
     */
    boolean isCompany();
}
