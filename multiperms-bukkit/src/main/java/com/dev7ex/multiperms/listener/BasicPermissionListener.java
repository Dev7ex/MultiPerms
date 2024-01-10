package com.dev7ex.multiperms.listener;

import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApi;
import com.dev7ex.multiperms.api.bukkit.event.MultiPermsListener;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 10.01.2024
 */
public class BasicPermissionListener extends MultiPermsListener {

    public BasicPermissionListener(@NotNull final MultiPermsBukkitApi multiPermsApi) {
        super(multiPermsApi);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handlePlayerInteract(final PlayerInteractEvent event) {
        if (event.getPlayer().hasPermission("multiperms.basic.interact")) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handlePlayerDamageByPlayer(final EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() != EntityType.PLAYER) {
            return;
        }
        final Player damager = (Player) event.getDamager();

        if (event.getEntity().getType() != EntityType.PLAYER) {
            return;
        }

        if (damager.hasPermission("multiperms.basic.pvp")) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handlePlayerPlaceBlock(final BlockPlaceEvent event) {
        if (event.getPlayer().hasPermission("multiperms.basic.place")) {
            return;
        }
        event.setBuild(false);
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handlePlayerBreakBlock(final BlockBreakEvent event) {
        if (event.getPlayer().hasPermission("multiperms.basic.break")) {
            return;
        }
        event.setCancelled(true);
    }

}
