package com.dev7ex.multiperms.listener.player;

import com.dev7ex.common.collect.map.ParsedMap;
import com.dev7ex.multiperms.api.bungeecord.MultiPermsBungeeApi;
import com.dev7ex.multiperms.api.bungeecord.event.MultiPermsListener;
import com.dev7ex.multiperms.api.bungeecord.event.user.PermissionUserLoginEvent;
import com.dev7ex.multiperms.api.bungeecord.event.user.PermissionUserLogoutEvent;
import com.dev7ex.multiperms.api.bungeecord.user.BungeePermissionUser;
import com.dev7ex.multiperms.api.bungeecord.user.BungeePermissionUserConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUserProperty;
import com.dev7ex.multiperms.user.User;
import com.dev7ex.multiperms.user.UserConfiguration;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
public class PlayerConnectionListener extends MultiPermsListener {

    public PlayerConnectionListener(@NotNull final MultiPermsBungeeApi multiPermsApi) {
        super(multiPermsApi);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handlePlayerJoin(final PostLoginEvent event) {
        final ProxiedPlayer player = event.getPlayer();
        final BungeePermissionUser user = new User(player.getUniqueId(), player.getName());
        final BungeePermissionUserConfiguration userConfiguration = new UserConfiguration(user);
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

        System.out.println(user.getFirstLogin());
        if (user.getFirstLogin() == 0L) {
            user.setFirstLogin(System.currentTimeMillis());
        }
        System.out.println("2   " + user.getFirstLogin());

        super.getUserProvider().register(user);
        super.getUserProvider().saveUser(user, PermissionUserProperty.FIRST_LOGIN, PermissionUserProperty.LAST_LOGIN,
                PermissionUserProperty.GROUP, PermissionUserProperty.SUB_GROUPS);

        ProxyServer.getInstance().getPluginManager().callEvent(new PermissionUserLoginEvent(user));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handlePlayerJoin(final PlayerDisconnectEvent event) {
        final ProxiedPlayer player = event.getPlayer();
        final BungeePermissionUser user = super.getUserProvider()
                .getUser(player.getUniqueId())
                .orElseThrow();

        super.getUserProvider().saveUser(user);
        super.getUserProvider().unregister(player.getUniqueId());

        ProxyServer.getInstance().getPluginManager().callEvent(new PermissionUserLogoutEvent(user));
    }

}
