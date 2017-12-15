package com.csi.itaca.people.model.dto;

import com.csi.itaca.common.model.BaseModelImpl;
import com.csi.itaca.people.model.Person;
import com.csi.itaca.people.model.IDType;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a single person.
 * @author bboothe
 */
@NoArgsConstructor
@Getter
public class PersonDTO extends BaseModelImpl implements Person {

    private IDType idType;

    private String identificationCode;

    private String externalReferenceCode;

}
