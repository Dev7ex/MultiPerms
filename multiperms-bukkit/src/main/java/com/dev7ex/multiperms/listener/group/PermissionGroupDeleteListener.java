package com.dev7ex.multiperms.listener.group;

import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApi;
import com.dev7ex.multiperms.api.bukkit.event.MultiPermsListener;
import com.dev7ex.multiperms.api.bukkit.event.group.PermissionGroupDeleteEvent;
import com.dev7ex.multiperms.api.bukkit.user.BukkitPermissionUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Dev7ex
 * @since 09.11.2024
 */
public class PermissionGroupDeleteListener extends MultiPermsListener {

    /**
     * Constructs a new listener with access to the MultiPerms API.
     *
     * @param multiPermsApi The {@link MultiPermsBukkitApi} instance to interact with the MultiPerms system.
     */
    public PermissionGroupDeleteListener(@NotNull final MultiPermsBukkitApi multiPermsApi) {
        super(multiPermsApi);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleGroupDelete(final PermissionGroupDeleteEvent event) {
        final Collection<BukkitPermissionUser> users = super.getUserProvider()
                .getUsers(event.getGroup())
                .values();

        if (users.isEmpty()) {
            return;
        }

        users.forEach(permissionUser -> {
            permissionUser.setGroup(super.getGroupProvider().getNextBestGroup(permissionUser.getSubGroups()));
            super.getScoreboardProvider().updateNameTagsDelayed(permissionUser.getEntity(), permissionUser, 5L);
        });
    }

}
