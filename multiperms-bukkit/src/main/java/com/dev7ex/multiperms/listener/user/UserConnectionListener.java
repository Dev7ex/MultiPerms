package com.dev7ex.multiperms.listener.user;

import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApi;
import com.dev7ex.multiperms.api.bukkit.event.MultiPermsListener;
import com.dev7ex.multiperms.api.bukkit.event.user.PermissionUserLoginEvent;
import com.dev7ex.multiperms.api.bukkit.user.BukkitPermissionUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 09.11.2024
 */
public class UserConnectionListener extends MultiPermsListener {

    /**
     * Constructs a new listener with access to the MultiPerms API.
     *
     * @param multiPermsApi The {@link MultiPermsBukkitApi} instance to interact with the MultiPerms system.
     */
    public UserConnectionListener(@NotNull final MultiPermsBukkitApi multiPermsApi) {
        super(multiPermsApi);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleUserLogin(final PermissionUserLoginEvent event) {
        final BukkitPermissionUser user = event.getUser();
        final Player player = user.getEntity();

        super.getScoreboardProvider().updateNameTagsDelayed(player, user, 5L);
    }

}
