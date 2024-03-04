package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@BukkitCommandProperties(name = "remove", permission = "multiperms.command.permission.group.remove")
public class RemoveCommand extends BukkitCommand implements BukkitTabCompleter {

    public RemoveCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 4) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.remove.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        final PermissionGroupProvider groupProvider = MultiPermsPlugin.getInstance().getGroupProvider();
        final PermissionGroupConfiguration groupConfiguration = MultiPermsPlugin.getInstance().getGroupConfiguration();

        if (groupProvider.getGroup(arguments[2].toLowerCase()).isEmpty()) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.general.group-not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        final PermissionGroup group = groupProvider.getGroup(arguments[2].toLowerCase()).get();

        if (!group.getPermissions().contains(arguments[3])) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.remove.group-has-permission-not")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%colored_group_name%", group.getColoredDisplayName())
                    .replaceAll("%permission%", arguments[3]));
            return;
        }
        group.getPermissions().remove(arguments[3]);
        groupConfiguration.removePermission(group.getIdentification(), arguments[3]);
        commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.remove.successfully-removed")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%colored_group_name%", group.getColoredDisplayName())
                .replaceAll("%permission%", arguments[3]));
        return;
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if ((arguments.length < 3) || (arguments.length > 4)) {
            return Collections.emptyList();
        }

        if (arguments.length == 3) {
            return MultiPermsPlugin.getInstance().getGroupProvider()
                    .getGroups()
                    .values()
                    .stream()
                    .map(PermissionGroup::getName)
                    .collect(Collectors.toList());
        }

        if (MultiPermsPlugin.getInstance().getGroupProvider().getGroup(arguments[2].toLowerCase()).isEmpty()) {
            return Collections.emptyList();
        }
        return MultiPermsPlugin.getInstance().getGroupProvider().getGroup(arguments[2].toLowerCase()).get().getPermissions();
    }

}