package com.dev7ex.multiperms.command.permission;

import com.dev7ex.common.bungeecord.command.ProxyCommand;
import com.dev7ex.common.bungeecord.command.ProxyCommandProperties;
import com.dev7ex.common.bungeecord.plugin.ProxyPlugin;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@ProxyCommandProperties(name = "reload", permission = "multiperms.command.permission.reload")
public class ReloadCommand extends ProxyCommand {

    public ReloadCommand(@NotNull final ProxyPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 1) {
            commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.reload.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }
        super.getConfiguration().load();
        commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.reload.message")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
    }

}
