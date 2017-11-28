package com.csi.itaca.users.model;

import com.csi.itaca.common.model.BaseModel;

/**
 *
 * @author bboothe
 */
public interface ProfileType extends BaseModel {

    String getValue();

    void setValue(String newValue);
}
