package com.dev7ex.multiperms.api.hook;

import java.util.Map;

/**
 * @author Dev7ex
 * @since 03.08.2023
 */
public interface PermissionHookProvider<T> {

    Map<T, PermissionHook> getPermissionHooks();

    void register(final T hookHolder, final PermissionHook hook);

    void unregister(final T hookHolder);

}
