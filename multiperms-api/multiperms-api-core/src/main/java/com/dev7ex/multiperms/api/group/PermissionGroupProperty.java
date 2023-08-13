package com.dev7ex.multiperms.api.group;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
public enum PermissionGroupProperty {

    IDENTIFICATION("identification"),
    NAME("name"),
    PERMISSIONS("permissions"),
    DISPLAY_NAME("display-name"),
    PRIORITY("priority"),
    COLOR("color"),
    CHAT_PREFIX("chat-prefix"),
    TABLIST_PREFIX("tablist-prefix");

    private final String storagePath;

    PermissionGroupProperty(final String storagePath) {
        this.storagePath = storagePath;
    }

    public static List<String> toStringList() {
        return Arrays.stream(PermissionGroupProperty.values()).map(Enum::name).toList();
    }

    public static Optional<PermissionGroupProperty> fromString(@NotNull final String s) {
        return Arrays.stream(PermissionGroupProperty.values()).filter(property -> property.name().equalsIgnoreCase(s)).findFirst();
    }

}
