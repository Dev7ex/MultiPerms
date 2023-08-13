package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.CommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
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
@CommandProperties(name = "add", permission = "multiperms.command.permission.group.add")
public class AddCommand extends BukkitCommand implements TabCompleter {

    public AddCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 4) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.add.usage")
                    .replaceAll("%prefix%", super.getPrefix()));
            return true;
        }
        final PermissionGroupProvider groupProvider = MultiPermsPlugin.getInstance().getGroupProvider();

        if (groupProvider.getGroup(arguments[2].toLowerCase()).isEmpty()) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.general.group-not-exists")
                    .replaceAll("%prefix%", super.getPrefix()));
            return true;
        }
        final PermissionGroup group = groupProvider.getGroup(arguments[2].toLowerCase()).get();

        if (group.getPermissions().contains(arguments[3])) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.add.group-has-permission")
                    .replaceAll("%prefix%", super.getPrefix())
                    .replaceAll("%colored_group_name%", group.getColoredDisplayName())
                    .replaceAll("%permission%", arguments[3]));
            return true;
        }
        group.getPermissions().add(arguments[3]);
        commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.add.successfully-added")
                .replaceAll("%prefix%", super.getPrefix())
                .replaceAll("%colored_group_name%", group.getColoredDisplayName())
                .replaceAll("%permission%", arguments[3]));
        groupProvider.getConfiguration().addPermission(group.getIdentification(), arguments[3]);
        return true;
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final Command command,
                                            @NotNull final String commandLabel, @NotNull final String[] arguments) {
        if ((arguments.length < 3) || (arguments.length > 4)) {
            return Collections.emptyList();
        }
        if (arguments.length == 3) {
            return MultiPermsPlugin.getInstance().getGroupProvider().getGroups().values().stream().map(PermissionGroup::getName).collect(Collectors.toList());
        }
        return MultiPermsPlugin.getInstance().getPermissionHookProvider().getAllPermissions();
    }

}