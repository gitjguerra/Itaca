package com.csi.itaca.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BaseModelImpl implements BaseModel {

    @NotNull
    private Long id;
}
