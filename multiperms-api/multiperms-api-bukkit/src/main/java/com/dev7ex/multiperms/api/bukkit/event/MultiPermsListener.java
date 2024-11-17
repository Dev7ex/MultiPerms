package com.dev7ex.multiperms.api.bukkit.event;

import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApi;
import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApiConfiguration;
import com.dev7ex.multiperms.api.bukkit.hook.BukkitPermissionHookProvider;
import com.dev7ex.multiperms.api.bukkit.scoreboard.BukkitScoreboardProvider;
import com.dev7ex.multiperms.api.bukkit.translation.BukkitTranslationProvider;
import com.dev7ex.multiperms.api.bukkit.user.BukkitPermissionUserProvider;
import com.dev7ex.multiperms.api.group.PermissionGroupConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

/**
 * An abstract listener class for integrating with the MultiPerms system in Bukkit.
 * This class provides access to the core MultiPerms API components like configuration,
 * group management, translations, and user management.
 *
 * @author Dev7ex
 * @since 03.07.2023
 */
public abstract class MultiPermsListener implements Listener {

    private final MultiPermsBukkitApi multiPermsApi;

    /**
     * Constructs a new listener with access to the MultiPerms API.
     *
     * @param multiPermsApi The {@link MultiPermsBukkitApi} instance to interact with the MultiPerms system.
     */
    public MultiPermsListener(@NotNull final MultiPermsBukkitApi multiPermsApi) {
        this.multiPermsApi = multiPermsApi;
    }

    public MultiPermsBukkitApiConfiguration getConfiguration() {
        return this.multiPermsApi.getConfiguration();
    }

    public PermissionGroupConfiguration getGroupConfiguration() {
        return this.multiPermsApi.getGroupConfiguration();
    }

    public PermissionGroupProvider getGroupProvider() {
        return this.multiPermsApi.getGroupProvider();
    }

    public BukkitPermissionHookProvider getPermissionHookProvider() {
        return this.multiPermsApi.getPermissionHookProvider();
    }

    public BukkitScoreboardProvider getScoreboardProvider() {
        return this.multiPermsApi.getScoreboardProvider();
    }

    public BukkitTranslationProvider getTranslationProvider() {
        return this.multiPermsApi.getTranslationProvider();
    }

    public BukkitPermissionUserProvider getUserProvider() {
        return this.multiPermsApi.getUserProvider();
    }

}

