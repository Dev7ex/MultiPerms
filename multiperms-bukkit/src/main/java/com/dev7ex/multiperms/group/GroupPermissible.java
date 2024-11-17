package com.dev7ex.multiperms.group;

import com.dev7ex.multiperms.api.bukkit.user.BukkitPermissionUser;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public class GroupPermissible extends PermissibleBase {

    private final Player player;
    private final BukkitPermissionUser user;

    public GroupPermissible(@NotNull final Player player, @NotNull final BukkitPermissionUser user) {
        super(player);
        this.player = player;
        this.user = user;
    }

    @Override
    public boolean hasPermission(@NotNull final String permission) {
        return (this.user.hasPermission(permission)) || (this.player.isOp());
    }

    @Override
    public boolean isPermissionSet(@NotNull final String permission) {
        return (this.user.hasPermission(permission)) || (this.player.isOp());
    }

}
