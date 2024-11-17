package com.dev7ex.multiperms.api.bukkit.event.user;

import com.dev7ex.multiperms.api.bukkit.user.BukkitPermissionUser;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUser;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event triggered when a {@link PermissionUser} is assigned to a new {@link PermissionGroup}.
 * This event provides access to the user and the new group they were assigned to.
 *
 * @author Dev7ex
 * @since 03.08.2023
 */
@Getter(AccessLevel.PUBLIC)
public class PermissionUserGroupSetEvent extends PermissionUserEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private final PermissionGroup newGroup;

    /**
     * Constructs a new event for setting a {@link PermissionGroup} for a {@link PermissionUser}.
     *
     * @param user The {@link PermissionUser} whose group is being set.
     * @param newGroup The new {@link PermissionGroup} the user is being assigned to.
     */
    public PermissionUserGroupSetEvent(@NotNull final BukkitPermissionUser user, @NotNull final PermissionGroup newGroup) {
        super(user);
        this.newGroup = newGroup;
    }

    /**
     * Gets the list of event handlers for this event.
     *
     * @return A {@link HandlerList} containing all handlers for this event.
     */
    public static HandlerList getHandlerList() {
        return PermissionUserGroupSetEvent.HANDLERS;
    }

    /**
     * Gets the list of event handlers for this event.
     *
     * @return A {@link HandlerList} for handling the event.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return PermissionUserGroupSetEvent.HANDLERS;
    }

}

