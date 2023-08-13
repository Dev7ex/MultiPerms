package com.dev7ex.multiperms.hook;

import com.dev7ex.common.bukkit.BukkitCommonPlugin;
import com.dev7ex.common.bukkit.plugin.service.PluginService;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bukkit.hook.BukkitPermissionHook;
import com.dev7ex.multiperms.api.hook.PermissionHook;
import com.dev7ex.multiperms.api.hook.PermissionHookProvider;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dev7ex
 * @since 03.08.2023
 */
@Getter(AccessLevel.PUBLIC)
public class DefaultPermissionHookProvider implements PluginService, PermissionHookProvider<Plugin> {

    private final Map<Plugin, PermissionHook> permissionHooks = new HashMap<>();

    @Override
    public void onEnable() {
        this.register(MultiPermsPlugin.getInstance(), new BukkitPermissionHook());
        this.register(JavaPlugin.getPlugin(BukkitCommonPlugin.class), new MultiPermsHook());
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
