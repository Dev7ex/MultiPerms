package com.dev7ex.multiperms.api.hook.defaults;

import com.dev7ex.multiperms.api.hook.PermissionHook;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

/**
 * @author Dev7ex
 * @since 10.11.2024
 */
@Getter(AccessLevel.PUBLIC)
public class MultiPermsHook implements PermissionHook {

    private final List<String> permissions = List.of(
            "multiperms.command.permission",
            "multiperms.command.permission.group",
            "multiperms.command.permission.group.add",
            "multiperms.command.permission.group.create",
            "multiperms.command.permission.group.delete",
            "multiperms.command.permission.group.edit",
            "multiperms.command.permission.group.list",
            "multiperms.command.permission.group.remove",
            "multiperms.command.permission.reload",
            "multiperms.command.permission.user",
            "multiperms.command.permission.user.add",
            "multiperms.command.permission.user.clear",
            "multiperms.command.permission.user.info",
            "multiperms.command.permission.user.remove",
            "multiperms.command.permission.user.set",
            "multiperms.command.permission.version"
    );

    @Override
    public void register() {}

    @Override
    public void unregister() {}

}
