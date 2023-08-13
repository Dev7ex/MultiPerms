package com.dev7ex.multiperms.api.bukkit.event.user;

import com.dev7ex.multiperms.api.user.PermissionUser;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.08.2023
 */
public class PermissionUserLoginEvent extends PermissionUserEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public PermissionUserLoginEvent(@NotNull final PermissionUser user) {
        super(user);
    }

    public static HandlerList getHandlerList() {
        return PermissionUserLoginEvent.HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return PermissionUserLoginEvent.HANDLERS;
    }

}
