package com.dev7ex.multiperms.listener;

import com.dev7ex.multiperms.api.MultiPermsBungeeApi;
import com.dev7ex.multiperms.api.event.MultiPermsListener;
import com.dev7ex.multiperms.api.user.PermissionUser;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
public class PermissionCheckListener extends MultiPermsListener {

    public PermissionCheckListener(@NotNull final MultiPermsBungeeApi multiPermsApi) {
        super(multiPermsApi);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handlePermissionCheck(final PermissionCheckEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer)) {
            return;
        }
        final ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (super.getUserProvider().getUser(player.getUniqueId()).isEmpty()) {
            return;
        }
        final PermissionUser user = super.getUserProvider().getUser(player.getUniqueId()).get();

        event.setHasPermission(user.hasPermission(event.getPermission()));
    }

}
