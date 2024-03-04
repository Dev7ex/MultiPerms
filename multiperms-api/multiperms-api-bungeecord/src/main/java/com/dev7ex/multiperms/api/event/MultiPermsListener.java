package com.dev7ex.multiperms.api.event;

import com.dev7ex.multiperms.api.MultiPermsApiConfiguration;
import com.dev7ex.multiperms.api.MultiPermsBungeeApi;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import com.dev7ex.multiperms.api.user.PermissionUserProvider;
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

    public String getPrefix() {
        return this.multiPermsApi.getConfiguration().getPrefix();
    }

    public PermissionGroupProvider getGroupProvider() {
        return this.multiPermsApi.getGroupProvider();
    }

    public PermissionUserProvider getUserProvider() {
        return this.multiPermsApi.getUserProvider();
    }


}
