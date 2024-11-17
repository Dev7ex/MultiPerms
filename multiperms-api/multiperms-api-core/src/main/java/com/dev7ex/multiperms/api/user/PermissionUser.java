package com.dev7ex.multiperms.api.user;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Represents a user with permissions within the system. This interface provides
 * methods to manage user-related data such as ID, name, permissions, groups, and
 * configuration.
 *
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface PermissionUser {

    /**
     * Retrieves the unique identifier (UUID) of the user.
     *
     * @return The user's {@link UUID}.
     */
    UUID getUniqueId();

    /**
     * Retrieves the name of the user.
     *
     * @return The user's name as a {@link String}.
     */
    String getName();

    /**
     * Sets the name of the user.
     *
     * @param name The new name for the user.
     */
    void setName(@NotNull final String name);

    /**
     * Retrieves the user's configuration settings.
     *
     * @return The {@link PermissionUserConfiguration} instance associated with the user.
     */
    PermissionUserConfiguration getConfiguration();

    /**
     * Sets the user's configuration settings.
     *
     * @param configuration The {@link PermissionUserConfiguration} to be applied to the user.
     */
    void setConfiguration(@NotNull final PermissionUserConfiguration configuration);

    /**
     * Retrieves the timestamp of the user's first login.
     *
     * @return The first login time as a long value representing milliseconds since the epoch.
     */
    long getFirstLogin();

    /**
     * Sets the timestamp of the user's first login.
     *
     * @param firstLogin The timestamp of the first login in milliseconds since the epoch.
     */
    void setFirstLogin(final long firstLogin);

    /**
     * Retrieves the timestamp of the user's last login.
     *
     * @return The last login time as a long value representing milliseconds since the epoch.
     */
    long getLastLogin();

    /**
     * Sets the timestamp of the user's last login.
     *
     * @param lastLogin The timestamp of the last login in milliseconds since the epoch.
     */
    void setLastLogin(final long lastLogin);

    /**
     * Retrieves the user's name formatted with colors.
     *
     * @return The user's name with color codes as a {@link String}.
     */
    String getColoredName();

    /**
     * Retrieves the primary permission group of the user.
     *
     * @return The {@link PermissionGroup} associated with the user.
     */
    PermissionGroup getGroup();

    /**
     * Sets the primary permission group of the user.
     *
     * @param group The {@link PermissionGroup} to be assigned to the user.
     */
    void setGroup(@NotNull final PermissionGroup group);

    /**
     * Retrieves the subgroups associated with the user.
     *
     * @return A list of {@link PermissionGroup} instances representing the user's subgroups.
     */
    List<PermissionGroup> getSubGroups();

    /**
     * Sets the subgroups associated with the user.
     *
     * @param subGroups A list of {@link PermissionGroup} instances to be assigned to the user.
     */
    void setSubGroups(@NotNull final List<PermissionGroup> subGroups);

    /**
     * Retrieves the permissions directly assigned to the user.
     *
     * @return A list of permissions as {@link String} instances.
     */
    List<String> getPermissions();

    /**
     * Retrieves all permissions available to the user, including those from groups.
     *
     * @return A list of all permissions as {@link String} instances.
     */
    List<String> getAllPermissions();

    /**
     * Sets the permissions directly assigned to the user.
     *
     * @param permissions A list of permissions as {@link String} instances.
     */
    void setPermissions(@NotNull final List<String> permissions);

    /**
     * Adds a specific permission to the user.
     *
     * @param permission The permission to be added, as a {@link String}.
     */
    void addPermission(@NotNull final String permission);

    /**
     * Removes a specific permission from the user.
     *
     * @param permission The permission to be removed, as a {@link String}.
     */
    void removePermission(@NotNull final String permission);

    /**
     * Checks if the user has a specific permission.
     *
     * @param permission The permission to check, as a {@link String}.
     * @return {@code true} if the user has the permission, {@code false} otherwise.
     */
    boolean hasPermission(@NotNull final String permission);

    /**
     * Sends a message to the user.
     *
     * @param message The message to be sent, as a {@link String}.
     */
    void sendMessage(@NotNull final String message);



}
