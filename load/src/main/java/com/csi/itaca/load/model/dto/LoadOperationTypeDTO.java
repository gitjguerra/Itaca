package com.csi.itaca.load.model.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.csi.itaca.load.model.LoadOperationType;
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode

public class LoadOperationTypeDTO implements LoadOperationType {

   private Long LoadOperationTypeId;

   private Long Name;

   private String Description;


}
