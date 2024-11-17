package com.dev7ex.multiperms.api.velocity.event.group;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
@Getter(AccessLevel.PUBLIC)
public abstract class PermissionGroupEvent {

    private final PermissionGroup group;

    public PermissionGroupEvent(@NotNull final PermissionGroup group) {
        this.group = group;
    }

}
