package com.csi.itaca.preLoad.model.dto;


import com.csi.itaca.preLoad.model.LdErrorField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode

public class LdErrorFieldDTO implements LdErrorField{


   private Long errFieldsId;

   private Long preloaDataId;

   private String preloadFieldDefinitionId;

   private String validationErrMsg;


}
