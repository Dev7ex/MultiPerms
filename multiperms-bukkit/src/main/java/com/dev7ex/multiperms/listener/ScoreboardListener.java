package com.dev7ex.multiperms.listener;

import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApi;
import com.dev7ex.multiperms.api.bukkit.event.MultiPermsListener;
import com.dev7ex.multiperms.api.bukkit.event.group.PermissionGroupDeleteEvent;
import com.dev7ex.multiperms.api.bukkit.event.group.PermissionGroupEditEvent;
import com.dev7ex.multiperms.api.bukkit.event.user.PermissionUserLoginEvent;
import com.dev7ex.multiperms.api.user.PermissionUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Dev7ex
 * @since 13.08.2023
 */
public class ScoreboardListener extends MultiPermsListener {

    public ScoreboardListener(@NotNull final MultiPermsBukkitApi multiPermsApi) {
        super(multiPermsApi);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleUserLogin(final PermissionUserLoginEvent event) {
        final PermissionUser user = event.getUser();
        final Player player = Objects.requireNonNull(Bukkit.getPlayer(user.getUniqueId()));

        MultiPermsPlugin.getInstance().getScoreboardProvider().updateNameTagsDelayed(player, user, 5L);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleGroupDelete(final PermissionGroupDeleteEvent event) {
        final Collection<PermissionUser> users = super.getUserProvider().getUsers(event.getGroup()).values();
        if (users.isEmpty()) {
            return;
        }

        for (final PermissionUser user : users) {
            user.setGroup(super.getGroupProvider().getNextBestGroup(user.getSubGroups()));
            MultiPermsPlugin.getInstance().getScoreboardProvider().updateNameTagsDelayed(Objects.requireNonNull(Bukkit.getPlayer(user.getUniqueId())), user, 5L);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleGroupEdit(final PermissionGroupEditEvent event) {
        for (final PermissionUser user : super.getUserProvider().getUsers().values()) {
            MultiPermsPlugin.getInstance().getScoreboardProvider().updateNameTagsDelayed(Objects.requireNonNull(Bukkit.getPlayer(user.getUniqueId())), user, 5L);
        }
    }

}
