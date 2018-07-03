package com.csi.itaca.load.model;

/**
 * Created by Arturo on 01/07/2018.
 */
public interface LoadRowOperation {


       Long getLoadRowOperationId();

       Long getLoadOperationTypeId();

       String getPreloadRowTypeId();

       String getLoadDefinitiveTableId();

       String getKeyColumnNo();

       String getOperationOrder();


}
