package com.dev7ex.multiperms.api.hook;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * Provides methods for managing permission hooks, including registration and retrieval.
 *
 * @param <T> The type of the hook holder.
 * @author Dev7ex
 * @since 03.08.2023
 */
public interface PermissionHookProvider<T> {

    /**
     * Gets all the permission hooks mapped by their hook holders.
     *
     * @return A map of hook holders to their associated {@link PermissionHook}.
     */
    @NotNull Map<T, List<PermissionHook>> getPermissionHooks();

    /**
     * Registers a permission hook for a specific hook holder.
     *
     * @param hookHolder The object holding the hook.
     * @param hook The {@link PermissionHook} to register.
     */
    void register(@NotNull final T hookHolder, @NotNull final PermissionHook hook);

    /**
     * Unregisters the permission hook for a specific hook holder.
     *
     * @param hookHolder The object holding the hook to unregister.
     */
    void unregister(@NotNull final T hookHolder);

    /**
     * Checks if the given hookHolder has any hooks registered.
     *
     * @param hookHolder The hook holder whose registration status is being checked.
     * @return {@code true} if the hook holder has at least one hook registered, otherwise {@code false}.
     */
    boolean isRegistered(@NotNull final T hookHolder);

    /**
     * Retrieves the list of hooks registered for the given hookHolder.
     *
     * @param hookHolder The hook holder whose hooks are being retrieved.
     * @return A list of hooks registered for the given hook holder, or an empty list if none are registered.
     */
    List<PermissionHook> getHooks(@NotNull final T hookHolder);

    /**
     * Retrieves a list of all the permissions that are currently registered.
     *
     * @return A list of registered permissions.
     */
    List<String> getRegisteredPermissions();

}

