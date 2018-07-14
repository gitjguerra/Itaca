package com.csi.itaca.load.model;

/**
 * Created by Arturo on 01/07/2018.
 */
public interface LoadRowOperation {


       Long getLoadRowOperationId();
/*
       LoadOperationType getLoadOperationTypeId();
       PreloadRowType getPreloadRowTypeId();
       LoadDefinitiveTable getLoadDefinitiveTableId();
*/

       Long getLoadOperationTypeId();
       Long getPreloadRowTypeId();
       Long getLoadDefinitiveTableId();

       Long getKeyColumnNo();

       Long getOperationOrder();


}
