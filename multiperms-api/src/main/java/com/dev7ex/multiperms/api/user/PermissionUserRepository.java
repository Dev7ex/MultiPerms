package com.dev7ex.multiperms.api.user;

import java.util.UUID;

public interface PermissionUserRepository {

    void add(final PermissionUser user);

    void remove(final PermissionUser user);

    void remove(final UUID uniqueId);

    boolean contains(final PermissionUser user);

    boolean contains(final UUID uniqueId);

    void write(final PermissionUser user, final PermissionUserProperty property, final Object value);

    PermissionUser read(final PermissionUserProperty... properties);

}
