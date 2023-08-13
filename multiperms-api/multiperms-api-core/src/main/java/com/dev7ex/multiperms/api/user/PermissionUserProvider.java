package com.dev7ex.multiperms.api.user;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface PermissionUserProvider {

    void registerUser(@NotNull final PermissionUser user);

    void unregisterUser(@NotNull final UUID uniqueId);

    Optional<PermissionUser> getUser(@NotNull final UUID uniqueId);

    Optional<PermissionUser> getUser(@NotNull final String name);

    Map<UUID, PermissionUser> getUsers();

    Map<UUID, PermissionUser> getUsers(@NotNull final PermissionGroup group);

    void saveUser(@NotNull final PermissionUser user);

    void saveUser(@NotNull final PermissionUser user, @NotNull final PermissionUserProperty... properties);

}
