package com.dev7ex.multiperms.api.velocity.user;

import com.dev7ex.multiperms.api.user.PermissionUser;
import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 09.11.2024
 */
public interface VelocityPermissionUser extends PermissionUser {

    @NotNull
    Player getEntity();

}
