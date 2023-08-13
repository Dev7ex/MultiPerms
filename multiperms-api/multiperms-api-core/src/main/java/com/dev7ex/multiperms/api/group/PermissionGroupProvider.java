package com.dev7ex.multiperms.api.group;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface PermissionGroupProvider {

    void register(@NotNull final PermissionGroup group);

    void unregister(final int identification);

    boolean isRegistered(final int identification);

    Optional<PermissionGroup> getGroup(final int identification);

    Optional<PermissionGroup> getGroup(@NotNull final String name);

    PermissionGroup getGroupOrDefault(final int identification);

    PermissionGroup getDefaultGroup();

    Map<Integer, PermissionGroup> getGroups(@NotNull final List<Integer> identifications);

    PermissionGroup getNextBestGroup(@NotNull  final List<PermissionGroup> groups);

    List<PermissionGroup> getExistingGroups(@NotNull final List<Integer> groupIdentifications);

    List<PermissionGroup> getDeadGroups(@NotNull  final List<Integer> groupIdentifications);

    void saveGroup(@NotNull final PermissionGroup group, @NotNull final PermissionGroupProperty... properties);

    void saveGroup(@NotNull final PermissionGroup group);

    PermissionGroupConfiguration getConfiguration();

    Map<Integer, PermissionGroup> getGroups();

}
