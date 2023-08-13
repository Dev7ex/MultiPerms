package com.dev7ex.multiperms.listener;

import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApi;
import com.dev7ex.multiperms.api.bukkit.event.MultiPermsListener;
import com.dev7ex.multiperms.api.user.PermissionUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

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
        final Optional<PermissionUser> optionalUser = super.getUserProvider().getUser(player.getUniqueId());

        if (optionalUser.isEmpty()) {
            return;
        }
        event.setFormat(super.getConfiguration().getChatFormat()
                .replaceAll("%prefix%", optionalUser.get().getGroup().getChatPrefix().replaceAll("&", "§"))
                .replaceAll("%name%", player.getName())
                .replaceAll("%message%", event.getMessage()));

        if (player.hasPermission("multiperms.chat.colored")) {
            event.setFormat(event.getFormat().replaceAll("&", "§"));
        }
    }

}
