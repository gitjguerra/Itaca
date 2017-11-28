package com.csi.itaca.users.model;

import com.csi.itaca.common.model.BaseModel;

public interface UserConfig extends BaseModel {

    Profile getProfile();

    User getUser();

}
