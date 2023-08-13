package com.dev7ex.multiperms.api.bukkit.event.user;

import com.dev7ex.multiperms.api.user.PermissionUser;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
public abstract class PermissionUserEvent extends Event {

    private final PermissionUser user;

    public PermissionUserEvent(@NotNull final PermissionUser user) {
        this.user = user;
    }

}