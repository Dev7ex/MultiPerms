package com.dev7ex.multiperms.api.user;

import java.util.UUID;

public interface PermissionUserFactory {

    PermissionUser create(final UUID uniqueId, final String name);

    PermissionUser delete(final UUID uniqueId);

}
