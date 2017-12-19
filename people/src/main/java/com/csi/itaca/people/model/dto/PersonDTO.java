package com.csi.itaca.people.model.dto;

import com.csi.itaca.common.model.BaseModelImpl;
import com.csi.itaca.people.model.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a single person.
 * @author bboothe
 */
@NoArgsConstructor
@Getter
@Setter
public abstract class PersonDTO extends BaseModelImpl implements Person {

    private IDTypeDTO idType;

    private String identificationCode;

    private String externalReferenceCode;

}
