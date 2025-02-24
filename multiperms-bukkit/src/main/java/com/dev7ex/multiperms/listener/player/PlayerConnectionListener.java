package com.dev7ex.multiperms.listener.player;

import com.dev7ex.common.collect.map.ParsedMap;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApi;
import com.dev7ex.multiperms.api.bukkit.event.MultiPermsListener;
import com.dev7ex.multiperms.api.bukkit.event.user.PermissionUserLoginEvent;
import com.dev7ex.multiperms.api.bukkit.event.user.PermissionUserLogoutEvent;
import com.dev7ex.multiperms.api.bukkit.user.BukkitPermissionUser;
import com.dev7ex.multiperms.api.bukkit.user.BukkitPermissionUserConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUserProperty;
import com.dev7ex.multiperms.user.User;
import com.dev7ex.multiperms.user.UserConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public class PlayerConnectionListener extends MultiPermsListener {

    public PlayerConnectionListener(@NotNull final MultiPermsBukkitApi multiPermsApi) {
        super(multiPermsApi);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handlePlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final BukkitPermissionUser user = new User(player.getUniqueId(), player.getName());
        final BukkitPermissionUserConfiguration userConfiguration = new UserConfiguration(user);
        final ParsedMap<PermissionUserProperty, Object> userData = userConfiguration.read();

        final List<PermissionGroup> subGroups = super.getGroupProvider().getExistingGroups(userData.getIntList(PermissionUserProperty.SUB_GROUPS)); // Get SubGroups from config
        final PermissionGroup userGroup = super.getGroupProvider().getGroupOrDefault(userData.getInteger(PermissionUserProperty.GROUP));

        subGroups.removeIf(group -> group.getIdentification() == userGroup.getIdentification());

        user.setConfiguration(userConfiguration);
        user.setGroup(userGroup);
        user.setPermissions(userData.getStringList(PermissionUserProperty.PERMISSION));
        user.setSubGroups(subGroups);
        user.setLastLogin(System.currentTimeMillis());
        user.setFirstLogin(userData.getLong(PermissionUserProperty.FIRST_LOGIN));

        if (user.getFirstLogin() == 0L) {
            user.setFirstLogin(System.currentTimeMillis());
        }
        MultiPermsPlugin.getInstance().getGroupProvider().invokePermissions(player, user);

        super.getUserProvider().register(user);
        super.getUserProvider().saveUser(user, PermissionUserProperty.FIRST_LOGIN, PermissionUserProperty.LAST_LOGIN,
                PermissionUserProperty.GROUP, PermissionUserProperty.SUB_GROUPS);

        Bukkit.getPluginManager().callEvent(new PermissionUserLoginEvent(user));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handlePlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final BukkitPermissionUser user = super.getUserProvider()
                .getUser(player.getUniqueId())
                .orElseThrow();

        Bukkit.getPluginManager().callEvent(new PermissionUserLogoutEvent(user));

        super.getUserProvider().saveUser(user);
        super.getUserProvider().unregister(player.getUniqueId());
    }

}
