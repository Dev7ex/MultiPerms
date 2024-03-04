package com.dev7ex.multiperms.command.permission;

import com.dev7ex.common.bungeecord.command.ProxyCommand;
import com.dev7ex.common.bungeecord.command.ProxyCommandProperties;
import com.dev7ex.common.bungeecord.plugin.ProxyPlugin;
import net.md_5.bungee.api.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@ProxyCommandProperties(name = "help", permission = "multiperms.command.permission")
public class HelpCommand extends ProxyCommand {

    public HelpCommand(@NotNull final ProxyPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        super.getConfiguration().getStringList("messages.commands.permission.help.message").forEach(message -> {
            commandSender.sendMessage(message.replaceAll("%prefix%", super.getConfiguration().getPrefix()));
        });
    }

}
