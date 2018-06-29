package com.csi.itaca.preload.model.dto;


import com.csi.itaca.preload.model.ErrorField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode

public class ErrorFieldDTO implements ErrorField {


   private Long errFieldsId;

   private Long preloaDataId;

   private String preloadFieldDefinitionId;

   private String validationErrMsg;


}
