package com.dev7ex.multiperms.api.bungeecord.event.user;

import com.dev7ex.multiperms.api.bungeecord.user.BungeePermissionUser;
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

    private final BungeePermissionUser user;

    public PermissionUserEvent(@NotNull final BungeePermissionUser user) {
        this.user = user;
    }

}
