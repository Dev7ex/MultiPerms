package com.dev7ex.multiperms.api;

import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import com.dev7ex.multiperms.api.hook.PermissionHookProvider;
import com.dev7ex.multiperms.api.user.PermissionUserProvider;

import java.io.File;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface MultiPermsApi {

    MultiPermsApiConfiguration getConfiguration();

    File getUserFolder();

    PermissionGroupProvider getGroupProvider();

    PermissionUserProvider getUserProvider();

    PermissionHookProvider getPermissionHookProvider();

}
