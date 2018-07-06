package com.csi.itaca.load.model;

/**
 * Created by Arturo on 01/07/2018.
 */
public interface LoadAtomicOperation {

    Long getLoadAtomicOperationId();

    Long getLoadOperationTypeId();

    String getAtomicOpCode();

    String getName();

    String getDescription();

}
