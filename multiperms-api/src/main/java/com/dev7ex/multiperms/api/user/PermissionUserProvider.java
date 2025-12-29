package com.dev7ex.multiperms.api.user;

import java.util.Optional;
import java.util.UUID;

public interface PermissionUserProvider {

    void register(final PermissionUser user);

    void unregister(final PermissionUser user);

    void unregister(final UUID uniqueId);

    Optional<PermissionUser> find(final UUID uniqueId);

    Optional<PermissionUser> find(final String name);

    PermissionUserRepository getRepository();

    PermissionUserFactory getFactory();

}
