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

public class ErrorFieldDTO implements ErrorField {


   private Long errFieldsId;

   private PreloadDataDTO preloaDataId;

   private String preloadFieldDefinitionId;

   private String validationErrMsg;


}
