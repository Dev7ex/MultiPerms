package com.dev7ex.multiperms.api.event.user;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUser;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@Getter(AccessLevel.PUBLIC)
public class PermissionUserGroupSetEvent extends PermissionUserEvent {

    private final PermissionGroup newGroup;

    public PermissionUserGroupSetEvent(@NotNull final PermissionUser user, @NotNull final PermissionGroup newGroup) {
        super(user);
        this.newGroup = newGroup;
    }

}