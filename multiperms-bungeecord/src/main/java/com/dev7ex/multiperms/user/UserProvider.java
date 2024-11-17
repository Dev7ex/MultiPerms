package com.dev7ex.multiperms.user;

import com.dev7ex.common.bungeecord.plugin.module.PluginModule;
import com.dev7ex.common.collect.map.ParsedMap;
import com.dev7ex.multiperms.MultiPermsConfiguration;
import com.dev7ex.multiperms.api.bungeecord.user.BungeePermissionUser;
import com.dev7ex.multiperms.api.bungeecord.user.BungeePermissionUserConfiguration;
import com.dev7ex.multiperms.api.bungeecord.user.BungeePermissionUserProvider;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUserProperty;
import com.dev7ex.multiperms.group.GroupProvider;
import lombok.AccessLevel;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
public class UserProvider implements PluginModule, BungeePermissionUserProvider {

    private final Map<UUID, BungeePermissionUser> users = new HashMap<>();
    private final MultiPermsConfiguration configuration;
    private final GroupProvider groupProvider;

    public UserProvider(@NotNull final MultiPermsConfiguration configuration,
                        @NotNull final GroupProvider groupProvider) {
        this.configuration = configuration;
        this.groupProvider = groupProvider;
    }

    @Override
    public void onEnable() {
        for (final ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            final BungeePermissionUser user = new User(player.getUniqueId(), player.getName());
            final BungeePermissionUserConfiguration userConfiguration = new UserConfiguration(user);

            final ParsedMap<PermissionUserProperty, Object> userData = userConfiguration.read(); // Read Data from Config
            final List<PermissionGroup> subGroups = this.groupProvider.getExistingGroups(userData.getIntList(PermissionUserProperty.SUB_GROUPS)); // Get SubGroups from config
            final PermissionGroup userGroup = this.groupProvider.getGroupOrDefault(userData.getInteger(PermissionUserProperty.GROUP));

            subGroups.removeIf(group -> group.getIdentification() == userGroup.getIdentification());

            user.setConfiguration(userConfiguration);
            user.setGroup(userGroup);
            user.setPermissions(userData.getStringList(PermissionUserProperty.PERMISSION));
            user.setSubGroups(subGroups);
            user.setConfiguration(userConfiguration);

            this.users.put(user.getUniqueId(), user);

            this.saveUser(user, PermissionUserProperty.GROUP, PermissionUserProperty.SUB_GROUPS);
        }
    }

    @Override
    public void onDisable() {
        this.users.values().forEach(this::saveUser);
        this.users.clear();
    }

    @Override
    public void register(@NotNull final BungeePermissionUser user) {
        this.users.put(user.getUniqueId(), user);
    }

    @Override
    public void unregister(@NotNull final UUID uniqueId) {
        this.users.remove(uniqueId);
    }

    @Override
    public Optional<BungeePermissionUser> getUser(@NotNull final UUID uniqueId) {
        return Optional.ofNullable(this.users.get(uniqueId));
    }

    @Override
    public Optional<BungeePermissionUser> getUser(@NotNull final String name) {
        return this.users.values()
                .stream()
                .filter(permissionUser -> permissionUser.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public @NotNull Map<UUID, BungeePermissionUser> getUsers(@NotNull final PermissionGroup group) {
        return this.users.values().stream()
                .filter(onlineUser -> onlineUser.getGroup().getIdentification() == group.getIdentification())
                .collect(Collectors.toMap(BungeePermissionUser::getUniqueId, onlineUser -> onlineUser));
    }

    @Override
    public void saveUser(@NotNull final BungeePermissionUser user) {
        this.saveUser(user, PermissionUserProperty.UNIQUE_ID,
                PermissionUserProperty.NAME,
                PermissionUserProperty.LAST_LOGIN,
                PermissionUserProperty.GROUP,
                PermissionUserProperty.SUB_GROUPS,
                PermissionUserProperty.PERMISSION);
    }

    @Override
    public void saveUser(@NotNull final BungeePermissionUser user, @NotNull final PermissionUserProperty... properties) {
        final ParsedMap<PermissionUserProperty, Object> data = new ParsedMap<>();

        for (final PermissionUserProperty property : properties) {
            switch (property) {
                case UNIQUE_ID:
                    data.put(property, user.getUniqueId());
                    break;

                case NAME:
                    data.put(property, user.getName());
                    break;

                case FIRST_LOGIN:
                    data.put(property, user.getFirstLogin());
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
