package com.csi.itaca.load.model.dto;


import com.csi.itaca.load.model.ErrorField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode

public class LoadDefinitiveFieldDTO implements LoadDefinitiveField {

    private Long LoadDefinitiveFieldId;

   private Long LoadDefinitiveTableId;

   private String FieldName;

   private String IsKeyField;



}
