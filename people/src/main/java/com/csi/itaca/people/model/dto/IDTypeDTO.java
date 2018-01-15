package com.csi.itaca.people.model.dto;

import com.csi.itaca.common.model.BaseModelImpl;
import com.csi.itaca.tools.utils.beaner.Extension;
import com.csi.itaca.people.model.IDType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Getter
@Setter
@NoArgsConstructor
@Extension(of=IDType.class)
public class IDTypeDTO extends BaseModelImpl implements IDType {

    @JsonInclude(NON_NULL)
    String name;

    @JsonInclude(NON_NULL)
    Boolean individual;

    @JsonInclude(NON_NULL)
    Boolean company;
}
