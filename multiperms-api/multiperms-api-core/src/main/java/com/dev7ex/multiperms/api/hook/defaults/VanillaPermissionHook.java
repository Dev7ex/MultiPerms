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
public class VanillaPermissionHook implements PermissionHook {

    private final List<String> permissions = List.of(
            "minecraft.admin.command_feedback",
            "minecraft.commandblock",
            "minecraft.debugstick",
            "minecraft.debugstick.always",
            "minecraft.nbt.copy",
            "minecraft.nbt.place",
            "minecraft"
    );

    @Override
    public void register() {}

    @Override
    public void unregister() {}

}
