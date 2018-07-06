package com.csi.itaca.load.model.dto;

import com.csi.itaca.load.model.PreloadDefinition;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
/**
 * Created by Robert on 19/06/2018.
 */
public class PreloadDefinitionDTO implements PreloadDefinition {

    private Long preloadDefinitionId;

    private String name;

    private String description;

    private Long maxPreloadLowErrors;

    private Long maxPreloadMediumErrors;

    private Long maxPreloadHighErrors;

    private String loadIfPreloadOk;

    private String autoLoadDirectory;

    private String autoCronScheduling;

    private Boolean autoEnable;

}
