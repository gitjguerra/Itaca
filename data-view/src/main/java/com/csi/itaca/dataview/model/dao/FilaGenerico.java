package com.csi.itaca.dataview.model.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rommel Vega
 *
 */
public class FilaGenerico {


    public void setCampos(List<Object> campos) {
        this.campos = campos;
    }

    List<Object> campos = new ArrayList<Object>();

    public void addData(Object value) {
            campos.add(value);
        }

    public List<Object> getCampos() {
        return campos;
    }
    public FilaGenerico (){

    }
}
