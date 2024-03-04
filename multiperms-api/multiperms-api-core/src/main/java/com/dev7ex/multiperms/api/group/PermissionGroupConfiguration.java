package com.dev7ex.multiperms.api.group;

import com.dev7ex.common.collect.map.ParsedMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface PermissionGroupConfiguration {

    void add(@NotNull final PermissionGroup group);

    void remove(final int identification);

    boolean contains(final int identification);

    void save();

    void write(final int identification, @NotNull final ParsedMap<PermissionGroupProperty, Object> groupData);

    void addPermission(final int identification, @NotNull final String permission);

    void removePermission(final int identification, @NotNull final String permission);

    void clearPermissions(final int identification);

    @NotNull
    ParsedMap<PermissionGroupProperty, Object> read(final int identification);

    @NotNull
    ParsedMap<PermissionGroupProperty, Object> read(final int identification, final PermissionGroupProperty... properties);

    PermissionGroup getGroup(final int identification);

    Map<Integer, PermissionGroup> getGroups();

}
