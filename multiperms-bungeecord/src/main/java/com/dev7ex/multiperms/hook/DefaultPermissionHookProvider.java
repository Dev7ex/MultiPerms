package com.dev7ex.multiperms.hook;

import com.dev7ex.common.bungeecord.plugin.module.PluginModule;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.hook.PermissionHook;
import com.dev7ex.multiperms.api.hook.PermissionHookProvider;
import lombok.AccessLevel;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter(AccessLevel.PUBLIC)
public class DefaultPermissionHookProvider implements PluginModule, PermissionHookProvider<Plugin> {

    private final Map<Plugin, PermissionHook> permissionHooks = new HashMap<>();

    @Override
    public void onEnable() {
        this.register(MultiPermsPlugin.getInstance(), new MultiPermsHook());
    }

    @Override
    public void onDisable() {
        this.permissionHooks.clear();
    }

    @Override
    public void register(@NotNull final Plugin hookHolder, @NotNull final PermissionHook hook) {
        MultiPermsPlugin.getInstance().getLogger().info("Register Hook " + hook.getClass().getSimpleName());
        this.permissionHooks.put(hookHolder, hook);
    }

    @Override
    public void unregister(@NotNull final Plugin hookHolder) {
        this.permissionHooks.remove(hookHolder);
    }

    public List<String> getAllPermissions() {
        final List<String> permissions = new ArrayList<>();
        this.permissionHooks.values().forEach(permissionHook -> permissions.addAll(permissionHook.getPermissions()));
        return permissions;
    }

}
