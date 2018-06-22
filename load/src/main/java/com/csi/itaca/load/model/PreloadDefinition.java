package com.csi.itaca.load.model;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadDefinition {

    Long getPreloadDefinitionId();

    String getName();

    String getDescription();

    Long getMaxPreloadLowErrors();

    Long getMaxPreloadMediumErrors();

    Long getMaxPreloadHighErrors();

    String getLoadIfPreloadOk();

    String getAutoLoadDirectory();

    String getAutoCronScheduling();

    Boolean getAutoEnable();

}
