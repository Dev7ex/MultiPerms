package com.dev7ex.multiperms.api.bukkit.event.user;

import com.dev7ex.multiperms.api.bukkit.user.BukkitPermissionUser;
import com.dev7ex.multiperms.api.user.PermissionUser;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event triggered when a {@link PermissionUser} logs in.
 * This event provides access to the user who has logged in.
 *
 * @author Dev7ex
 * @since 03.08.2023
 */
public class PermissionUserLoginEvent extends PermissionUserEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * Constructs a new event for a {@link PermissionUser} logging in.
     *
     * @param user The {@link PermissionUser} who is logging in.
     */
    public PermissionUserLoginEvent(@NotNull final BukkitPermissionUser user) {
        super(user);
    }

    /**
     * Gets the list of event handlers for this event.
     *
     * @return A {@link HandlerList} containing all handlers for this event.
     */
    public static HandlerList getHandlerList() {
        return PermissionUserLoginEvent.HANDLERS;
    }

    /**
     * Gets the list of event handlers for this event.
     *
     * @return A {@link HandlerList} for handling the event.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return PermissionUserLoginEvent.HANDLERS;
    }

}


