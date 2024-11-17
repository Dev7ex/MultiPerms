package com.dev7ex.multiperms.api.group;

import com.dev7ex.common.collect.map.ParsedMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Provides methods for managing and configuring permission groups within the system.
 * This interface allows for adding, removing, saving, and accessing group configurations,
 * as well as modifying group permissions and reading group data.
 *
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface PermissionGroupConfiguration {

    /**
     * Adds a new permission group to the configuration.
     *
     * @param group The {@link PermissionGroup} to be added.
     */
    void add(@NotNull final PermissionGroup group);

    /**
     * Removes a permission group from the configuration by its identification number.
     *
     * @param identification The identification number of the group to be removed.
     */
    void remove(final int identification);

    /**
     * Checks if a permission group with the given identification number exists in the configuration.
     *
     * @param identification The identification number to check.
     * @return {@code true} if the group exists, {@code false} otherwise.
     */
    boolean contains(final int identification);

    /**
     * Writes data for a permission group to the configuration.
     *
     * @param identification The identification number of the group.
     * @param groupData A {@link ParsedMap} containing the properties and values to be written.
     */
    void write(final int identification, @NotNull final ParsedMap<PermissionGroupProperty, Object> groupData);

    /**
     * Adds a specific permission to a permission group.
     *
     * @param identification The identification number of the group.
     * @param permission The permission to be added, as a {@link String}.
     */
    void addPermission(final int identification, @NotNull final String permission);

    /**
     * Removes a specific permission from a permission group.
     *
     * @param identification The identification number of the group.
     * @param permission The permission to be removed, as a {@link String}.
     */
    void removePermission(final int identification, @NotNull final String permission);

    /**
     * Clears all permissions from a permission group.
     *
     * @param identification The identification number of the group.
     */
    void clearPermissions(final int identification);

    /**
     * Reads and retrieves the data for a permission group.
     *
     * @param identification The identification number of the group.
     * @return A {@link ParsedMap} containing the properties and values of the group.
     */
    @NotNull
    ParsedMap<PermissionGroupProperty, Object> read(final int identification);

    /**
     * Reads specific properties of a permission group.
     *
     * @param identification The identification number of the group.
     * @param properties The properties to be retrieved.
     * @return A {@link ParsedMap} containing the specified properties and their values.
     */
    @NotNull
    ParsedMap<PermissionGroupProperty, Object> read(final int identification, final PermissionGroupProperty... properties);

    /**
     * Saves the current state of the permission group configuration to persistent storage.
     */
    void save();

    /**
     * Retrieves a permission group by its identification number.
     *
     * @param identification The identification number of the group.
     * @return The {@link PermissionGroup} associated with the given identification number.
     */
    PermissionGroup getGroup(final int identification);

    /**
     * Retrieves all permission groups in the configuration.
     *
     * @return A {@link Map} containing all permission groups, keyed by their identification numbers.
     */
    Map<Integer, PermissionGroup> getGroups();

}
