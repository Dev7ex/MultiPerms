package com.dev7ex.multiperms.api.bukkit.event.group;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event triggered when a {@link PermissionGroup} is created.
 * This event provides access to the group being created and allows it to be cancelled.
 *
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class PermissionGroupCreateEvent extends PermissionGroupEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false;

    /**
     * Constructs a new event for creating a {@link PermissionGroup}.
     *
     * @param group The {@link PermissionGroup} being created.
     */
    public PermissionGroupCreateEvent(@NotNull final PermissionGroup group) {
        super(group);
    }

    /**
     * Gets the list of event handlers for this event.
     *
     * @return A {@link HandlerList} containing all handlers for this event.
     */
    public static HandlerList getHandlerList() {
        return PermissionGroupCreateEvent.HANDLERS;
    }

    /**
     * Gets the list of event handlers for this event.
     *
     * @return A {@link HandlerList} for handling the event.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return PermissionGroupCreateEvent.HANDLERS;
    }

}

