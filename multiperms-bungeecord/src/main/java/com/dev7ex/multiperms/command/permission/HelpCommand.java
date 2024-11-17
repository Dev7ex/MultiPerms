package com.dev7ex.multiperms.command.permission;

import com.dev7ex.common.bungeecord.command.BungeeCommand;
import com.dev7ex.common.bungeecord.command.BungeeCommandProperties;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@BungeeCommandProperties(name = "help", permission = "multiperms.command.permission")
public class HelpCommand extends BungeeCommand {

    private final DefaultTranslationProvider translationProvider;

    public HelpCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.translationProvider = plugin.getTranslationProvider();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        this.translationProvider.getMessageList(commandSender, "commands.permission.help.message")
                .forEach(message -> commandSender.sendMessage(
                        new TextComponent(message.replaceAll("%prefix%", super.getConfiguration().getPrefix()))));
    }

}
