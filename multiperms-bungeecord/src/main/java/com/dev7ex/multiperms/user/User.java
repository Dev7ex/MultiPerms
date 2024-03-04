package com.dev7ex.multiperms.user;

import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUser;
import com.dev7ex.multiperms.api.user.PermissionUserConfiguration;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class User implements PermissionUser {

    private UUID uniqueId;
    private String name;
    private PermissionUserConfiguration configuration;
    private long lastLogin;
    private PermissionGroup group;
    private List<PermissionGroup> subGroups = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();

    public User(@NotNull final UUID uniqueId, @NotNull final String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    @Override
    public String getColoredName() {
        return (ChatColor.getByChar(this.group.getColor()) + this.name);
    }

    @Override
    public List<String> getAllPermissions() {
        final List<String> permissions = Lists.newArrayList(this.permissions);
        permissions.addAll(this.group.getPermissions());
        if (!this.subGroups.isEmpty()) {
            this.subGroups.forEach(permissionGroup -> permissions.addAll(permissionGroup.getPermissions()));
        }
        return permissions;
    }

    @Override
    public void addPermission(@NotNull final String permission) {
        this.permissions.add(permission);
    }

    @Override
    public void removePermission(@NotNull final String permission) {
        this.permissions.remove(permission);
    }

    @Override
    public boolean hasPermission(@NotNull final String permission) {
        return ((this.permissions.contains("*")) || (this.permissions.contains(permission))
                || (this.group.hasPermission(permission))
                || (this.subGroups.stream().anyMatch(permissionGroup -> permissionGroup.hasPermission(permission))));
    }

    @Override
    public void sendMessage(@NotNull final String message) {
        this.getEntity().sendMessage(new TextComponent(MultiPermsPlugin.getInstance().getConfiguration().getPrefix() + message));
    }

    public ProxiedPlayer getEntity() {
        return ProxyServer.getInstance().getPlayer(this.uniqueId);
    }


}
