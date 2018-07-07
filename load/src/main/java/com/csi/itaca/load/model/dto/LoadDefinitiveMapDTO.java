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

   private Long LoadRowOperationId;

   private String AtomicOpCode;

   private String ColumnNo;

   private String LoadDefinitiveFieldId;


}
