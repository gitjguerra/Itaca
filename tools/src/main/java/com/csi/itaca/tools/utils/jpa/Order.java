package com.csi.itaca.tools.utils.jpa;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Bean to represent the ordering of a single field.
 * @author bboothe
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Order {

    private String field;

    private boolean ascending;

    public Order(String field, boolean ascending) {
        this.field = field;
        this.ascending = ascending;
    }
}
