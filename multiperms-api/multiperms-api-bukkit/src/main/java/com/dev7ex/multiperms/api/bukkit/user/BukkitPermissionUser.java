package com.dev7ex.multiperms.api.bukkit.user;

import com.dev7ex.multiperms.api.user.PermissionUser;
import org.bukkit.entity.Player;

/**
 * @author Dev7ex
 * @since 09.11.2024
 */
public interface BukkitPermissionUser extends PermissionUser {

    Player getEntity();

}
