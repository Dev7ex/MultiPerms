package com.dev7ex.multiperms.command;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiperms.command.permission.GroupCommand;
import com.dev7ex.multiperms.command.permission.HelpCommand;
import com.dev7ex.multiperms.command.permission.ReloadCommand;
import com.dev7ex.multiperms.command.permission.UserCommand;
import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@BukkitCommandProperties(name = "permission", permission = "multiperms.command.permission", aliases = {"mp", "perms"})
public class PermissionCommand extends BukkitCommand implements BukkitTabCompleter {

    public PermissionCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);

        super.registerSubCommand(new GroupCommand(plugin));
        super.registerSubCommand(new HelpCommand(plugin));
        super.registerSubCommand(new ReloadCommand(plugin));
        super.registerSubCommand(new UserCommand(plugin));
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if ((arguments.length == 0) || (arguments.length > 6)) {
            Objects.requireNonNull(super.getSubCommand(HelpCommand.class)).execute(commandSender, arguments);
            return;
        }
        final Optional<BukkitCommand> commandOptional = super.getSubCommand(arguments[0].toLowerCase());

        if (super.getSubCommand(arguments[0].toLowerCase()).isEmpty()) {
            Objects.requireNonNull(super.getSubCommand(HelpCommand.class)).execute(commandSender, arguments);
            return;
        }
        super.getSubCommand(arguments[0].toLowerCase()).get().execute(commandSender, arguments);
    }

    @Override
    public List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length == 1) {
            return Lists.newArrayList(super.getSubCommands().keySet());
        }
        final Optional<BukkitCommand> commandOptional = super.getSubCommand(arguments[0].toLowerCase());

        if ((commandOptional.isEmpty()) || (!(commandOptional.get() instanceof BukkitTabCompleter))) {
            return null;
        }
        return ((BukkitTabCompleter) commandOptional.get()).onTabComplete(commandSender, arguments);
    }


}
