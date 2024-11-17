package com.dev7ex.multiperms.api.velocity.event.user;

import com.dev7ex.multiperms.api.velocity.user.VelocityPermissionUser;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
public class PermissionUserLogoutEvent extends PermissionUserEvent {

    public PermissionUserLogoutEvent(@NotNull final VelocityPermissionUser user) {
        super(user);
    }

}
