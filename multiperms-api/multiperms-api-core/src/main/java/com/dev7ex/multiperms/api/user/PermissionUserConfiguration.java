package com.dev7ex.multiperms.api.user;

import com.dev7ex.common.collect.map.ParsedMap;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface PermissionUserConfiguration {

    ParsedMap<PermissionUserProperty, Object> read();

    ParsedMap<PermissionUserProperty, Object> read(@NotNull final PermissionUserProperty... properties);

    void write(@NotNull final ParsedMap<PermissionUserProperty, Object> userData);

    void write(@NotNull final PermissionUserProperty property, @NotNull final Object value);

    void removePermission(final String permission);

    void addPermission(final String permission);

    void clearPermissions();

    void save();

}
