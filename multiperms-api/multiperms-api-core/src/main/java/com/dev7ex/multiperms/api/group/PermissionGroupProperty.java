package com.dev7ex.multiperms.api.group;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Defines the various properties related to a permission group, each associated with a specific storage path.
 * This enum provides a mapping between property names and their storage locations within the system.
 * It also includes utility methods to convert properties to a list of strings and to retrieve a property from its string representation.
 *
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
public enum PermissionGroupProperty {

    CHAT_PREFIX("chat-prefix"),
    COLOR("color"),
    DISPLAY_NAME("display-name"),
    IDENTIFICATION("identification"),
    NAME("name"),
    PERMISSIONS("permissions"),
    PRIORITY("priority"),
    TABLIST_PREFIX("tablist-prefix");

    /**
     * The path used for storing the property in the system's storage.
     */
    private final String storagePath;

    /**
     * Constructs a {@link PermissionGroupProperty} with a specified storage path.
     *
     * @param storagePath The path used for storing the property.
     */
    PermissionGroupProperty(@NotNull final String storagePath) {
        this.storagePath = storagePath;
    }

    /**
     * Converts all enum property names to a list of strings.
     *
     * @return A {@link List} of property names as {@link String} instances.
     */
    public static List<String> toStringList() {
        return Arrays.stream(PermissionGroupProperty.values())
                .map(Enum::name)
                .toList();
    }

    /**
     * Retrieves a {@link PermissionGroupProperty} from its string representation.
     *
     * @param s The string representation of the property.
     * @return An {@link Optional} containing the {@link PermissionGroupProperty} if found, otherwise empty.
     */
    public static Optional<PermissionGroupProperty> fromString(@NotNull final String s) {
        return Arrays.stream(PermissionGroupProperty.values())
                .filter(property -> property.name().equalsIgnoreCase(s))
                .findFirst();
    }

}
