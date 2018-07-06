package com.csi.itaca.load.model.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.csi.itaca.load.model.LoadDefinitiveTable;
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode

public class LoadDefinitiveTableDTO implements LoadDefinitiveTable {

   private Long LoadDefinitiveTableId;

   private Long TableName;



}
