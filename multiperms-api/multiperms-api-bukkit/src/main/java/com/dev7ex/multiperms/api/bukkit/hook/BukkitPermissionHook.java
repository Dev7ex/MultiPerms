package com.dev7ex.multiperms.api.bukkit.hook;

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

    private final List<String> permissions = List.of("bukkit.command.version", "bukkit.command.plugins", "bukkit.command.help", "bukkit.command.reload", "bukkit.command.timings");

}
