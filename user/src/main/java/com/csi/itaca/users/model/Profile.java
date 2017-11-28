package com.csi.itaca.users.model;

import com.csi.itaca.common.model.BaseModel;

/**
 *
 * @author bboothe
 */
public interface Profile extends BaseModel {

    String getValue();

    void setValue(String newValue);

    ProfileType getProfileType();

    //void setProfileType(ProfileType profileType);
}
