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
public class VelocityPermissionHook implements PermissionHook {

    private final List<String> permissions = List.of(
            "velocity.command.dump",
            "velocity.command.glist",
            "velocity.command.heap",
            "velocity.command.info",
            "velocity.command.plugins",
            "velocity.command.reload",
            "velocity.command.send",
            "velocity.command.server"
    );

    @Override
    public void register() {}

    @Override
    public void unregister() {}

}
