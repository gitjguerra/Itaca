package com.csi.itaca.users.businessLogic;

import com.csi.itaca.users.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserManagementBusinessLogicImpl implements UserManagementBusinessLogic {

    public boolean isUserAuthorisedToLogOn(User user) {
        return !user.isBlocked();
    }
}
