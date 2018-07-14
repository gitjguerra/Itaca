package com.csi.itaca.load.model.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Robert on 09/07/2018.
 */

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class PreloadDataDTOFilter {

    private Long id;

    public PreloadDataDTOFilter(Long id) {

        this.id = id;
    }

}
