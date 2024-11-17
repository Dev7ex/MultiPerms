package com.dev7ex.multiperms.api.bukkit.event.user;

import com.dev7ex.multiperms.api.bukkit.user.BukkitPermissionUser;
import com.dev7ex.multiperms.api.user.PermissionUser;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an event related to a {@link PermissionUser} in the MultiPerms system.
 * This abstract class provides access to the user involved in the event.
 *
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
public abstract class PermissionUserEvent extends Event {

    private final BukkitPermissionUser user;

    /**
     * Constructs a new event for a specific {@link PermissionUser}.
     *
     * @param user The {@link PermissionUser} associated with this event.
     */
    public PermissionUserEvent(@NotNull final BukkitPermissionUser user) {
        this.user = user;
    }

    public PermissionUserEvent(@NotNull final BukkitPermissionUser user, final boolean async) {
        super(async);
        this.user = user;
    }

}