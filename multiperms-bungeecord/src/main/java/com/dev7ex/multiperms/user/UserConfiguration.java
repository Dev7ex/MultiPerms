package com.dev7ex.multiperms.user;

import com.dev7ex.common.collect.map.ParsedMap;
import com.dev7ex.common.io.file.configuration.ConfigurationProvider;
import com.dev7ex.common.io.file.configuration.FileConfiguration;
import com.dev7ex.common.io.file.configuration.YamlConfiguration;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.user.PermissionUser;
import com.dev7ex.multiperms.api.user.PermissionUserConfiguration;
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
public class UserConfiguration implements PermissionUserConfiguration {

    private final PermissionUser user;
    private final File configurationFile;
    private final FileConfiguration fileConfiguration;

    @SneakyThrows
    public UserConfiguration(final PermissionUser user) {
        this.user = user;

        this.configurationFile = new File(MultiPermsPlugin.getInstance().getSubFolder("user")
                + File.separator + user.getUniqueId().toString() + ".yml");

        if (!this.configurationFile.exists()) {
            final boolean created = this.configurationFile.createNewFile();
        }
        this.fileConfiguration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.configurationFile);
        this.fileConfiguration.addDefault(PermissionUserProperty.UNIQUE_ID.getStoragePath(), user.getUniqueId().toString());
        this.fileConfiguration.addDefault(PermissionUserProperty.NAME.getStoragePath(), user.getName());
        this.fileConfiguration.addDefault(PermissionUserProperty.LAST_LOGIN.getStoragePath(), 0L);
        this.fileConfiguration.addDefault(PermissionUserProperty.GROUP.getStoragePath(), 0);
        this.fileConfiguration.addDefault(PermissionUserProperty.SUB_GROUPS.getStoragePath(), Lists.newArrayList());
        this.fileConfiguration.addDefault(PermissionUserProperty.PERMISSION.getStoragePath(), Lists.newArrayList());
        this.save();
    }

    @Override
    public ParsedMap<PermissionUserProperty, Object> read() {
        final ParsedMap<PermissionUserProperty, Object> userData = new ParsedMap<>();

        Arrays.stream(PermissionUserProperty.values()).forEach(property -> {
            switch (property) {
                case UNIQUE_ID:
                    userData.put(property, UUID.fromString(Objects.requireNonNull(this.fileConfiguration.getString(property.getStoragePath()))));
                    break;

                case NAME:
                    userData.put(property, this.fileConfiguration.getString(property.getStoragePath()));
                    break;

                case LAST_LOGIN:
                    userData.put(property, this.fileConfiguration.getLong(property.getStoragePath()));
                    break;


                case GROUP:
                    userData.put(property, this.fileConfiguration.getInt(property.getStoragePath()));
                    break;

                case SUB_GROUPS:
                    userData.put(property, this.fileConfiguration.getIntegerList(property.getStoragePath()));
                    break;

                case PERMISSION:
                    userData.put(property, this.fileConfiguration.getStringList(property.getStoragePath()));
                    break;
            }
        });
        return userData;
    }

    @Override
    public ParsedMap<PermissionUserProperty, Object> read(@NotNull final PermissionUserProperty... properties) {
        final ParsedMap<PermissionUserProperty, Object> userData = new ParsedMap<>();

        for (final PermissionUserProperty property : properties) {
            switch (property) {
                case UNIQUE_ID:
                    userData.put(PermissionUserProperty.UNIQUE_ID, UUID.fromString(Objects.requireNonNull(this.fileConfiguration.getString(property.getStoragePath()))));
                    break;

                case NAME:
                    userData.put(property, this.fileConfiguration.getString(property.getStoragePath()));
                    break;

                case LAST_LOGIN:
                    userData.put(property, this.fileConfiguration.getLong(property.getStoragePath()));
                    break;

                case GROUP:
                    userData.put(property, this.fileConfiguration.getInt(property.getStoragePath()));
                    break;

                case SUB_GROUPS:
                    userData.put(property, this.fileConfiguration.getIntegerList(property.getStoragePath()));
                    break;

                case PERMISSION:
                    userData.put(property, this.fileConfiguration.getStringList(property.getStoragePath()));
                    break;

                default:
                    return this.read();
            }
        }
        return userData;
    }

    @Override
    public void write(final ParsedMap<PermissionUserProperty, Object> userData) {
        for (final PermissionUserProperty property : userData.keySet()) {
            switch (property) {
                case UNIQUE_ID:
                case NAME:
                    this.fileConfiguration.set(property.getStoragePath(), userData.getString(property));
                    break;

                case LAST_LOGIN:
                    this.fileConfiguration.set(property.getStoragePath(), userData.getLong(property));
                    break;

                case GROUP:
                    this.fileConfiguration.set(property.getStoragePath(), userData.getInteger(PermissionUserProperty.GROUP));
                    break;

                case SUB_GROUPS:
                    this.fileConfiguration.set(property.getStoragePath(), userData.getIntList(PermissionUserProperty.SUB_GROUPS));
                    break;

                case PERMISSION:
                    this.fileConfiguration.set(property.getStoragePath(), userData.getStringList(PermissionUserProperty.PERMISSION));
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
    public void removePermission(final String permission) {
        final List<String> permissions = this.fileConfiguration.getStringList(PermissionUserProperty.PERMISSION.getStoragePath());
        permissions.remove(permission);
        this.fileConfiguration.set(PermissionUserProperty.PERMISSION.getStoragePath(), permissions);
        this.save();
    }

    @Override
    public void addPermission(final String permission) {
        final List<String> permissions = this.fileConfiguration.getStringList(PermissionUserProperty.PERMISSION.getStoragePath());
        permissions.add(permission);
        this.fileConfiguration.set(PermissionUserProperty.PERMISSION.getStoragePath(), permissions);
        this.save();
    }

    @Override
    public void clearPermissions() {
        this.fileConfiguration.set(PermissionUserProperty.PERMISSION.getStoragePath(), Collections.emptyList());
    }

    @Override
    @SneakyThrows
    public void save() {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.fileConfiguration, this.configurationFile);
    }

}
