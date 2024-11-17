package com.dev7ex.multiperms.api.bungeecord.event;

import com.dev7ex.multiperms.api.MultiPermsApiConfiguration;
import com.dev7ex.multiperms.api.bungeecord.MultiPermsBungeeApi;
import com.dev7ex.multiperms.api.bungeecord.hook.BungeePermissionHookProvider;
import com.dev7ex.multiperms.api.bungeecord.translation.BungeeTranslationProvider;
import com.dev7ex.multiperms.api.bungeecord.user.BungeePermissionUserProvider;
import com.dev7ex.multiperms.api.group.PermissionGroupConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import net.md_5.bungee.api.plugin.Listener;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
public class MultiPermsListener implements Listener {

    private final MultiPermsBungeeApi multiPermsApi;

    public MultiPermsListener(@NotNull final MultiPermsBungeeApi multiPermsApi) {
        this.multiPermsApi = multiPermsApi;
    }

    public MultiPermsApiConfiguration getConfiguration() {
        return this.multiPermsApi.getConfiguration();
    }

    public PermissionGroupConfiguration getGroupConfiguration() {
        return this.multiPermsApi.getGroupConfiguration();
    }

    public PermissionGroupProvider getGroupProvider() {
        return this.multiPermsApi.getGroupProvider();
    }

    public BungeePermissionHookProvider getPermissionHookProvider() {
        return this.multiPermsApi.getPermissionHookProvider();
    }

    public BungeeTranslationProvider getTranslationProvider() {
        return this.multiPermsApi.getTranslationProvider();
    }

    public BungeePermissionUserProvider getUserProvider() {
        return this.multiPermsApi.getUserProvider();
    }


}
