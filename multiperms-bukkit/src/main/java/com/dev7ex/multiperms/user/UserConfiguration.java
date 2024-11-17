package com.dev7ex.multiperms.user;

import com.dev7ex.common.collect.map.ParsedMap;
import com.dev7ex.common.io.file.configuration.ConfigurationProvider;
import com.dev7ex.common.io.file.configuration.FileConfiguration;
import com.dev7ex.common.io.file.configuration.JsonConfiguration;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bukkit.user.BukkitPermissionUserConfiguration;
import com.dev7ex.multiperms.api.user.PermissionUser;
import com.dev7ex.multiperms.api.user.PermissionUserProperty;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public class UserConfiguration implements BukkitPermissionUserConfiguration {

    private final File configurationFile;
    private final FileConfiguration fileConfiguration;

    @SneakyThrows
    public UserConfiguration(final PermissionUser user) {
        this.configurationFile = new File(MultiPermsPlugin.getInstance().getUserFolder()
                + File.separator + user.getUniqueId().toString() + ".json");

        if (!this.configurationFile.exists()) {
            final boolean created = this.configurationFile.createNewFile();
        }
        this.fileConfiguration = ConfigurationProvider.getProvider(JsonConfiguration.class).load(this.configurationFile);
        this.fileConfiguration.addDefault(PermissionUserProperty.UNIQUE_ID.getStoragePath(), user.getUniqueId().toString());
        this.fileConfiguration.addDefault(PermissionUserProperty.NAME.getStoragePath(), user.getName());
        this.fileConfiguration.addDefault(PermissionUserProperty.FIRST_LOGIN.getStoragePath(), 0L);
        this.fileConfiguration.addDefault(PermissionUserProperty.LAST_LOGIN.getStoragePath(), 0L);
        this.fileConfiguration.addDefault(PermissionUserProperty.GROUP.getStoragePath(), 0);
        this.fileConfiguration.addDefault(PermissionUserProperty.SUB_GROUPS.getStoragePath(), Lists.newArrayList());
        this.fileConfiguration.addDefault(PermissionUserProperty.PERMISSION.getStoragePath(), Lists.newArrayList());
        this.save();
    }

    @Override
    public @NotNull ParsedMap<PermissionUserProperty, Object> read() {
        final ParsedMap<PermissionUserProperty, Object> userData = new ParsedMap<>();

        Arrays.stream(PermissionUserProperty.values()).forEach(property -> {
            final String storagePath = property.getStoragePath();

            switch (property) {
                case UNIQUE_ID:
                    final String uniqueIdStr = this.fileConfiguration.getString(storagePath);
                    if (uniqueIdStr != null) {
                        userData.put(property, UUID.fromString(uniqueIdStr));
                    }
                    break;

                case NAME:
                    userData.put(property, this.fileConfiguration.getString(storagePath));
                    break;

                case LAST_LOGIN: case FIRST_LOGIN:
                    userData.put(property, this.fileConfiguration.getLong(storagePath));
                    break;

                case GROUP:
                    userData.put(property, this.fileConfiguration.getInt(storagePath));
                    break;

                case SUB_GROUPS:
                    userData.put(property, this.fileConfiguration.getIntegerList(storagePath));
                    break;

                case PERMISSION:
                    userData.put(property, this.fileConfiguration.getStringList(storagePath));
                    break;

                default:
                    throw new IllegalStateException("Unhandled property: " + property);
            }
        });
        return userData;
    }


    @Override
    public @NotNull ParsedMap<PermissionUserProperty, Object> read(@NotNull final PermissionUserProperty... properties) {
        final ParsedMap<PermissionUserProperty, Object> userData = new ParsedMap<>();

        for (final PermissionUserProperty property : properties) {
            final String storagePath = property.getStoragePath();

            switch (property) {
                case UNIQUE_ID:
                    final String uniqueIdStr = this.fileConfiguration.getString(storagePath);
                    if (uniqueIdStr != null) {
                        userData.put(property, UUID.fromString(uniqueIdStr));
                    }
                    break;

                case NAME:
                    userData.put(property, this.fileConfiguration.getString(storagePath));
                    break;

                case LAST_LOGIN: case FIRST_LOGIN:
                    userData.put(property, this.fileConfiguration.getLong(storagePath));
                    break;

                case GROUP:
                    userData.put(property, this.fileConfiguration.getInt(storagePath));
                    break;

                case SUB_GROUPS:
                    userData.put(property, this.fileConfiguration.getIntegerList(storagePath));
                    break;

                case PERMISSION:
                    userData.put(property, this.fileConfiguration.getStringList(storagePath));
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported property: " + property);
            }
        }
        return userData;
    }


    @Override
    public void write(final ParsedMap<PermissionUserProperty, Object> userData) {
        for (final PermissionUserProperty property : userData.keySet()) {
            final String storagePath = property.getStoragePath();

            switch (property) {
                case UNIQUE_ID:
                case NAME:
                    this.fileConfiguration.set(storagePath, userData.getString(property));
                    break;

                case LAST_LOGIN: case FIRST_LOGIN:
                    this.fileConfiguration.set(storagePath, userData.getLong(property));
                    break;

                case GROUP:
                    this.fileConfiguration.set(storagePath, userData.getInteger(property));
                    break;

                case SUB_GROUPS:
                    this.fileConfiguration.set(storagePath, userData.getIntList(property));
                    break;

                case PERMISSION:
                    this.fileConfiguration.set(storagePath, userData.getStringList(property));
                    break;

                default:
                    break;
            }
        }
        this.save();
    }


    @Override
    public void write(@NotNull final PermissionUserProperty property, @NotNull final Object value) {
        switch (property) {
            case UNIQUE_ID:
            case NAME:
            case FIRST_LOGIN:
            case LAST_LOGIN:
            case GROUP:
            case SUB_GROUPS:
            case PERMISSION:
                this.fileConfiguration.set(property.getStoragePath(), value);
                break;
        }
        this.save();
    }

    @Override
    public void removePermission(@NotNull final String permission) {
        final List<String> permissions = new ArrayList<>(this.fileConfiguration.getStringList(PermissionUserProperty.PERMISSION.getStoragePath()));

        if (permissions.remove(permission)) {
            this.fileConfiguration.set(PermissionUserProperty.PERMISSION.getStoragePath(), permissions);
            this.save();
        }
    }

    @Override
    public void addPermission(@NotNull final String permission) {
        final List<String> permissions = new ArrayList<>(this.fileConfiguration.getStringList(PermissionUserProperty.PERMISSION.getStoragePath()));

        if (!permissions.contains(permission)) {
            permissions.add(permission);
            this.fileConfiguration.set(PermissionUserProperty.PERMISSION.getStoragePath(), permissions);
            this.save();
        }
    }

    @Override
    public void clearPermissions() {
        this.fileConfiguration.set(PermissionUserProperty.PERMISSION.getStoragePath(), Collections.emptyList());
    }

    @Override
    @SneakyThrows
    public void save() {
        ConfigurationProvider.getProvider(JsonConfiguration.class).save(this.fileConfiguration, this.configurationFile);
    }

}
