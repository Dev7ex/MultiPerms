package com.dev7ex.multiperms.api.hook;

import java.util.List;

/**
 * Interface for managing permission hooks in the MultiPerms plugin.
 * Provides methods to retrieve permissions and register/unregister hooks.
 *
 * @author Dev7ex
 * @since 03.08.2023
 */
public interface PermissionHook {

    /**
     * Gets the list of permissions associated with this hook.
     *
     * @return A list of permissions as strings.
     */
    List<String> getPermissions();

    /**
     * Registers the permission hook.
     */
    void register();

    /**
     * Unregisters the permission hook.
     */
    void unregister();

}

