package com.dev7ex.multiperms.api.user;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Defines the various properties related to a permission user, each associated with a specific storage path.
 * This enum provides a mapping between property names and their storage locations within the system.
 *
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
public enum PermissionUserProperty {

    UNIQUE_ID("unique-id"),
    NAME("name"),
    FIRST_LOGIN("first-login"),
    LAST_LOGIN("last-login"),
    GROUP("group"),
    SUB_GROUPS("sub-groups"),
    PERMISSION("permission");

    /**
     * The path used for storing the property in the system's storage.
     */
    private final String storagePath;

    /**
     * Constructs a {@link PermissionUserProperty} with a specified storage path.
     *
     * @param storagePath The path used for storing the property.
     */
    PermissionUserProperty(@NotNull final String storagePath) {
        this.storagePath = storagePath;
    }
}

