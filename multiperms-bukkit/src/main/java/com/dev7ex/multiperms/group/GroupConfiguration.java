package com.dev7ex.multiperms.group;

import com.dev7ex.common.collect.map.ParsedMap;
import com.dev7ex.common.io.file.configuration.Configuration;
import com.dev7ex.common.io.file.configuration.ConfigurationHolder;
import com.dev7ex.common.io.file.configuration.ConfigurationProperties;
import com.dev7ex.common.io.file.configuration.JsonConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroupProperty;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@ConfigurationProperties(fileName = "group.json", provider = JsonConfiguration.class)
public class GroupConfiguration extends Configuration implements PermissionGroupConfiguration {

    public GroupConfiguration(@NotNull final ConfigurationHolder configurationHolder) {
        super(configurationHolder);
    }

    public void load() {
        super.loadFile();
    }

    @Override
    public void add(@NotNull final PermissionGroup group) {
        super.getFileConfiguration().set(group.getIdentification() + "." + PermissionGroupProperty.NAME.getStoragePath(), group.getName());
        super.getFileConfiguration().set(group.getIdentification() + "." + PermissionGroupProperty.DISPLAY_NAME.getStoragePath(), group.getDisplayName());
        super.getFileConfiguration().set(group.getIdentification() + "." + PermissionGroupProperty.COLOR.getStoragePath(), group.getColor());
        super.getFileConfiguration().set(group.getIdentification() + "." + PermissionGroupProperty.PRIORITY.getStoragePath(), group.getPriority());
        super.getFileConfiguration().set(group.getIdentification() + "." + PermissionGroupProperty.TABLIST_PREFIX.getStoragePath(), group.getTablistPrefix());
        super.getFileConfiguration().set(group.getIdentification() + "." + PermissionGroupProperty.CHAT_PREFIX.getStoragePath(), group.getChatPrefix());
        super.getFileConfiguration().set(group.getIdentification() + "." + PermissionGroupProperty.PERMISSIONS.getStoragePath(), group.getPermissions());
        this.save();
    }

    @Override
    public void remove(final int identification) {
        super.getFileConfiguration().set(String.valueOf(identification), null);
        this.save();
    }

    @Override
    public boolean contains(final int identification) {
        return super.getFileConfiguration().contains(String.valueOf(identification));
    }

    @Override
    public void write(final int identification, @NotNull final ParsedMap<PermissionGroupProperty, Object> userData) {
        for (final PermissionGroupProperty property : userData.keySet()) {
            final String path = identification + "." + property.getStoragePath();

            switch (property) {
                case IDENTIFICATION:
                case PRIORITY:
                    super.getFileConfiguration().set(path, userData.getInteger(property));
                    break;

                case NAME:
                case DISPLAY_NAME:
                case CHAT_PREFIX:
                case TABLIST_PREFIX:
                    super.getFileConfiguration().set(path, userData.getString(property));
                    break;

                case PERMISSIONS:
                    final List<String> permissions = super.getFileConfiguration().getStringList(path) == null
                            ? new ArrayList<>()
                            : super.getFileConfiguration().getStringList(path);

                    permissions.addAll(userData.getStringList(PermissionGroupProperty.PERMISSIONS));
                    super.getFileConfiguration().set(path, permissions);
                    break;

                case COLOR:
                    super.getFileConfiguration().set(path, userData.getCharacter(property));
                    break;

                default:
                    break;
            }
        }
        this.save();
    }


    @Override
    public void addPermission(final int identification, @NotNull final String permission) {
        final String path = identification + "." + PermissionGroupProperty.PERMISSIONS.getStoragePath();
        final List<String> permissions = super.getFileConfiguration().getStringList(path) == null
                ? new ArrayList<>()
                : super.getFileConfiguration().getStringList(path);

        if (!permissions.contains(permission)) {
            permissions.add(permission);
            super.getFileConfiguration().set(path, permissions);
            super.saveFile();
        }
    }

    @Override
    public void removePermission(final int identification, @NotNull final String permission) {
        final String path = identification + "." + PermissionGroupProperty.PERMISSIONS.getStoragePath();
        final List<String> permissions = super.getFileConfiguration().getStringList(path);

        if (permissions.contains(permission)) {
            permissions.remove(permission);
            super.getFileConfiguration().set(path, permissions);
            this.save();
        }
    }

    @Override
    public void clearPermissions(final int identification) {
        super.getFileConfiguration().set(identification + "." + PermissionGroupProperty.PERMISSIONS.getStoragePath(), Collections.emptyList());
        this.save();
    }

    @Override
    public @NotNull ParsedMap<PermissionGroupProperty, Object> read(final int identification) {
        final ParsedMap<PermissionGroupProperty, Object> groupData = new ParsedMap<>();

        for (final PermissionGroupProperty property : PermissionGroupProperty.values()) {
            final String storagePath = property.getStoragePath();

            final Object value = switch (property) {
                case IDENTIFICATION, PRIORITY -> super.getFileConfiguration().getInt(storagePath);
                case NAME, DISPLAY_NAME, CHAT_PREFIX, TABLIST_PREFIX -> super.getFileConfiguration().getString(storagePath);
                case PERMISSIONS -> super.getFileConfiguration().getStringList(storagePath);
                case COLOR -> {
                    final String colorStr = super.getFileConfiguration().getString(storagePath);
                    yield  (((colorStr != null) && (!colorStr.isEmpty())) ? (colorStr.charAt(0)) : null);
                }
                default -> null;
            };
            if (value != null) {
                groupData.put(property, value);
            }
        }
        return groupData;
    }

    @Override
    public @NotNull ParsedMap<PermissionGroupProperty, Object> read(final int identification, @NotNull final PermissionGroupProperty... properties) {
        final ParsedMap<PermissionGroupProperty, Object> groupData = new ParsedMap<>();

        if (properties == null || properties.length == 0) {
            return this.read(identification);
        }

        for (final PermissionGroupProperty property : properties) {
            final String storagePath = property.getStoragePath();

            final Object value = switch (property) {
                case IDENTIFICATION, PRIORITY -> super.getFileConfiguration().getInt(storagePath);
                case NAME, DISPLAY_NAME, CHAT_PREFIX, TABLIST_PREFIX -> super.getFileConfiguration().getString(storagePath);
                case PERMISSIONS -> super.getFileConfiguration().getStringList(storagePath);
                case COLOR -> {
                    final String colorStr = super.getFileConfiguration().getString(storagePath);
                    yield  (((colorStr != null) && (!colorStr.isEmpty())) ? (colorStr.charAt(0)) : null);
                }
                default -> null;
            };
            if (value != null) {
                groupData.put(property, value);
            }
        }
        return groupData;
    }

    @Override
    public void save() {
        super.saveFile();
    }

    @Override
    public PermissionGroup getGroup(final int identification) {
        return Group.builder()
                .setIdentification(identification)
                .setName(super.getFileConfiguration().getString(identification + "." + PermissionGroupProperty.NAME.getStoragePath()))
                .setDisplayName(super.getFileConfiguration().getString(identification + "." + PermissionGroupProperty.DISPLAY_NAME.getStoragePath()))
                .setColor(super.getFileConfiguration().getString(identification + "." + PermissionGroupProperty.COLOR.getStoragePath()).charAt(0))
                .setPriority(super.getFileConfiguration().getInt(identification + "." + PermissionGroupProperty.PRIORITY.getStoragePath()))
                .setTablistPrefix(super.getFileConfiguration().getString(identification + "." + PermissionGroupProperty.TABLIST_PREFIX.getStoragePath()))
                .setChatPrefix(super.getFileConfiguration().getString(identification + "." + PermissionGroupProperty.CHAT_PREFIX.getStoragePath()))
                .setPermissions(super.getFileConfiguration().getStringList(identification + "." + PermissionGroupProperty.PERMISSIONS.getStoragePath()))
                .build();
    }

    @Override
    public Map<Integer, PermissionGroup> getGroups() {
        return super.getFileConfiguration().getKeys().stream()
                .collect(Collectors.toMap(
                        Integer::parseInt,
                        key -> this.getGroup(Integer.parseInt(key))
                ));
    }

}
