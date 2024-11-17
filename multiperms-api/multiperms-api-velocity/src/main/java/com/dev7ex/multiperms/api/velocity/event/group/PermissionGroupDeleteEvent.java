package com.dev7ex.multiperms.api.velocity.event.group;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class PermissionGroupDeleteEvent extends PermissionGroupEvent {

    private boolean cancelled = false;

    public PermissionGroupDeleteEvent(@NotNull final PermissionGroup group) {
        super(group);
    }

}
