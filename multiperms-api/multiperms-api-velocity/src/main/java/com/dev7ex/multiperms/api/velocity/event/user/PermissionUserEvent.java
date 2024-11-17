package com.dev7ex.multiperms.api.velocity.event.user;

import com.dev7ex.multiperms.api.velocity.user.VelocityPermissionUser;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
@Getter(AccessLevel.PUBLIC)
public abstract class PermissionUserEvent {

    private final VelocityPermissionUser user;

    public PermissionUserEvent(@NotNull final VelocityPermissionUser user) {
        this.user = user;
    }

}
