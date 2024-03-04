package com.dev7ex.multiperms.api.event.user;

import com.dev7ex.multiperms.api.user.PermissionUser;
import lombok.AccessLevel;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@Getter(AccessLevel.PUBLIC)
public abstract class PermissionUserEvent extends Event {

    private final PermissionUser user;

    public PermissionUserEvent(@NotNull final PermissionUser user) {
        this.user = user;
    }

}
