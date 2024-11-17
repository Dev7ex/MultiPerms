package com.dev7ex.multiperms.api.hook.defaults;

import com.dev7ex.multiperms.api.hook.PermissionHook;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

/**
 * @author Dev7ex
 * @since 03.08.2023
 */
@Getter(AccessLevel.PUBLIC)
public class BukkitPermissionHook implements PermissionHook {

    private final List<String> permissions = List.of(
            "bukkit.broadcast",
            "bukkit.broadcast.admin",
            "bukkit.broadcast.user",
            "bukkit.command.help",
            "bukkit.command.plugins",
            "bukkit.command.reload",
            "bukkit.command.timings",
            "bukkit.command.version"
    );

    @Override
    public void register() {

    }

    @Override
    public void unregister() {

    }

}
