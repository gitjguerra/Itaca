package com.csi.itaca.load.model.dao;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class ErrorFieldEntityTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ErrorFieldEntity.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @org.junit.Test
    public void getErrFieldsId() {
    }

    @org.junit.Test
    public void getPreloaDataId() {
    }

    @org.junit.Test
    public void getPreloadFieldDefinitionId() {
    }

    @org.junit.Test
    public void getValidationErrMsg() {
    }

    @org.junit.Test
    public void setErrFieldsId() {
    }

    @org.junit.Test
    public void setPreloaDataId() {
    }

    @org.junit.Test
    public void setPreloadFieldDefinitionId() {
    }

    @org.junit.Test
    public void setValidationErrMsg() {
    }
}
