package com.dev7ex.multiperms.command.permission.user;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import com.dev7ex.multiperms.api.user.PermissionUser;
import com.dev7ex.multiperms.api.user.PermissionUserProperty;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@BukkitCommandProperties(name = "clear", permission = "multiperms.command.permission.user.clear")
public class ClearCommand extends BukkitCommand implements BukkitTabCompleter {

    public ClearCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 4) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.clear.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        final PermissionGroupProvider groupProvider = MultiPermsPlugin.getInstance().getGroupProvider();
        final PermissionUser user = MultiPermsPlugin.getInstance().getUserProvider().getUser(arguments[1]).orElseThrow();

        switch (arguments[3]) {
            case "group":
                if (user.getSubGroups().isEmpty()) {
                    commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.clear.group.user-groups-empty")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName()));
                    return;
                }
                user.getSubGroups().clear();
                user.getConfiguration().write(PermissionUserProperty.SUB_GROUPS, Collections.emptyList());
                commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.clear.group.successfully-cleared")
                        .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                        .replaceAll("%colored_user_name%", user.getColoredName()));
                return;

            case "permission":
                if (user.getPermissions().isEmpty()) {
                    commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.clear.permission.user-permissions-empty")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName()));
                    return;
                }
                user.getPermissions().clear();
                user.getConfiguration().write(PermissionUserProperty.PERMISSION, Collections.emptyList());
                commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.clear.permission.successfully-cleared")
                        .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                        .replaceAll("%colored_user_name%", user.getColoredName()));

            default:
                commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.clear.usage")
                        .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
                return;
        }
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {

        if (arguments.length == 4) {
            return List.of("group", "permission");
        }
        return null;
    }

}
