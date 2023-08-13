package com.dev7ex.multiperms.command.permission;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.CommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiperms.command.permission.group.*;
import com.google.common.collect.Lists;
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
@CommandProperties(name = "group", permission = "multiperms.command.permission.group")
public class GroupCommand extends BukkitCommand implements TabCompleter {

    public GroupCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);

        super.registerSubCommand(new AddCommand(plugin));
        super.registerSubCommand(new CreateCommand(plugin));
        super.registerSubCommand(new DeleteCommand(plugin));
        super.registerSubCommand(new EditCommand(plugin));
        super.registerSubCommand(new HelpCommand(plugin));
        super.registerSubCommand(new ListCommand(plugin));
        super.registerSubCommand(new RemoveCommand(plugin));
    }

    @Override
    public boolean execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if ((arguments.length == 1) || (arguments.length > 6)) {
            return super.getSubCommand("help").orElseThrow().execute(commandSender, arguments);
        }

        if (super.getSubCommand(arguments[1].toLowerCase()).isEmpty()) {
            super.getSubCommand("help").orElseThrow().execute(commandSender, arguments);
            return true;
        }
        return super.getSubCommand(arguments[1].toLowerCase()).get().execute(commandSender, arguments);
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final Command command,
                                            @NotNull final String commandLabel, @NotNull final String[] arguments) {
        if (arguments.length == 2) {
            return Lists.newArrayList(super.getSubCommands().keySet());
        }

        if (super.getSubCommand(arguments[1].toLowerCase()).isEmpty()) {
            return Collections.emptyList();
        }
        final BukkitCommand subCommand = super.getSubCommand(arguments[1].toLowerCase()).get();

        if (!(subCommand instanceof TabCompleter)) {
            return Collections.emptyList();
        }
        return ((TabCompleter) subCommand).onTabComplete(commandSender, command, commandLabel, arguments);
    }

}
