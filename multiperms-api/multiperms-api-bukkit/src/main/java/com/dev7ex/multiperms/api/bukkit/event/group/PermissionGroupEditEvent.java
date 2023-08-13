package com.dev7ex.multiperms.api.bukkit.event.group;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupProperty;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.08.2023
 */
@Getter(AccessLevel.PUBLIC)
public class PermissionGroupEditEvent extends PermissionGroupEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private final PermissionGroupProperty groupProperty;
    private final Object value;

    public PermissionGroupEditEvent(@NotNull final PermissionGroup group, @NotNull final PermissionGroupProperty groupProperty, @NotNull final Object value) {
        super(group);
        this.groupProperty = groupProperty;
        this.value = value;
    }

    public static HandlerList getHandlerList() {
        return PermissionGroupEditEvent.HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return PermissionGroupEditEvent.HANDLERS;
    }

}
