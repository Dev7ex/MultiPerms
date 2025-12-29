package com.dev7ex.multiperms.user;

import com.dev7ex.multiperms.api.user.PermissionUser;
import com.dev7ex.multiperms.api.user.PermissionUserProperty;
import com.dev7ex.multiperms.api.user.PermissionUserRepository;

import java.util.UUID;

public class DefaultPermissionUserRepository implements PermissionUserRepository {

    @Override
    public void add(PermissionUser user) {

    }

    @Override
    public void remove(PermissionUser user) {

    }

    @Override
    public void remove(UUID uniqueId) {

    }

    @Override
    public boolean contains(PermissionUser user) {
        return false;
    }

    @Override
    public boolean contains(UUID uniqueId) {
        return false;
    }

    @Override
    public void write(PermissionUser user, PermissionUserProperty property, Object value) {

    }

    @Override
    public PermissionUser read(PermissionUserProperty... properties) {
        return null;
    }

}
