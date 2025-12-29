package com.dev7ex.multiperms.user;

import com.dev7ex.multiperms.api.user.PermissionUser;
import com.dev7ex.multiperms.api.user.PermissionUserFactory;

import java.util.UUID;

public class DefaultPermissionUserFactory implements PermissionUserFactory {

    @Override
    public PermissionUser create(UUID uniqueId, String name) {
        return null;
    }

    @Override
    public PermissionUser delete(UUID uniqueId) {
        return null;
    }

}
