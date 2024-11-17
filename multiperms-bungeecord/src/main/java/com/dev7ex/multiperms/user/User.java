package com.dev7ex.multiperms.user;

import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bungeecord.user.BungeePermissionUser;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUserConfiguration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class User implements BungeePermissionUser {

    private UUID uniqueId;
    private String name;
    private PermissionUserConfiguration configuration;
    private long firstLogin;
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
        final Set<String> permissions = new HashSet<>(this.permissions);
        permissions.addAll(this.group.getPermissions());

        this.subGroups.stream()
                .map(PermissionGroup::getPermissions)
                .forEach(permissions::addAll);
        return new ArrayList<>(permissions);
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
        return this.permissions.contains("*")
                || this.permissions.contains(permission)
                || this.group.hasPermission(permission)
                || this.subGroups.stream().anyMatch(subGroup -> subGroup.hasPermission(permission));
    }

    @Override
    public void sendMessage(@NotNull final String message) {
        this.getEntity().sendMessage(new TextComponent(MultiPermsPlugin.getInstance()
                .getConfiguration()
                .getPrefix() + message));
    }

    @Override
    public ProxiedPlayer getEntity() {
        return ProxyServer.getInstance()
                .getPlayer(this.uniqueId);
    }

}
