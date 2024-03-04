package com.dev7ex.multiperms.user;

import com.dev7ex.common.bukkit.plugin.module.PluginModule;
import com.dev7ex.common.collect.map.ParsedMap;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import com.dev7ex.multiperms.api.user.PermissionUser;
import com.dev7ex.multiperms.api.user.PermissionUserConfiguration;
import com.dev7ex.multiperms.api.user.PermissionUserProperty;
import com.dev7ex.multiperms.api.user.PermissionUserProvider;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
public class UserService implements PluginModule, PermissionUserProvider {

    private final Map<UUID, PermissionUser> users = new HashMap<>();
    private final PermissionGroupProvider groupProvider;

    public UserService(final PermissionGroupProvider groupProvider) {
        this.groupProvider = groupProvider;
    }

    @Override
    public void onEnable() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final PermissionUser user = new User(player.getUniqueId(), player.getName());
            final PermissionUserConfiguration userConfiguration = new UserConfiguration(user);

            final ParsedMap<PermissionUserProperty, Object> userData = userConfiguration.read(); // Read Data from Config
            final List<PermissionGroup> subGroups = MultiPermsPlugin.getInstance().getGroupProvider().getExistingGroups(userData.getIntList(PermissionUserProperty.SUB_GROUPS)); // Get SubGroups from config
            final PermissionGroup userGroup = MultiPermsPlugin.getInstance().getGroupProvider().getGroupOrDefault(userData.getInteger(PermissionUserProperty.GROUP));

            subGroups.removeIf(group -> group.getIdentification() == userGroup.getIdentification());

            user.setConfiguration(userConfiguration);
            user.setGroup(userGroup);
            user.setPermissions(userData.getStringList(PermissionUserProperty.PERMISSION));
            user.setSubGroups(subGroups);

            user.setConfiguration(userConfiguration);

            this.registerUser(user);

            MultiPermsPlugin.getInstance().getGroupProvider().invokePermissions(player, user);

            this.saveUser(user, PermissionUserProperty.GROUP, PermissionUserProperty.SUB_GROUPS);
        }

        if (MultiPermsPlugin.getInstance().getConfiguration().isTablistEnabled()) {
            for (final PermissionUser user : this.users.values()) {
                MultiPermsPlugin.getInstance().getScoreboardProvider().updateNameTags(Objects.requireNonNull(Bukkit.getPlayer(user.getUniqueId())), user);
            }
        }
    }

    @Override
    public void onDisable() {
        for (final PermissionUser user : this.users.values()) {
            this.saveUser(user);
        }
        this.users.clear();
    }

    @Override
    public void registerUser(@NotNull final PermissionUser user) {
        this.users.put(user.getUniqueId(), user);
    }

    @Override
    public void unregisterUser(@NotNull final UUID uniqueId) {
        this.users.remove(uniqueId);
    }

    @Override
    public Optional<PermissionUser> getUser(@NotNull final UUID uniqueId) {
        return Optional.ofNullable(this.users.get(uniqueId));
    }

    @Override
    public Optional<PermissionUser> getUser(@NotNull final String name) {
        return this.users.values().stream().filter(permissionUser -> permissionUser.getName().equalsIgnoreCase(name)).findFirst();
    }

    @Override
    public Map<UUID, PermissionUser> getUsers(@NotNull final PermissionGroup group) {
        final Map<UUID, PermissionUser> users = new HashMap<>();

        for (final PermissionUser onlineUser : this.users.values()) {
            if (onlineUser.getGroup().getIdentification() != group.getIdentification()) {
                continue;
            }
            users.put(onlineUser.getUniqueId(), onlineUser);
        }
        return users;
    }

    @Override
    public void saveUser(@NotNull final PermissionUser user) {
        this.saveUser(user, PermissionUserProperty.UNIQUE_ID,
                PermissionUserProperty.NAME,
                PermissionUserProperty.LAST_LOGIN,
                PermissionUserProperty.GROUP,
                PermissionUserProperty.SUB_GROUPS,
                PermissionUserProperty.PERMISSION);
    }

    @Override
    public void saveUser(@NotNull final PermissionUser user, @NotNull final PermissionUserProperty... properties) {
        final ParsedMap<PermissionUserProperty, Object> data = new ParsedMap<>();

        for (final PermissionUserProperty property : properties) {
            switch (property) {
                case UNIQUE_ID:
                    data.put(property, user.getUniqueId());
                    break;

                case NAME:
                    data.put(property, user.getName());
                    break;

                case LAST_LOGIN:
                    data.put(property, user.getLastLogin());
                    break;

                case GROUP:
                    data.put(property, user.getGroup().getIdentification());
                    break;

                case SUB_GROUPS:
                    data.put(property, user.getSubGroups().stream().map(PermissionGroup::getIdentification).collect(Collectors.toList()));
                    break;

                case PERMISSION:
                    data.put(property, user.getPermissions());
                    break;

                default:
                    this.saveUser(user);
                    break;
            }
            user.getConfiguration().write(data);
        }
    }

}
