package com.dev7ex.multiperms.command.permission.user;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.CommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import com.dev7ex.multiperms.api.user.PermissionUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@CommandProperties(name = "remove", permission = "multiperms.command.user.remove")
public class RemoveCommand extends BukkitCommand implements TabCompleter {

    public RemoveCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 5) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.remove.usage")
                    .replaceAll("%prefix%", super.getPrefix()));
            return true;
        }

        final PermissionGroupProvider groupProvider = MultiPermsPlugin.getInstance().getGroupProvider();
        final PermissionUser user = MultiPermsPlugin.getInstance().getUserProvider().getUser(arguments[1]).orElseThrow();

        switch (arguments[3]) {
            case "group":
                if (groupProvider.getGroup(arguments[4].toLowerCase()).isEmpty()) {
                    commandSender.sendMessage(super.getConfiguration().getString("messages.general.group-not-exists")
                            .replaceAll("%prefix%", super.getPrefix()));
                    return true;
                }
                final PermissionGroup group = groupProvider.getGroup(arguments[4].toLowerCase()).get();

                if (user.getGroup().getIdentification() == group.getIdentification()) {
                    commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.remove.group.main-group")
                            .replaceAll("%prefix%", super.getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName()));
                    return true;
                }

                if (!user.getSubGroups().contains(group)) {
                    commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.remove.group.user-has-group-not")
                            .replaceAll("%prefix%", super.getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName())
                            .replaceAll("%colored_group_name%", group.getColoredDisplayName()));
                    return true;
                }
                user.getSubGroups().remove(group);
                commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.remove.group.successfully-removed")
                        .replaceAll("%prefix%", super.getPrefix())
                        .replaceAll("%colored_user_name%", user.getColoredName())
                        .replaceAll("%colored_group_name%", group.getColoredDisplayName()));
                break;

            case "permission":
                if (!user.hasPermission(arguments[4])) {
                    commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.remove.permission.user-has-permission-not")
                            .replaceAll("%prefix%", super.getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName()));
                    return true;
                }
                user.getConfiguration().addPermission(arguments[4]);
                user.addPermission(arguments[4]);
                commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.user.remove.permission.successfully-removed")
                        .replaceAll("%prefix%", super.getPrefix())
                        .replaceAll("%colored_user_name%", user.getColoredName())
                        .replaceAll("%permission%", arguments[4]));
                break;
        }

        return true;
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final Command command,
                                            @NotNull final String commandLabel, @NotNull final String[] arguments) {
        if ((arguments.length < 4) || (arguments.length > 5)) {
            return Collections.emptyList();
        }

        if (arguments.length == 4) {
            return List.of("group", "permission");
        }

        if (MultiPermsPlugin.getInstance().getUserProvider().getUser(arguments[1]).isEmpty()) {
            return Collections.emptyList();
        }
        final PermissionUser user = MultiPermsPlugin.getInstance().getUserProvider().getUser(arguments[1]).orElseThrow();

        switch (arguments[3]) {
            case "group":
                return user.getSubGroups().stream().map(PermissionGroup::getName).collect(Collectors.toList());

            case "permission":
                return user.getAllPermissions();

            default:
                return Collections.emptyList();
        }
    }

}
