package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.CommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bukkit.event.group.PermissionGroupDeleteEvent;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@CommandProperties(name = "delete", permission = "multiperms.command.permission.group.delete")
public class DeleteCommand extends BukkitCommand implements TabCompleter {

    public DeleteCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 3) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.create.usage")
                    .replaceAll("%prefix%", super.getPrefix()));
            return true;
        }
        final PermissionGroupProvider groupProvider = MultiPermsPlugin.getInstance().getGroupProvider();
        final PermissionGroupConfiguration groupConfiguration = MultiPermsPlugin.getInstance().getGroupConfiguration();

        if (groupProvider.getGroup(arguments[2].toLowerCase()).isEmpty()) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.general.group-not-exists")
                    .replaceAll("%prefix%", super.getPrefix()));
            return true;
        }
        final PermissionGroup group = groupProvider.getGroup(arguments[2].toLowerCase()).get();
        final PermissionGroupDeleteEvent event = new PermissionGroupDeleteEvent(group);

        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return true;
        }
        groupProvider.unregister(group.getIdentification());
        groupConfiguration.remove(group.getIdentification());
        commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.delete.successfully-deleted")
                .replaceAll("%prefix%", super.getPrefix())
                .replaceAll("%colored_group_name%", group.getColoredDisplayName()));
        return true;
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final Command command,
                                            @NotNull final String commandLabel, @NotNull final String[] arguments) {
        if (arguments.length != 3) {
            return Collections.emptyList();
        }
        return MultiPermsPlugin.getInstance().getGroupProvider().getGroups().values().stream().map(PermissionGroup::getName).toList();
    }

}
