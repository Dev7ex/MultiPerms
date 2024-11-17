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
public class PaperPermissionHook implements PermissionHook {

    private final List<String> permissions = List.of(
            "bukkit.command.paper",
            "bukkit.command.paper.debug",
            "bukkit.command.paper.dumpitem",
            "bukkit.command.paper.dumplisteners",
            "bukkit.command.paper.dumpplugins",
            "bukkit.command.paper.entity",
            "bukkit.command.paper.fixlight",
            "bukkit.command.paper.mobcaps",
            "bukkit.command.paper.reload",
            "bukkit.command.paper.syncloadinfo",
            "bukkit.command.paper.version",
            "bukkit.command.paper.heap"
    );

    @Override
    public void register() {}

    @Override
    public void unregister() {}

}
