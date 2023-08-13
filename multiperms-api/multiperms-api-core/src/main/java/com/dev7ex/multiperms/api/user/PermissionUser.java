package com.dev7ex.multiperms.api.user;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface PermissionUser {

    UUID getUniqueId();

    String getName();

    void setName(@NotNull final String name);

    PermissionUserConfiguration getConfiguration();

    void setConfiguration(@NotNull final PermissionUserConfiguration configuration);

    long getLastLogin();

    void setLastLogin(final long lastLogin);

    String getColoredName();

    PermissionGroup getGroup();

    void setGroup(@NotNull final PermissionGroup group);

    List<PermissionGroup> getSubGroups();

    void setSubGroups(@NotNull final List<PermissionGroup> subGroups);

    List<String> getPermissions();

    List<String> getAllPermissions();

    void setPermissions(@NotNull final List<String> permissions);

    void addPermission(@NotNull final String permission);

    void removePermission(@NotNull final String permission);

    boolean hasPermission(@NotNull final String permission);

    void sendMessage(@NotNull final String message);

}
