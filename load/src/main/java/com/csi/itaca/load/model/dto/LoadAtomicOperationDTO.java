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

public class LoadAtomicOperationDTO implements LoadAtomicOperation {

   private Long LoadAtomicOperationId;

   private Long LoadOperationTypeId;

   private String AtomicOpCode;

   private String Name;

   private String Description;


}
