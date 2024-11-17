package com.dev7ex.multiperms.listener.player;

import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApi;
import com.dev7ex.multiperms.api.bukkit.event.MultiPermsListener;
import com.dev7ex.multiperms.api.bukkit.user.BukkitPermissionUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public class PlayerChatListener extends MultiPermsListener {

    public PlayerChatListener(@NotNull final MultiPermsBukkitApi multiPermsApi) {
        super(multiPermsApi);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handlePlayerChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();

        if (super.getUserProvider().getUser(player.getUniqueId()).isEmpty()) {
            return;
        }
        final BukkitPermissionUser user = super.getUserProvider()
                .getUser(player.getUniqueId())
                .get();

        event.setFormat(super.getConfiguration().getChatFormat()
                .replaceAll("%prefix%",user.getGroup().getChatPrefix().replaceAll("&", "§"))
                .replaceAll("%name%", player.getName())
                .replaceAll("%message%", event.getMessage()));

        if (player.hasPermission("multiperms.chat.colored")) {
            event.setFormat(event.getFormat().replaceAll("&", "§"));
        }
    }

}
