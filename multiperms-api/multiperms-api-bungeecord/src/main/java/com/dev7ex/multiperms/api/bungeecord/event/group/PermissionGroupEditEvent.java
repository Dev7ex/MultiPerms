package com.dev7ex.multiperms.api.bungeecord.event.group;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupProperty;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@Getter(AccessLevel.PUBLIC)
public class PermissionGroupEditEvent extends PermissionGroupEvent {

    private final PermissionGroupProperty groupProperty;
    private final Object value;

    public PermissionGroupEditEvent(@NotNull final PermissionGroup group, @NotNull final PermissionGroupProperty groupProperty, @NotNull final Object value) {
        super(group);
        this.groupProperty = groupProperty;
        this.value = value;
    }

}
