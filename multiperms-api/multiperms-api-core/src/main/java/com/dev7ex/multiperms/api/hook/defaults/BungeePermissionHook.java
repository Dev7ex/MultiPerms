package com.dev7ex.multiperms.api.hook.defaults;

import com.dev7ex.multiperms.api.hook.PermissionHook;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

/**
 * @author Dev7ex
 * @since 09.11.2024
 */
@Getter(AccessLevel.PUBLIC)
public class BungeePermissionHook implements PermissionHook {

    private final List<String> permissions = List.of(
            "bungeecord.command.alert",
            "bungeecord.command.end",
            "bungeecord.command.find",
            "bungeecord.command.list",
            "bungeecord.command.reload",
            "bungeecord.command.ip",
            "bungeecord.command.send",
            "bungeecord.command.server");

    @Override
    public void register() {}

    @Override
    public void unregister() {}

}
