package com.dev7ex.multiperms.group;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder(setterPrefix = "set")
public class Group implements PermissionGroup {

    private int identification;
    private String name;
    private String displayName;
    private char color;
    private int priority;
    private String tablistPrefix;
    private String chatPrefix;
    private List<String> permissions;

    @Override
    public String getColoredDisplayName() {
        return (ChatColor.getByChar(this.color) + this.displayName);
    }

    @Override
    public boolean hasPermission(@NotNull final String permission) {
        return (this.permissions.contains("*"))
                || (this.permissions.contains(permission));
    }

}
