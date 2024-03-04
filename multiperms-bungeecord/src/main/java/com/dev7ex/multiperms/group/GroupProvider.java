package com.dev7ex.multiperms.group;

import com.dev7ex.common.bungeecord.plugin.module.PluginModule;
import com.dev7ex.common.collect.map.ParsedMap;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroupProperty;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
public class GroupProvider implements PluginModule, PermissionGroupProvider {

    private final Map<Integer, PermissionGroup> groups = new HashMap<>();
    private final PermissionGroupConfiguration configuration;

    public GroupProvider(@NotNull final PermissionGroupConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void onEnable() {
        this.groups.putAll(this.configuration.getGroups());

        MultiPermsPlugin.getInstance().getLogger().info("Found [" + this.groups.values().size() + "] Groups");
    }

    @Override
    public void onDisable() {
        this.groups.clear();
    }

    @Override
    public void register(final PermissionGroup group) {
        this.groups.put(group.getIdentification(), group);
    }

    @Override
    public void unregister(final int identification) {
        this.groups.remove(identification);
    }

    @Override
    public boolean isRegistered(final int identification) {
        return this.groups.containsKey(identification);
    }

    @Override
    public Optional<PermissionGroup> getGroup(final int identification) {
        return this.groups.values().stream().filter(permissionGroup -> permissionGroup.getIdentification() == identification).findFirst();
    }

    @Override
    public Optional<PermissionGroup> getGroup(@NotNull final String name) {
        return this.groups.values().stream().filter(permissionGroup -> permissionGroup.getName().equalsIgnoreCase(name)).findFirst();
    }

    @Override
    public PermissionGroup getGroupOrDefault(final int identification) {
        return (this.getGroup(identification).isEmpty() ? this.getDefaultGroup() : this.getGroup(identification).get());
    }

    @Override
    public Map<Integer, PermissionGroup> getGroups(final List<Integer> identifications) {
        final Map<Integer, PermissionGroup> groups = new HashMap<>();

        for (final int identification : identifications) {
            groups.put(identification, this.groups.get(identification));
        }
        return groups;
    }

    @Override
    public PermissionGroup getNextBestGroup(final List<PermissionGroup> groups) {
        if (groups.isEmpty()) {
            return this.getDefaultGroup();
        }
        Collections.sort(groups);
        return groups.get(0);
    }

    public PermissionGroup getNextWorstGroup(final List<PermissionGroup> groups) {
        if (groups.isEmpty()) {
            return this.getDefaultGroup();
        }
        Collections.sort(groups);
        Collections.reverse(groups);
        return groups.get(0);
    }

    @Override
    public List<PermissionGroup> getDeadGroups(final List<Integer> groupIdentifications) {
        final List<PermissionGroup> deadGroups = new ArrayList<>();

        for (final int identification : groupIdentifications) {
            if (this.getGroup(identification).isPresent()) {
                continue;
            }
            deadGroups.add(this.getGroup(identification).get());
        }
        return deadGroups;
    }

    @Override
    public List<PermissionGroup> getExistingGroups(final List<Integer> groupIdentifications) {
        final List<PermissionGroup> existingGroups = new ArrayList<>();

        for (final int identification : groupIdentifications) {
            if (this.getGroup(identification).isEmpty()) {
                continue;
            }
            existingGroups.add(this.getGroup(identification).get());
        }
        return existingGroups;
    }

    @Override
    public PermissionGroup getDefaultGroup() {
        return this.groups.get(0);
    }

    @Override
    public void saveGroup(@NotNull final PermissionGroup group) {
        this.saveGroup(group, PermissionGroupProperty.IDENTIFICATION,
                PermissionGroupProperty.NAME,
                PermissionGroupProperty.DISPLAY_NAME,
                PermissionGroupProperty.COLOR,
                PermissionGroupProperty.PRIORITY,
                PermissionGroupProperty.TABLIST_PREFIX,
                PermissionGroupProperty.CHAT_PREFIX,
                PermissionGroupProperty.PERMISSIONS);
    }

    @Override
    public void saveGroup(@NotNull final PermissionGroup group, @NotNull final PermissionGroupProperty... properties) {
        final ParsedMap<PermissionGroupProperty, Object> data = new ParsedMap<>();

        for (final PermissionGroupProperty property : properties) {
            switch (property) {
                case IDENTIFICATION:
                    data.put(property, group.getIdentification());
                    break;

                case NAME:
                    data.put(property, group.getName());
                    break;

                case DISPLAY_NAME:
                    data.put(property, group.getDisplayName());
                    break;

                case COLOR:
                    data.put(property, group.getColor());
                    break;

                case PRIORITY:
                    data.put(property, group.getPriority());
                    break;

                case TABLIST_PREFIX:
                    data.put(property, group.getTablistPrefix());
                    break;

                case CHAT_PREFIX:
                    data.put(property, group.getChatPrefix());
                    break;

                case PERMISSIONS:
                    data.put(property, group.getPermissions());
                    break;
            }
        }
        this.configuration.write(group.getIdentification(), data);
    }

}
