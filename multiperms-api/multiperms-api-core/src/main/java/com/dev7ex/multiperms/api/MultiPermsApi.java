package com.dev7ex.multiperms.api;

import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import com.dev7ex.multiperms.api.hook.PermissionHookProvider;
import com.dev7ex.multiperms.api.translation.TranslationProvider;
import com.dev7ex.multiperms.api.user.PermissionUserProvider;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * Provides an API for managing permissions, groups, and users within the MultiPerms system.
 *
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface MultiPermsApi {

    /**
     * Retrieves the provider responsible for managing permission groups.
     *
     * @return A non-null {@link PermissionGroupProvider} instance.
     */
    PermissionGroupProvider getGroupProvider();

    /**
     * Retrieves the provider responsible for handling permission hooks.
     *
     * @return A non-null {@link PermissionHookProvider} instance, parameterized with the appropriate type.
     */
   PermissionHookProvider<?> getPermissionHookProvider();

    /**
     * Retrieves the current translation provider.
     * <p>
     * This method returns an instance of {@code TranslationProvider<?>} that is responsible for
     * handling translations across different locales or languages. The provider may return
     * translations based on the current system locale or another specified locale.
     * </p>
     *
     * @return a {@code TranslationProvider<?>} instance that can provide localized text or messages.
     */
   TranslationProvider<?> getTranslationProvider();

    /**
     * Retrieves the provider responsible for managing permission users.
     *
     * @return A non-null {@link PermissionUserProvider} instance.
     */
    PermissionUserProvider getUserProvider();

    /**
     * Retrieves the current configuration of the MultiPerms API.
     *
     * @return A non-null {@link MultiPermsApiConfiguration} instance.
     */
   MultiPermsApiConfiguration getConfiguration();

    /**
     * Retrieves the folder where user-related data is stored.
     *
     * @return A {@link File} instance representing the user folder, or null if it is not available.
     */
    @Nullable File getUserFolder();
    
}

