package com.dev7ex.multiperms.api.group;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents a group within the permission system. This interface provides methods
 * to manage group attributes such as name, permissions, priority, and display settings.
 * Groups are also comparable based on their priority.
 *
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface PermissionGroup extends Comparable<PermissionGroup> {

    /**
     * Retrieves the identification number of the group.
     *
     * @return The group's identification number.
     */
    int getIdentification();

    /**
     * Sets the identification number of the group.
     *
     * @param identification The identification number to be set.
     */
    void setIdentification(final int identification);

    /**
     * Retrieves the name of the group.
     *
     * @return The group's name as a {@link String}.
     */
    String getName();

    /**
     * Sets the name of the group.
     *
     * @param name The name to be set for the group.
     */
    void setName(@NotNull final String name);

    /**
     * Retrieves the list of permissions associated with the group.
     *
     * @return A list of permissions as {@link String} instances.
     */
    List<String> getPermissions();

    /**
     * Checks if the group has a specific permission.
     *
     * @param permission The permission to check for, as a {@link String}.
     * @return {@code true} if the group has the permission, {@code false} otherwise.
     */
    boolean hasPermission(@NotNull final String permission);

    /**
     * Retrieves the display name of the group.
     *
     * @return The group's display name as a {@link String}.
     */
    String getDisplayName();

    /**
     * Sets the display name of the group.
     *
     * @param displayName The display name to be set for the group.
     */
    void setDisplayName(@NotNull final String displayName);

    /**
     * Retrieves the priority of the group.
     *
     * @return The group's priority as an integer.
     */
    int getPriority();

    /**
     * Sets the priority of the group.
     *
     * @param priority The priority to be set for the group.
     */
    void setPriority(final int priority);

    /**
     * Retrieves the color associated with the group.
     *
     * @return The group's color as a {@code char}.
     */
    char getColor();

    /**
     * Sets the color associated with the group.
     *
     * @param color The color to be set for the group, as a {@code char}.
     */
    void setColor(final char color);

    /**
     * Retrieves the chat prefix for the group.
     *
     * @return The chat prefix as a {@link String}.
     */
    String getChatPrefix();

    /**
     * Sets the chat prefix for the group.
     *
     * @param chatPrefix The chat prefix to be set for the group.
     */
    void setChatPrefix(@NotNull final String chatPrefix);

    /**
     * Retrieves the tab list prefix for the group.
     *
     * @return The tab list prefix as a {@link String}.
     */
    String getTablistPrefix();

    /**
     * Sets the tab list prefix for the group.
     *
     * @param tablistPrefix The tab list prefix to be set for the group.
     */
    void setTablistPrefix(@NotNull final String tablistPrefix);

    /**
     * Retrieves the display name of the group, including color codes.
     *
     * @return The colored display name as a {@link String}.
     */
    String getColoredDisplayName();

    /**
     * Compares this group to another group based on priority.
     *
     * @param group The other {@link PermissionGroup} to compare against.
     * @return A negative integer, zero, or a positive integer as this group's priority
     *         is less than, equal to, or greater than the specified group's priority.
     */
    @Override
    default int compareTo(@NotNull final PermissionGroup group) {
        return Integer.compare(this.getPriority(), group.getPriority());
    }

}
