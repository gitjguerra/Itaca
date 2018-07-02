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

public class LoadDefinitiveTableDTO implements LoadDefinitiveTable {

   private Long LoadDefinitiveTableId;

   private Long TableName;


}
