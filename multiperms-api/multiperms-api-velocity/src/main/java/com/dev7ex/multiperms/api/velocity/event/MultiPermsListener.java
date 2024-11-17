package com.dev7ex.multiperms.api.velocity.event;

import com.dev7ex.multiperms.api.group.PermissionGroupConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import com.dev7ex.multiperms.api.velocity.MultiPermsVelocityApi;
import com.dev7ex.multiperms.api.velocity.MultiPermsVelocityApiConfiguration;
import com.dev7ex.multiperms.api.velocity.hook.VelocityPermissionHookProvider;
import com.dev7ex.multiperms.api.velocity.translation.VelocityTranslationProvider;
import com.dev7ex.multiperms.api.velocity.user.VelocityPermissionUserProvider;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
public class MultiPermsListener {

    private final MultiPermsVelocityApi multiPermsApi;

    public MultiPermsListener(@NotNull final MultiPermsVelocityApi multiPermsApi) {
        this.multiPermsApi = multiPermsApi;
    }

    public MultiPermsVelocityApiConfiguration getConfiguration() {
        return this.multiPermsApi.getConfiguration();
    }

    public PermissionGroupConfiguration getGroupConfiguration() {
        return this.multiPermsApi.getGroupConfiguration();
    }

    public PermissionGroupProvider getGroupProvider() {
        return this.multiPermsApi.getGroupProvider();
    }

    public VelocityPermissionHookProvider getPermissionHookProvider() {
        return this.multiPermsApi.getPermissionHookProvider();
    }

    public VelocityTranslationProvider getTranslationProvider() {
        return this.multiPermsApi.getTranslationProvider();
    }

    public VelocityPermissionUserProvider getUserProvider() {
        return this.multiPermsApi.getUserProvider();
    }

}
