package com.csi.itaca.load.model;

/**
 * Created by Arturo on 01/07/2018.
 */
public interface LoadDefinitiveField {


     Long getLoadDefinitiveFieldId();

     LoadDefinitiveTable getLoadDefinitiveTableId();

     String getFieldName();

     String getIsKeyField();

}
