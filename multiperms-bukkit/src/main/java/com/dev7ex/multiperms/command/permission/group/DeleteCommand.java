package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bukkit.event.group.PermissionGroupDeleteEvent;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.group.GroupConfiguration;
import com.dev7ex.multiperms.group.GroupProvider;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author Dev7ex
 * @since 03.07.2023
 *
 * Permission: multiperms.command.permission.group.delete
 * Usage: /permission group delete <Group>
 *
 */
@BukkitCommandProperties(name = "delete", permission = "multiperms.command.permission.group.delete")
public class DeleteCommand extends BukkitCommand implements BukkitTabCompleter {

    private final GroupConfiguration groupConfiguration;
    private final GroupProvider groupProvider;
    private final DefaultTranslationProvider translationProvider;

    public DeleteCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.groupConfiguration = plugin.getGroupConfiguration();
        this.groupProvider = plugin.getGroupProvider();
        this.translationProvider = plugin.getTranslationProvider();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 3) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.group.delete.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }

        if (this.groupProvider.getGroup(arguments[2].toLowerCase()).isEmpty()) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "general.group.not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%group_name%", arguments[2]));
            return;
        }
        final PermissionGroup group = this.groupProvider.getGroup(arguments[2].toLowerCase())
                .get();

        if (group.getIdentification() == 0) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "general.group.delete-locked")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%group_name%", group.getName())
                    .replaceAll("%colored_group_name%", group.getColoredDisplayName()));
            return;
        }
        final PermissionGroupDeleteEvent event = new PermissionGroupDeleteEvent(group);

        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }
        this.groupProvider.unregister(group.getIdentification());
        this.groupConfiguration.remove(group.getIdentification());
        commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.group.delete.successfully-deleted")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%group_name%", group.getName())
                .replaceAll("%colored_group_name%", group.getColoredDisplayName()));
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 3) {
            return Collections.emptyList();
        }
        return this.groupProvider.getGroups()
                .values()
                .stream()
                .filter(group -> group.getIdentification() != 0)
                .map(PermissionGroup::getName)
                .toList();
    }

}
