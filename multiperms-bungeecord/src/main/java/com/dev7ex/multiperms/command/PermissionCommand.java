package com.dev7ex.multiperms.command;

import com.dev7ex.common.bungeecord.command.ProxyCommand;
import com.dev7ex.common.bungeecord.command.ProxyCommandProperties;
import com.dev7ex.common.bungeecord.plugin.ProxyPlugin;
import com.dev7ex.multiperms.command.permission.GroupCommand;
import com.dev7ex.multiperms.command.permission.HelpCommand;
import com.dev7ex.multiperms.command.permission.ReloadCommand;
import com.dev7ex.multiperms.command.permission.UserCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Objects;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@ProxyCommandProperties(name = "bpermission", permission = "multiperms.command.permission", aliases = {"bmp", "bperms"})
public class PermissionCommand extends ProxyCommand implements TabExecutor {

    public PermissionCommand(@NotNull final ProxyPlugin plugin) {
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

        if (super.getSubCommand(arguments[0].toLowerCase()).isEmpty()) {
            Objects.requireNonNull(super.getSubCommand(HelpCommand.class)).execute(commandSender, arguments);
            return;
        }
        super.getSubCommand(arguments[0].toLowerCase()).get().execute(commandSender, arguments);
    }

    @Override
    public Iterable<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length == 1) {
            return super.getSubCommands().keySet();
        }

        if (super.getSubCommand(arguments[0].toLowerCase()).isEmpty()) {
            return Collections.emptyList();
        }
        final ProxyCommand subCommand = super.getSubCommand(arguments[0].toLowerCase()).get();

        if (!(subCommand instanceof TabExecutor)) {
            return Collections.emptyList();
        }
        return ((TabExecutor) subCommand).onTabComplete(commandSender, arguments);
    }
}
