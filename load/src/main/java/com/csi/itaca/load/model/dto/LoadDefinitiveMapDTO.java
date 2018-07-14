package com.csi.itaca.load.model.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.csi.itaca.load.model.LoadDefinitiveMap;
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode

public class LoadDefinitiveMapDTO implements LoadDefinitiveMap {

   private Long LoadDefinitiveMapId;

   private LoadRowOperationDTO LoadRowOperationId;

   private LoadAtomicOperationDTO AtomicOpCode;

   private Long ColumnNo;

   private LoadDefinitiveFieldDTO LoadDefinitiveFieldId;


}
