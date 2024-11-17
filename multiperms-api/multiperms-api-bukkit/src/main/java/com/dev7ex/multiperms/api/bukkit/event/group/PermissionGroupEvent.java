package com.dev7ex.multiperms.api.bukkit.event.group;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Base event class for all events related to {@link PermissionGroup} changes.
 * Provides access to the {@link PermissionGroup} involved in the event.
 *
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
public abstract class PermissionGroupEvent extends Event {

    private final PermissionGroup group;

    /**
     * Constructs a new event for a {@link PermissionGroup}.
     *
     * @param group The {@link PermissionGroup} associated with the event.
     */
    public PermissionGroupEvent(@NotNull final PermissionGroup group) {
        this.group = group;
    }

}

