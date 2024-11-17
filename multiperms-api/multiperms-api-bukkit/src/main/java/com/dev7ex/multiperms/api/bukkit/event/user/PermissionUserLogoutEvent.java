package com.dev7ex.multiperms.api.bukkit.event.user;

import com.dev7ex.multiperms.api.bukkit.user.BukkitPermissionUser;
import com.dev7ex.multiperms.api.user.PermissionUser;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event triggered when a {@link PermissionUser} logs out.
 * This event provides access to the user who has logged out.
 *
 * @author Dev7ex
 * @since 03.08.2023
 */
public class PermissionUserLogoutEvent extends PermissionUserEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * Constructs a new event for a {@link PermissionUser} logging out.
     *
     * @param user The {@link PermissionUser} who is logging out.
     */
    public PermissionUserLogoutEvent(@NotNull final BukkitPermissionUser user) {
        super(user);
    }

    /**
     * Gets the list of event handlers for this event.
     *
     * @return A {@link HandlerList} containing all handlers for this event.
     */
    public static HandlerList getHandlerList() {
        return PermissionUserLogoutEvent.HANDLERS;
    }

    /**
     * Gets the list of event handlers for this event.
     *
     * @return A {@link HandlerList} for handling the event.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return PermissionUserLogoutEvent.HANDLERS;
    }

}

