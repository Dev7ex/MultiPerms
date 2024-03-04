package com.dev7ex.multiperms.command.permission.user;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import com.dev7ex.multiperms.api.user.PermissionUser;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@BukkitCommandProperties(name = "add", permission = "multiperms.command.permission.user.add")
public class AddCommand extends BukkitCommand implements BukkitTabCompleter {

    public AddCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 5) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.add.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }

        final PermissionGroupProvider groupProvider = MultiPermsPlugin.getInstance().getGroupProvider();
        final PermissionUser user = MultiPermsPlugin.getInstance().getUserProvider().getUser(arguments[1]).orElseThrow();

        switch (arguments[3]) {
            case "group":
                if (groupProvider.getGroup(arguments[4].toLowerCase()).isEmpty()) {
                    commandSender.sendMessage(super.getConfiguration().getString("messages.general.group-not-exists")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
                    return;
                }
                final PermissionGroup group = groupProvider.getGroup(arguments[4].toLowerCase()).get();

                if (user.getGroup().getIdentification() == group.getIdentification()) {
                    commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.add.group.main-group")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName()));
                    return;
                }

                if (user.getSubGroups().contains(group)) {
                    commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.add.group.user-has-group")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName()));
                    return;
                }
                user.getSubGroups().add(group);
                commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.add.group.successfully-added")
                        .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                        .replaceAll("%colored_user_name%", user.getColoredName())
                        .replaceAll("%colored_group_name%", group.getColoredDisplayName()));
                break;

            case "permission":
                if (user.hasPermission(arguments[4])) {
                    commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.add.permission.user-has-permission")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName()));
                    return;
                }
                user.getConfiguration().addPermission(arguments[4]);
                user.addPermission(arguments[4]);
                commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.add.permission.successfully-added")
                        .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                        .replaceAll("%colored_user_name%", user.getColoredName())
                        .replaceAll("%permission%", arguments[4]));
                break;

            default:
                commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.add.usage")
                        .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
                return;
        }
        return;
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {

        if (arguments.length < 4 || arguments.length > 5) {
            return Collections.emptyList();
        }

        if (arguments.length == 4) {
            return List.of("group", "permission");
        }

        switch (arguments[3]) {
            case "group":
                return MultiPermsPlugin.getInstance().getGroupProvider().getGroups().values().stream().map(PermissionGroup::getName).toList();

            case "permission":
                return MultiPermsPlugin.getInstance().getPermissionHookProvider().getAllPermissions();

            default:
                return Collections.emptyList();
        }
    }

}
