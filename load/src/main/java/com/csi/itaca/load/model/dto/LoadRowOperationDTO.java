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

   private Long LoadOperationTypeId;

   private String PreloadRowTypeId;

   private String LoadDefinitiveTableId;

   private String KeyColumnNo;

   private String OperationOrder;


}
