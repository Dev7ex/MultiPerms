package com.dev7ex.multiperms.command.permission;

import com.dev7ex.common.bungeecord.command.ProxyCommand;
import com.dev7ex.common.bungeecord.command.ProxyCommandProperties;
import com.dev7ex.common.bungeecord.plugin.ProxyPlugin;
import com.dev7ex.multiperms.command.permission.group.*;
import com.google.common.collect.Lists;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Objects;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@ProxyCommandProperties(name = "group", permission = "multiperms.command.permission.group")
public class GroupCommand extends ProxyCommand implements TabExecutor {

    public GroupCommand(@NotNull final ProxyPlugin plugin) {
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
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if ((arguments.length == 1) || (arguments.length > 6)) {
            Objects.requireNonNull(super.getSubCommand(HelpCommand.class)).execute(commandSender, arguments);
            return;
        }

        if (super.getSubCommand(arguments[1].toLowerCase()).isEmpty()) {
            Objects.requireNonNull(super.getSubCommand(HelpCommand.class)).execute(commandSender, arguments);
            return;
        }
        super.getSubCommand(arguments[1].toLowerCase()).get().execute(commandSender, arguments);
    }

    @Override
    public final Iterable<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length == 2) {
            return Lists.newArrayList(super.getSubCommands().keySet());
        }

        if (super.getSubCommand(arguments[1].toLowerCase()).isEmpty()) {
            return Collections.emptyList();
        }
        final ProxyCommand subCommand = super.getSubCommand(arguments[1].toLowerCase()).get();

        if (!(subCommand instanceof TabExecutor)) {
            return Collections.emptyList();
        }
        return ((TabExecutor) subCommand).onTabComplete(commandSender, arguments);
    }

}
