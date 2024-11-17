package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.group.GroupProvider;
import com.dev7ex.multiperms.hook.DefaultPermissionHookProvider;
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
 * Permission: multiperms.command.permission.group.add
 * Usage: /permission group add <Group> <Permission>
 *
 */
@BukkitCommandProperties(name = "add", permission = "multiperms.command.permission.group.add")
public class AddCommand extends BukkitCommand implements BukkitTabCompleter {

    private final DefaultPermissionHookProvider permissionHookProvider;
    private final GroupProvider groupProvider;
    private final DefaultTranslationProvider translationProvider;

    public AddCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.permissionHookProvider = plugin.getPermissionHookProvider();
        this.groupProvider = plugin.getGroupProvider();
        this.translationProvider = plugin.getTranslationProvider();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 4) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.group.add.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }

        if (this.groupProvider.getGroup(arguments[2].toLowerCase()).isEmpty()) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender,"general.group.not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%group_name%", arguments[2]));
            return;
        }
        final PermissionGroup group = this.groupProvider.getGroup(arguments[2].toLowerCase()).get();

        if (group.getPermissions().contains(arguments[3])) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender,"commands.permission.group.add.group-has-permission")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%colored_group_name%", group.getColoredDisplayName())
                    .replaceAll("%permission%", arguments[3]));
            return;
        }
        group.getPermissions().add(arguments[3]);
        this.groupProvider.getConfiguration().addPermission(group.getIdentification(), arguments[3]);
        commandSender.sendMessage(this.translationProvider.getMessage(commandSender,"commands.permission.group.add.successfully-added")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
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
        return this.permissionHookProvider.getRegisteredPermissions();
    }

}