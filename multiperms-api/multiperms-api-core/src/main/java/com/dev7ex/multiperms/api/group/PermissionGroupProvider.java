package com.dev7ex.multiperms.api.group;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Provides methods for managing and accessing permission groups within the system.
 * This interface allows for registering, unregistering, retrieving, and saving groups,
 * as well as obtaining configurations and handling group properties.
 *
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface PermissionGroupProvider {

    /**
     * Registers a new permission group in the system.
     *
     * @param group The {@link PermissionGroup} to be registered.
     */
    void register(@NotNull final PermissionGroup group);

    /**
     * Unregisters a permission group from the system by its identification number.
     *
     * @param identification The identification number of the group to be unregistered.
     */
    void unregister(final int identification);

    /**
     * Checks if a permission group with the given identification number is registered.
     *
     * @param identification The identification number to check.
     * @return {@code true} if the group is registered, {@code false} otherwise.
     */
    boolean isRegistered(final int identification);

    /**
     * Retrieves a permission group by its identification number.
     *
     * @param identification The identification number of the group.
     * @return An {@link Optional} containing the {@link PermissionGroup} if found, otherwise empty.
     */
    Optional<PermissionGroup> getGroup(final int identification);

    /**
     * Retrieves a permission group by its name.
     *
     * @param name The name of the group.
     * @return An {@link Optional} containing the {@link PermissionGroup} if found, otherwise empty.
     */
    Optional<PermissionGroup> getGroup(@NotNull final String name);

    /**
     * Retrieves a permission group by its identification number, or returns a default group if not found.
     *
     * @param identification The identification number of the group.
     * @return The {@link PermissionGroup} if found, or the default group otherwise.
     */
    PermissionGroup getGroupOrDefault(final int identification);

    /**
     * Retrieves the default permission group.
     *
     * @return The default {@link PermissionGroup}.
     */
    PermissionGroup getDefaultGroup();

    /**
     * Retrieves permission groups associated with a list of identification numbers.
     *
     * @param identifications A {@link List} of identification numbers.
     * @return A {@link Map} of {@link PermissionGroup} instances keyed by their identification numbers.
     */
    Map<Integer, PermissionGroup> getGroups(@NotNull final List<Integer> identifications);

    /**
     * Finds the next best permission group from a list of groups based on priority.
     *
     * @param groups A {@link List} of {@link PermissionGroup} instances.
     * @return The {@link PermissionGroup} with the highest priority from the provided list.
     */
    PermissionGroup getNextBestGroup(@NotNull final List<PermissionGroup> groups);

    /**
     * Retrieves existing permission groups that match a list of identification numbers.
     *
     * @param groupIdentifications A {@link List} of group identification numbers.
     * @return A {@link List} of existing {@link PermissionGroup} instances.
     */
    List<PermissionGroup> getExistingGroups(@NotNull final List<Integer> groupIdentifications);

    /**
     * Retrieves permission groups that no longer exist or are invalid, based on a list of identification numbers.
     *
     * @param groupIdentifications A {@link List} of group identification numbers.
     * @return A {@link List} of dead {@link PermissionGroup} instances.
     */
    List<PermissionGroup> getDeadGroups(@NotNull final List<Integer> groupIdentifications);

    /**
     * Saves the state of a specific permission group, including specified properties.
     *
     * @param group The {@link PermissionGroup} to be saved.
     * @param properties The properties to be saved for the group.
     */
    void saveGroup(@NotNull final PermissionGroup group, @NotNull final PermissionGroupProperty... properties);

    /**
     * Saves the state of a specific permission group.
     *
     * @param group The {@link PermissionGroup} to be saved.
     */
    void saveGroup(@NotNull final PermissionGroup group);

    /**
     * Retrieves the configuration for permission groups.
     *
     * @return The {@link PermissionGroupConfiguration} instance managing group configurations.
     */
    PermissionGroupConfiguration getConfiguration();

    /**
     * Retrieves all registered permission groups.
     *
     * @return A {@link Map} of all {@link PermissionGroup} instances, keyed by their identification numbers.
     */
    Map<Integer, PermissionGroup> getGroups();

}
