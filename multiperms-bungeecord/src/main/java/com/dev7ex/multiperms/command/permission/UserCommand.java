package com.dev7ex.multiperms.command.permission;

import com.dev7ex.common.bungeecord.command.BungeeCommand;
import com.dev7ex.common.bungeecord.command.BungeeCommandProperties;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.command.permission.user.*;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import com.google.common.collect.Lists;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Objects;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@BungeeCommandProperties(name = "user", permission = "multiperms.command.permission.user")
public class UserCommand extends BungeeCommand implements TabExecutor {

    private final DefaultTranslationProvider translationProvider;

    public UserCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.translationProvider = plugin.getTranslationProvider();

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

        if (ProxyServer.getInstance().getPlayer(arguments[1]) == null) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "general.no-player-found")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%player_name%", arguments[1]));
            return;
        }

        if (super.getSubCommand(arguments[2].toLowerCase()).isEmpty()) {
            Objects.requireNonNull(super.getSubCommand(HelpCommand.class)).execute(commandSender, arguments);
            return;
        }
        final BungeeCommand subCommand = super.getSubCommand(arguments[2].toLowerCase())
                .get();

        if (!commandSender.hasPermission(subCommand.getPermission())) {
            commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "general.no-permission")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }
        subCommand.execute(commandSender, arguments);
    }

    @Override
    public Iterable<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length == 2) {
            return ProxyServer.getInstance().getPlayers().stream().map(CommandSender::getName).toList();
        }
        if (arguments.length == 3) {
            return Lists.newArrayList(super.getSubCommands().keySet());
        }
        if (super.getSubCommand(arguments[2].toLowerCase()).isEmpty()) {
            return Collections.emptyList();
        }
        final BungeeCommand subCommand = super.getSubCommand(arguments[2].toLowerCase())
                .get();

        if (!commandSender.hasPermission(subCommand.getPermission())) {
            return Collections.emptyList();
        }
        if (!(subCommand instanceof TabExecutor)) {
            return Collections.emptyList();
        }
        return ((TabExecutor) subCommand).onTabComplete(commandSender, arguments);
    }

}
