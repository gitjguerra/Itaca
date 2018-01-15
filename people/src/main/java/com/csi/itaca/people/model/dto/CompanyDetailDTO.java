package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.CompanyDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDetailDTO extends PersonDetailDTO implements CompanyDetail {

    // We add this anotation(@JsonIgnoreProperties) in order to prevent recursion when generating the JSON.
    @JsonIgnoreProperties("details")
    private CompanyDTO person;

}
