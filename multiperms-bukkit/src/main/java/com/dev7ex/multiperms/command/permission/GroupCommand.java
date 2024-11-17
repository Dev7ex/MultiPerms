package com.dev7ex.multiperms.command.permission;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.command.permission.group.*;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@BukkitCommandProperties(name = "group", permission = "multiperms.command.permission.group")
public class GroupCommand extends BukkitCommand implements BukkitTabCompleter {

    private final DefaultTranslationProvider translationProvider;

    public GroupCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.translationProvider = plugin.getTranslationProvider();

        super.registerSubCommand(new AddCommand(plugin));
        super.registerSubCommand(new CreateCommand(plugin));
        super.registerSubCommand(new DeleteCommand(plugin));
        super.registerSubCommand(new EditCommand(plugin));
        super.registerSubCommand(new HelpCommand(plugin));
        super.registerSubCommand(new ListCommand(plugin));
        super.registerSubCommand(new RemoveCommand(plugin));
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if ((arguments.length == 1) || (arguments.length > 6)) {
            Objects.requireNonNull(super.getSubCommand(HelpCommand.class)).execute(commandSender, arguments);
            return;
        }

        if (super.getSubCommand(arguments[1].toLowerCase()).isEmpty()) {
            Objects.requireNonNull(super.getSubCommand(HelpCommand.class)).execute(commandSender, arguments);
            return;
        }
        final BukkitCommand subCommand = super.getSubCommand(arguments[1].toLowerCase()).get();

        if (!commandSender.hasPermission(subCommand.getPermission())) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "general.no-permission")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        subCommand.execute(commandSender, arguments);
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length == 2) {
            return Lists.newArrayList(super.getSubCommands().keySet());
        }

        if (super.getSubCommand(arguments[1].toLowerCase()).isEmpty()) {
            return Collections.emptyList();
        }
        final BukkitCommand subCommand = super.getSubCommand(arguments[1].toLowerCase())
                .get();

        if (!commandSender.hasPermission(subCommand.getPermission())) {
            return Collections.emptyList();
        }

        if (!(subCommand instanceof BukkitTabCompleter)) {
            return Collections.emptyList();
        }
        return ((BukkitTabCompleter) subCommand).onTabComplete(commandSender, arguments);
    }

}
