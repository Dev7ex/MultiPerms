package com.dev7ex.multiperms.group;

import com.dev7ex.common.bukkit.BukkitCommon;
import com.dev7ex.common.bukkit.plugin.module.PluginModule;
import com.dev7ex.common.collect.map.ParsedMap;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bukkit.user.BukkitPermissionUser;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroupProperty;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

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

        if (!this.configuration.contains(0)) {
            final PermissionGroup group = Group.builder()
                    .setIdentification(0)
                    .setName("default")
                    .setDisplayName("User")
                    .setColor('7')
                    .setPriority(0)
                    .setTablistPrefix("§8[§7User§8] §7")
                    .setChatPrefix("§8[§7User§8]§7")
                    .setPermissions(Collections.emptyList())
                    .build();

            this.groups.put(0, group);
            this.configuration.add(group);
        }

        MultiPermsPlugin.getInstance()
                .getLogger()
                .info("Found: [" + this.groups.values().size() + "] Groups");
    }

    @Override
    public void onDisable() {
        this.groups.clear();
    }

    @Override
    public void register(@NotNull final PermissionGroup group) {
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

    public void invokePermissions(final Player player, final BukkitPermissionUser user) {
        if (BukkitCommon.isPaper()) {
            try {
                final Class<?> entityClazz = Class.forName("org.bukkit.craftbukkit.entity.CraftHumanEntity");
                final Field field = entityClazz.getDeclaredField("perm");
                field.setAccessible(true);
                field.set(player, new GroupPermissible(player, user));

            } catch (final Exception exception) {
                exception.printStackTrace();
            }
            player.updateCommands();
            return;
        }
        final String serverVersion = BukkitCommon.getMinecraftServerVersion();

        try {
            final Class<?> entityClazz = Class.forName("org.bukkit.craftbukkit." + serverVersion + ".entity.CraftHumanEntity");
            final Field field = entityClazz.getDeclaredField("perm");
            field.setAccessible(true);
            field.set(player, new GroupPermissible(player, user));

        } catch (final Exception exception) {
            exception.printStackTrace();
        }
        player.updateCommands();
    }

    @Override
    public Optional<PermissionGroup> getGroup(final int identification) {
        return this.groups.values().stream()
                .filter(permissionGroup -> permissionGroup.getIdentification() == identification)
                .findFirst();
    }

    @Override
    public Optional<PermissionGroup> getGroup(@NotNull final String name) {
        return this.groups.values().stream()
                .filter(permissionGroup -> permissionGroup.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public PermissionGroup getGroupOrDefault(final int identification) {
        return this.getGroup(identification).orElseGet(this::getDefaultGroup);
    }

    @Override
    public Map<Integer, PermissionGroup> getGroups(@NotNull final List<Integer> identifications) {
        return identifications.stream()
                .filter(this.groups::containsKey)
                .collect(Collectors.toMap(id -> id, this.groups::get));
    }

    @Override
    public PermissionGroup getNextBestGroup(@NotNull final List<PermissionGroup> groups) {
        return groups.stream()
                .min(Comparator.naturalOrder())
                .orElseGet(this::getDefaultGroup);
    }

    @Override
    public List<PermissionGroup> getDeadGroups(@NotNull final List<Integer> groupIdentifications) {
        return groupIdentifications.stream()
                .map(this::getGroup)
                .filter(Optional::isEmpty)
                .map(Optional::get)  // Assuming `Optional.get()` is safe in this context
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionGroup> getExistingGroups(@NotNull final List<Integer> groupIdentifications) {
        return groupIdentifications.stream()
                .map(this::getGroup)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
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
