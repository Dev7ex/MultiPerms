package com.dev7ex.multiperms.api.bungeecord.event.user;

import com.dev7ex.multiperms.api.bungeecord.user.BungeePermissionUser;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
public class PermissionUserLogoutEvent extends PermissionUserEvent {

    public PermissionUserLogoutEvent(@NotNull final BungeePermissionUser user) {
        super(user);
    }

}
