package com.dev7ex.multiperms.api.bukkit.event.group;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class PermissionGroupCreateEvent extends PermissionGroupEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false;

    public PermissionGroupCreateEvent(@NotNull final PermissionGroup group) {
        super(group);
    }

    public static HandlerList getHandlerList() {
        return PermissionGroupCreateEvent.HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return PermissionGroupCreateEvent.HANDLERS;
    }

}
