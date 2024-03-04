package com.dev7ex.multiperms.group;

import com.dev7ex.common.collect.map.ParsedMap;
import com.dev7ex.common.io.file.configuration.Configuration;
import com.dev7ex.common.io.file.configuration.ConfigurationHolder;
import com.dev7ex.common.io.file.configuration.ConfigurationProperties;
import com.dev7ex.common.io.file.configuration.YamlConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroupProperty;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@ConfigurationProperties(fileName = "group.yml", provider = YamlConfiguration.class)
public class GroupConfiguration extends Configuration implements PermissionGroupConfiguration {

    public GroupConfiguration(@NotNull final ConfigurationHolder configurationHolder) {
        super(configurationHolder);
    }

    public void load() {
        super.loadFile();

        if (!this.contains(0)) {
            this.add(Group.builder()
                    .setIdentification(0)
                    .setName("default")
                    .setDisplayName("User")
                    .setColor('7')
                    .setPriority(0)
                    .setTablistPrefix("§8[§7User§8] §7")
                    .setChatPrefix("§8[§7User§8]§7")
                    .setPermissions(Collections.emptyList())
                    .build());
        }
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
        this.saveFile();
    }

    @Override
    public void remove(final int identification) {
        super.getFileConfiguration().set(String.valueOf(identification), null);
        super.saveFile();
    }

    @Override
    public boolean contains(final int identification) {
        return super.getFileConfiguration().contains(String.valueOf(identification));
    }

    @Override
    public void save() {
        super.saveFile();
    }

    @Override
    public void write(final int identification, @NotNull final ParsedMap<PermissionGroupProperty, Object> userData) {
        for (final PermissionGroupProperty property : userData.keySet()) {
            switch (property) {
                case IDENTIFICATION:
                case PRIORITY:
                    super.getFileConfiguration().set(identification + "." + property.getStoragePath(), userData.getInteger(property));
                    break;

                case NAME:
                case DISPLAY_NAME:
                case CHAT_PREFIX:
                case TABLIST_PREFIX:
                    super.getFileConfiguration().set(identification + "." + property.getStoragePath(), userData.getString(property));
                    break;

                case PERMISSIONS:
                    final List<String> permissions = super.getFileConfiguration().getStringList(identification + "." + PermissionGroupProperty.PERMISSIONS.getStoragePath());
                    permissions.addAll(userData.getStringList(PermissionGroupProperty.PERMISSIONS));
                    super.getFileConfiguration().set(identification + "." + property.getStoragePath(), permissions);

                case COLOR:
                    super.getFileConfiguration().set(identification + "." + property.getStoragePath(), userData.getCharacter(property));
                    break;
            }
        }
        this.save();
    }

    @Override
    public void addPermission(final int identification, @NotNull final String permission) {
        final List<String> permissions = super.getFileConfiguration().getStringList(identification + "." + PermissionGroupProperty.PERMISSIONS.getStoragePath());
        permissions.add(permission);
        super.getFileConfiguration().set(identification + "." + PermissionGroupProperty.PERMISSIONS.getStoragePath(), permissions);
        this.save();
    }

    @Override
    public void removePermission(final int identification, @NotNull final String permission) {
        final List<String> permissions = super.getFileConfiguration().getStringList(identification + "." + PermissionGroupProperty.PERMISSIONS.getStoragePath());
        permissions.remove(permission);
        super.getFileConfiguration().set(identification + "." + PermissionGroupProperty.PERMISSIONS.getStoragePath(), permissions);
        this.save();
    }

    @Override
    public void clearPermissions(final int identification) {
        super.getFileConfiguration().set(identification + "." + PermissionGroupProperty.PERMISSIONS.getStoragePath(), Collections.emptyList());
        this.save();
    }

    @Override
    public @NotNull ParsedMap<PermissionGroupProperty, Object> read(final int identification) {
        final ParsedMap<PermissionGroupProperty, Object> groupData = new ParsedMap<>();

        Arrays.stream(PermissionGroupProperty.values()).forEach(property -> {
            switch (property) {
                case IDENTIFICATION:
                    groupData.put(property, super.getFileConfiguration().getInt(property.getStoragePath()));
                    break;

                case NAME:
                case DISPLAY_NAME:
                case CHAT_PREFIX:
                case TABLIST_PREFIX:
                    groupData.put(property, super.getFileConfiguration().getString(property.getStoragePath()));
                    break;

                case PERMISSIONS:
                    groupData.put(property, super.getFileConfiguration().getStringList(property.getStoragePath()));
                    break;

                case COLOR:
                    groupData.put(property, Objects.requireNonNull(super.getFileConfiguration().getString(property.getStoragePath())).charAt(0));
                    break;
            }
        });
        return groupData;
    }

    @Override
    public @NotNull ParsedMap<PermissionGroupProperty, Object> read(final int identification, @NotNull final PermissionGroupProperty... properties) {
        if (properties == null) {
            return this.read(identification);
        }
        final ParsedMap<PermissionGroupProperty, Object> groupData = new ParsedMap<>();

        for (final PermissionGroupProperty property : properties) {
            switch (property) {
                case IDENTIFICATION:
                    groupData.put(property, super.getFileConfiguration().getInt(property.getStoragePath()));
                    break;

                case NAME:
                case DISPLAY_NAME:
                case CHAT_PREFIX:
                case TABLIST_PREFIX:
                    groupData.put(property, super.getFileConfiguration().getString(property.getStoragePath()));
                    break;

                case PERMISSIONS:
                    groupData.put(property, super.getFileConfiguration().getStringList(property.getStoragePath()));
                    break;

                case COLOR:
                    groupData.put(property, Objects.requireNonNull(super.getFileConfiguration().getString(property.getStoragePath())).charAt(0));
                    break;
            }
        }
        return groupData;
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
        final Map<Integer, PermissionGroup> groups = new HashMap<>();

        for (final String groupIdentification : super.getFileConfiguration().getKeys()) {
            final Group group = Group.builder()
                    .setIdentification(Integer.parseInt(groupIdentification))
                    .setName(super.getFileConfiguration().getString(groupIdentification + "." + PermissionGroupProperty.NAME.getStoragePath()))
                    .setPermissions(super.getFileConfiguration().getStringList(groupIdentification + "." + PermissionGroupProperty.PERMISSIONS.getStoragePath()))
                    .setDisplayName(super.getFileConfiguration().getString(groupIdentification + "." + PermissionGroupProperty.DISPLAY_NAME.getStoragePath()))
                    .setPriority(super.getFileConfiguration().getInt(groupIdentification + "." + PermissionGroupProperty.PRIORITY.getStoragePath()))
                    .setColor(super.getFileConfiguration().getString(groupIdentification + "." + PermissionGroupProperty.COLOR.getStoragePath()).toCharArray()[0])
                    .setChatPrefix(super.getFileConfiguration().getString(groupIdentification + "." + PermissionGroupProperty.CHAT_PREFIX.getStoragePath()))
                    .setTablistPrefix(super.getFileConfiguration().getString(groupIdentification + "." + PermissionGroupProperty.TABLIST_PREFIX.getStoragePath()))
                    .build();
            groups.put(group.getIdentification(), group);
        }
        return groups;
    }


}
