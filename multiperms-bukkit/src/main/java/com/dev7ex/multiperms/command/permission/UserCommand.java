package com.dev7ex.multiperms.command.permission;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiperms.command.permission.user.*;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@BukkitCommandProperties(name = "user", permission = "multiperms.command.permission.user")
public class UserCommand extends BukkitCommand implements BukkitTabCompleter {

    public UserCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);

        super.registerSubCommand(new AddCommand(plugin));
        super.registerSubCommand(new ClearCommand(plugin));
        super.registerSubCommand(new InfoCommand(plugin));
        super.registerSubCommand(new HelpCommand(plugin));
        super.registerSubCommand(new RemoveCommand(plugin));
        super.registerSubCommand(new SetCommand(plugin));
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if ((arguments.length < 3) || (arguments.length > 7)) {
            Objects.requireNonNull(super.getSubCommand(HelpCommand.class)).execute(commandSender, arguments);
            return;
        }

        if (Bukkit.getPlayer(arguments[1]) == null) {
            commandSender.sendMessage(super.getConfiguration().getString("no-player-found")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }

        if (super.getSubCommand(arguments[2].toLowerCase()).isEmpty()) {
            Objects.requireNonNull(super.getSubCommand(HelpCommand.class)).execute(commandSender, arguments);
            return;
        }
        super.getSubCommand(arguments[2].toLowerCase()).get().execute(commandSender, arguments);
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length == 2) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        }
        if (arguments.length == 3) {
            return Lists.newArrayList(super.getSubCommands().keySet());
        }
        if (super.getSubCommand(arguments[2].toLowerCase()).isEmpty()) {
            return Collections.emptyList();
        }
        final BukkitCommand subCommand = super.getSubCommand(arguments[2].toLowerCase()).get();

        if (!(subCommand instanceof BukkitTabCompleter)) {
            return Collections.emptyList();
        }
        return ((BukkitTabCompleter) subCommand).onTabComplete(commandSender, arguments);
    }

}
