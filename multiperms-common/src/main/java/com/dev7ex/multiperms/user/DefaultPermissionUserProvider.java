package com.dev7ex.multiperms.user;

import com.dev7ex.multiperms.api.user.PermissionUser;
import com.dev7ex.multiperms.api.user.PermissionUserFactory;
import com.dev7ex.multiperms.api.user.PermissionUserProvider;
import com.dev7ex.multiperms.api.user.PermissionUserRepository;

import java.util.Optional;
import java.util.UUID;

public class DefaultPermissionUserProvider implements PermissionUserProvider {

    @Override
    public void register(PermissionUser user) {

    }

    @Override
    public void unregister(PermissionUser user) {

    }

    @Override
    public void unregister(UUID uniqueId) {

    }

    @Override
    public Optional<PermissionUser> find(UUID uniqueId) {
        return Optional.empty();
    }

    @Override
    public Optional<PermissionUser> find(String name) {
        return Optional.empty();
    }

    @Override
    public PermissionUserRepository getRepository() {
        return null;
    }

    @Override
    public PermissionUserFactory getFactory() {
        return null;
    }

}
