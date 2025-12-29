package com.dev7ex.multiperms.api.user;

import com.dev7ex.multiperms.api.group.PermissionGroup;

import java.util.List;
import java.util.UUID;

public interface PermissionUser {

    UUID getUniqueId();

    String getName();

    long getFirstLogin();

    void setFirstLogin(final long firstLogin);

    long getLastLogin();

    void setLastLogin(final long lastLogin);

    String getColoredName();

    PermissionGroup getGroup();

    void setGroup(final PermissionGroup group);

    List<PermissionGroup> getSubGroups();

    void setSubGroups(final List<PermissionGroup> subGroups);

    List<String> getPermissions();

    void setPermissions(final List<String> permissions);

    void addPermission(final String permission);

    void removePermission(final String permission);

    boolean hasPermission(final String permission);

    void sendMessage(final String message);

}
