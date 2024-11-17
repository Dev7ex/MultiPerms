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
@BungeeCommandProperties(name = "reload", permission = "multiperms.command.permission.reload")
public class ReloadCommand extends BungeeCommand {

    private final DefaultTranslationProvider translationProvider;

    public ReloadCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.translationProvider = plugin.getTranslationProvider();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 1) {
            commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "commands.permission.reload.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }
        super.getConfiguration().load();

        commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "commands.permission.reload.message")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
    }

}
