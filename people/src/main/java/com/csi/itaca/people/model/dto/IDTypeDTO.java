package com.csi.itaca.people.model.dto;

import com.csi.itaca.common.model.BaseModelImpl;
import com.csi.itaca.tools.utils.beaner.Extension;
import com.csi.itaca.people.model.IDType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Extension(of=IDType.class)
public class IDTypeDTO extends BaseModelImpl implements IDType {

    String name;

    boolean individual;

    boolean company;
}
