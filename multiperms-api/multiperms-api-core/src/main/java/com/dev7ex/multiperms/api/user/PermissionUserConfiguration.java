package com.dev7ex.multiperms.api.user;

import com.dev7ex.common.collect.map.ParsedMap;
import org.jetbrains.annotations.NotNull;

/**
 * Provides methods for managing and configuring a user's properties and permissions within the system.
 * This interface allows reading, writing, and modifying user data, as well as saving changes.
 *
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface PermissionUserConfiguration {

    /**
     * Reads and retrieves all properties associated with the user.
     *
     * @return A {@link ParsedMap} containing user properties and their corresponding values.
     */
    @NotNull ParsedMap<PermissionUserProperty, Object> read();

    /**
     * Reads and retrieves specific properties associated with the user.
     *
     * @param properties The properties to be retrieved.
     * @return A {@link ParsedMap} containing the specified user properties and their corresponding values.
     */
    @NotNull ParsedMap<PermissionUserProperty, Object> read(@NotNull final PermissionUserProperty... properties);

    /**
     * Writes a set of properties and their corresponding values to the user's configuration.
     *
     * @param userData A {@link ParsedMap} containing properties and their values to be written.
     */
    void write(@NotNull final ParsedMap<PermissionUserProperty, Object> userData);

    /**
     * Writes a specific property and its value to the user's configuration.
     *
     * @param property The property to be written.
     * @param value The value to be assigned to the property.
     */
    void write(@NotNull final PermissionUserProperty property, @NotNull final Object value);

    /**
     * Removes a specific permission from the user's configuration.
     *
     * @param permission The permission to be removed, as a {@link String}.
     */
    void removePermission(@NotNull final String permission);

    /**
     * Adds a specific permission to the user's configuration.
     *
     * @param permission The permission to be added, as a {@link String}.
     */
    void addPermission(@NotNull final String permission);

    /**
     * Clears all permissions associated with the user.
     */
    void clearPermissions();

    /**
     * Saves the current state of the user's configuration to persistent storage.
     */
    void save();

}

