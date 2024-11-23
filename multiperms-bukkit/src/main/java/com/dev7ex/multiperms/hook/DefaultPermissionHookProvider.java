package com.dev7ex.multiperms.hook;

import com.dev7ex.common.bukkit.plugin.module.PluginModule;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bukkit.hook.BukkitPermissionHookProvider;
import com.dev7ex.multiperms.api.hook.PermissionHook;
import com.dev7ex.multiperms.api.hook.defaults.*;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Dev7ex
 * @since 03.08.2023
 */
@Getter(AccessLevel.PUBLIC)
public class DefaultPermissionHookProvider implements PluginModule, BukkitPermissionHookProvider {

    private final Map<Plugin, List<PermissionHook>> permissionHooks = new HashMap<>();

    @Override
    public void onEnable() {
        final MultiPermsPlugin hookHolder = MultiPermsPlugin.getInstance();
        final List<PermissionHook> multiPermsHooks = List.of(new BukkitPermissionHook(),
                new MinecraftPermissionHook(),
                new MinecraftPermissionHook(),
                new MultiPermsHook(),
                new PaperPermissionHook(),
                new VanillaPermissionHook());

        this.permissionHooks.put(hookHolder, multiPermsHooks);

        MultiPermsPlugin.getInstance().getLogger().info("Found: [" + this.permissionHooks.values().stream()
                .mapToLong(List::size)
                .sum() + "] PermissionHooks");
    }

    @Override
    public void onDisable() {
        this.permissionHooks.clear();
    }

    @Override
    public void register(@NotNull final Plugin hookHolder, @NotNull final PermissionHook hook) {
        this.permissionHooks.computeIfAbsent(hookHolder, plugin -> new ArrayList<>());
        final List<PermissionHook> hooks = this.permissionHooks.get(hookHolder);
        hooks.add(hook);

        this.permissionHooks.put(hookHolder, hooks);

        MultiPermsPlugin.getInstance()
                .getLogger()
                .info("Register [" + this.permissionHooks.get(hookHolder).size() + "] Permission Hooks from "
                        + hookHolder.getName());
    }

    @Override
    public void unregister(@NotNull final Plugin hookHolder) {
        final List<PermissionHook> hooks = this.permissionHooks.get(hookHolder);

        if ((hooks == null) || (hooks.isEmpty())) {
            return;
        }
        this.permissionHooks.remove(hookHolder);

        MultiPermsPlugin.getInstance()
                .getLogger()
                .info("Unregister [" + this.permissionHooks.get(hookHolder).size() + "] Permission Hooks from "
                        + hookHolder.getName());
    }

    @Override
    public boolean isRegistered(@NotNull final Plugin hookHolder) {
        return this.permissionHooks.containsKey(hookHolder);
    }

    @Override
    public List<PermissionHook> getHooks(@NotNull final Plugin hookHolder) {
        return this.permissionHooks.get(hookHolder);
    }

    @Override
    public List<String> getRegisteredPermissions() {
        return permissionHooks.values().stream()
                .flatMap(Collection::stream)
                .flatMap(permissionHook -> permissionHook.getPermissions().stream())
                .collect(Collectors.toList());
    }

}
