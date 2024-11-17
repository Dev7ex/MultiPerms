package com.dev7ex.multiperms.api.bukkit.event.group;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupProperty;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event triggered when a {@link PermissionGroup} is edited.
 * This event provides access to the group being edited, the property being modified,
 * and the new value for that property.
 *
 * @author Dev7ex
 * @since 03.08.2023
 */
@Getter(AccessLevel.PUBLIC)
public class PermissionGroupEditEvent extends PermissionGroupEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private final PermissionGroupProperty groupProperty;
    private final Object value;

    /**
     * Constructs a new event for editing a {@link PermissionGroup}.
     *
     * @param group The {@link PermissionGroup} being edited.
     * @param groupProperty The property of the group being modified.
     * @param value The new value for the specified property.
     */
    public PermissionGroupEditEvent(@NotNull final PermissionGroup group,
                                    @NotNull final PermissionGroupProperty groupProperty,
                                    @NotNull final Object value) {
        super(group);
        this.groupProperty = groupProperty;
        this.value = value;
    }

    /**
     * Gets the list of event handlers for this event.
     *
     * @return A {@link HandlerList} containing all handlers for this event.
     */
    public static HandlerList getHandlerList() {
        return PermissionGroupEditEvent.HANDLERS;
    }

    /**
     * Gets the list of event handlers for this event.
     *
     * @return A {@link HandlerList} for handling the event.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return PermissionGroupEditEvent.HANDLERS;
    }

}

