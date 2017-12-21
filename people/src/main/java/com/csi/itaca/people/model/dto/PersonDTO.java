package com.csi.itaca.people.model.dto;

import com.csi.itaca.common.model.BaseModelImpl;
import com.csi.itaca.people.model.Person;
import com.csi.itaca.people.model.PersonType;
import com.csi.itaca.tools.utils.beaner.Extension;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Represents a single person.
 * @author bboothe
 */
@NoArgsConstructor
@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY , property = "personType")
@JsonSubTypes({ @JsonSubTypes.Type(value = IndividualDTO.class, name = PersonType.INDIVIDUAL_PERSON_CODE),
                @JsonSubTypes.Type(value = CompanyDTO.class, name = PersonType.COMPANY_PERSON_CODE) })
@Extension
public abstract class PersonDTO implements Person {

    private Long id;

    private IDTypeDTO idType;

    private String identificationCode;

    private String externalReferenceCode;

}
