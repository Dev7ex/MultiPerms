package com.dev7ex.multiperms.api.event.user;

import com.dev7ex.multiperms.api.user.PermissionUser;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
public class PermissionUserLogoutEvent extends PermissionUserEvent {

    public PermissionUserLogoutEvent(@NotNull final PermissionUser user) {
        super(user);
    }

}
