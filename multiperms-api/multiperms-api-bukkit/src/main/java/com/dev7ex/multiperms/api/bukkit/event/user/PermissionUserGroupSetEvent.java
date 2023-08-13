package com.dev7ex.multiperms.api.bukkit.event.user;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUser;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.08.2023
 */
@Getter(AccessLevel.PUBLIC)
public class PermissionUserGroupSetEvent extends PermissionUserEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private final PermissionGroup newGroup;

    public PermissionUserGroupSetEvent(@NotNull final PermissionUser user, @NotNull final PermissionGroup newGroup) {
        super(user);
        this.newGroup = newGroup;
    }

    public static HandlerList getHandlerList() {
        return PermissionUserGroupSetEvent.HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return PermissionUserGroupSetEvent.HANDLERS;
    }

}
