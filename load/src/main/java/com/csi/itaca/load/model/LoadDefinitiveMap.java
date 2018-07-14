package com.csi.itaca.load.model;

/**
 * Created by Arturo on 01/07/2018.
 */
public interface LoadDefinitiveMap {


      Long getLoadDefinitiveMapId();

      LoadRowOperation getLoadRowOperationId();

      LoadAtomicOperation getAtomicOpCode();

      Long getColumnNo();

      LoadDefinitiveField getLoadDefinitiveFieldId();

}
