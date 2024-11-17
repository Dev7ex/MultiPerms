package com.dev7ex.multiperms.api.velocity.event.user;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.velocity.user.VelocityPermissionUser;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
@Getter(AccessLevel.PUBLIC)
public class PermissionUserGroupSetEvent extends PermissionUserEvent {

    private final PermissionGroup newGroup;

    public PermissionUserGroupSetEvent(@NotNull final VelocityPermissionUser user, @NotNull final PermissionGroup newGroup) {
        super(user);
        this.newGroup = newGroup;
    }

}
