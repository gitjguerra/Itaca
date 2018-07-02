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

public class LoadOperationTypeDTO implements LoadOperationType {

   private Long LoadOperationTypeId;

   private Long Name;

   private String Description;



}
