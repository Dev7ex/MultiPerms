package com.dev7ex.multiperms.api.bukkit.event;

import com.dev7ex.multiperms.api.MultiPermsApiConfiguration;
import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApi;
import com.dev7ex.multiperms.api.bukkit.translation.BukkitTranslationProvider;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import com.dev7ex.multiperms.api.user.PermissionUserProvider;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public abstract class MultiPermsListener implements Listener {

    private final MultiPermsBukkitApi multiPermsApi;

    public MultiPermsListener(@NotNull final MultiPermsBukkitApi multiPermsApi) {
        this.multiPermsApi = multiPermsApi;
    }

    public MultiPermsApiConfiguration getConfiguration() {
        return this.multiPermsApi.getConfiguration();
    }

    public PermissionGroupProvider getGroupProvider() {
        return this.multiPermsApi.getGroupProvider();
    }

    public BukkitTranslationProvider getTranslationProvider() {
        return this.multiPermsApi.getTranslationProvider();
    }

    public PermissionUserProvider getUserProvider() {
        return this.multiPermsApi.getUserProvider();
    }

}
