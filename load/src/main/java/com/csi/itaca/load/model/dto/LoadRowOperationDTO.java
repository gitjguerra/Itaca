package com.csi.itaca.load.model.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.csi.itaca.load.model.LoadRowOperation;
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode

public class LoadRowOperationDTO implements LoadRowOperation {

   private Long LoadRowOperationId;
/*
   private LoadOperationTypeDTO LoadOperationTypeId;
   private PreloadRowTypeDTO PreloadRowTypeId;
   private LoadDefinitiveTableDTO loadDefinitiveTableId;
*/

   private Long LoadOperationTypeId;
   private Long PreloadRowTypeId;
   private Long loadDefinitiveTableId;


   private Long KeyColumnNo;

   private Long OperationOrder;


}
