package com.dev7ex.multiperms.api;

import com.dev7ex.multiperms.api.group.PermissionGroupConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import com.dev7ex.multiperms.api.hook.PermissionHookProvider;
import com.dev7ex.multiperms.api.translation.TranslationProvider;
import com.dev7ex.multiperms.api.user.PermissionUserProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * API for managing permissions, groups, users, translations, and hooks within the MultiPerms plugin.
 * Provides access to various providers for permission management and user data handling.
 *
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface MultiPermsApi {

    /**
     * Gets the current configuration of the MultiPerms API.
     *
     * @return the {@link MultiPermsApiConfiguration} instance.
     */
    @NotNull MultiPermsApiConfiguration getConfiguration();

    /**
     * Gets the provider for managing permission groups.
     *
     * @return the {@link PermissionGroupProvider} instance.
     */
    @NotNull PermissionGroupProvider getGroupProvider();

    /**
     * Gets the configuration for managing permission groups.
     *
     * @return the {@link PermissionGroupConfiguration} instance.
     */
    @NotNull PermissionGroupConfiguration getGroupConfiguration();

    /**
     * Gets the provider for handling permission hooks.
     *
     * @return the {@link PermissionHookProvider} instance.
     */
    @NotNull PermissionHookProvider<?> getPermissionHookProvider();

    /**
     * Gets the translation provider for managing language localization.
     *
     * @return the {@link TranslationProvider} instance.
     */
    @NotNull TranslationProvider<?> getTranslationProvider();

    /**
     * Gets the provider for managing permission users.
     *
     * @return the {@link PermissionUserProvider} instance.
     */
    @NotNull PermissionUserProvider<?> getUserProvider();

    /**
     * Gets the folder where user data is stored.
     *
     * @return the {@link File} instance representing the user folder, or null if unavailable.
     */
    @Nullable File getUserFolder();

}


