package com.dev7ex.multiperms.api.user;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
public enum PermissionUserProperty {

    UNIQUE_ID("unique-id"),
    NAME("name"),
    LAST_LOGIN("last-login"),
    GROUP("group"),
    SUB_GROUPS("sub-groups"),
    PERMISSION("permission");

    private final String storagePath;

    PermissionUserProperty(@NotNull final String storagePath) {
        this.storagePath = storagePath;
    }

}
