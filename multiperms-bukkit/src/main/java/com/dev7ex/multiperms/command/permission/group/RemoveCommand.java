package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.group.GroupConfiguration;
import com.dev7ex.multiperms.group.GroupProvider;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dev7ex
 * @since 03.07.2023
 *
 * Permission: multiperms.command.permission.group.remove
 * Usage: /permission group remove <Group> <Permission>
 *
 */
@BukkitCommandProperties(name = "remove", permission = "multiperms.command.permission.group.remove")
public class RemoveCommand extends BukkitCommand implements BukkitTabCompleter {

    private final GroupConfiguration groupConfiguration;
    private final GroupProvider groupProvider;
    private final DefaultTranslationProvider translationProvider;

    public RemoveCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.groupConfiguration = plugin.getGroupConfiguration();
        this.groupProvider = plugin.getGroupProvider();
        this.translationProvider = plugin.getTranslationProvider();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 4) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.group.remove.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }

        if (this.groupProvider.getGroup(arguments[2].toLowerCase()).isEmpty()) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "general.group.not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%group_name%", arguments[2]));
            return;
        }
        final PermissionGroup group = this.groupProvider.getGroup(arguments[2].toLowerCase()).get();

        if (!group.getPermissions().contains(arguments[3])) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.group.remove.group-has-permission-not")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%group_name%", group.getName())
                    .replaceAll("%colored_group_name%", group.getColoredDisplayName())
                    .replaceAll("%permission%", arguments[3]));
            return;
        }
        group.getPermissions().remove(arguments[3]);
        this.groupConfiguration.removePermission(group.getIdentification(), arguments[3]);
        commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.group.remove.successfully-removed")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%group_name%", group.getName())
                .replaceAll("%colored_group_name%", group.getColoredDisplayName())
                .replaceAll("%permission%", arguments[3]));
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if ((arguments.length < 3) || (arguments.length > 4)) {
            return Collections.emptyList();
        }

        if (arguments.length == 3) {
            return this.groupProvider.getGroups()
                    .values()
                    .stream()
                    .map(PermissionGroup::getName)
                    .collect(Collectors.toList());
        }

        if (this.groupProvider.getGroup(arguments[2].toLowerCase()).isEmpty()) {
            return Collections.emptyList();
        }
        return this.groupProvider.getGroup(arguments[2].toLowerCase())
                .get()
                .getPermissions();
    }

}