package com.dev7ex.multiperms.command;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.CommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiperms.command.permission.GroupCommand;
import com.dev7ex.multiperms.command.permission.HelpCommand;
import com.dev7ex.multiperms.command.permission.ReloadCommand;
import com.dev7ex.multiperms.command.permission.UserCommand;
import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@CommandProperties(name = "permission", permission = "multiperms.command.permission", aliases = {"mp", "perms"})
public class PermissionCommand extends BukkitCommand implements TabCompleter {

    public PermissionCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);

        super.registerSubCommand(new GroupCommand(plugin));
        super.registerSubCommand(new HelpCommand(plugin));
        super.registerSubCommand(new ReloadCommand(plugin));
        super.registerSubCommand(new UserCommand(plugin));
    }

    @Override
    public boolean execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if ((arguments.length == 0) || (arguments.length > 6)) {
            return super.getSubCommand("help").orElseThrow().execute(commandSender, arguments);
        }
        final Optional<BukkitCommand> commandOptional = super.getSubCommand(arguments[0].toLowerCase());

        if (commandOptional.isEmpty()) {
            return super.getSubCommand("help").orElseThrow().execute(commandSender, arguments);
        }
        return commandOptional.get().execute(commandOptional.get(), commandSender, arguments);
    }

    @Override
    public List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final Command command,
                                      @NotNull final String commandLabel, @NotNull final String[] arguments) {
        if (arguments.length == 1) {
            return Lists.newArrayList(super.getSubCommands().keySet());
        }
        final Optional<BukkitCommand> commandOptional = super.getSubCommand(arguments[0].toLowerCase());

        if ((commandOptional.isEmpty()) || (!(commandOptional.get() instanceof TabCompleter))) {
            return null;
        }
        return ((TabCompleter) commandOptional.get()).onTabComplete(commandSender, command, commandLabel, arguments);
    }


}
