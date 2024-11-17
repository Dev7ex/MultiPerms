package com.dev7ex.multiperms.api.bungeecord.event.user;

import com.dev7ex.multiperms.api.bungeecord.user.BungeePermissionUser;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@Getter(AccessLevel.PUBLIC)
public class PermissionUserGroupSetEvent extends PermissionUserEvent {

    private final PermissionGroup newGroup;

    public PermissionUserGroupSetEvent(@NotNull final BungeePermissionUser user, @NotNull final PermissionGroup newGroup) {
        super(user);
        this.newGroup = newGroup;
    }

}