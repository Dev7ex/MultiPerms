package com.dev7ex.multiperms.api.group;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface PermissionGroup extends Comparable<PermissionGroup> {

    int getIdentification();

    void setIdentification(final int identification);

    String getName();

    void setName(@NotNull final String name);

    List<String> getPermissions();

    boolean hasPermission(@NotNull final String permission);

    String getDisplayName();

    void setDisplayName(@NotNull final String displayName);

    int getPriority();

    void setPriority(final int priority);

    char getColor();

    void setColor(final char color);

    String getChatPrefix();

    void setChatPrefix(@NotNull final String chatPrefix);

    String getTablistPrefix();

    void setTablistPrefix(@NotNull final String tablistPrefix);

    String getColoredDisplayName();

    @Override
    default int compareTo(@NotNull final PermissionGroup group) {
        return Integer.compare(this.getPriority(), group.getPriority());
    }

}
