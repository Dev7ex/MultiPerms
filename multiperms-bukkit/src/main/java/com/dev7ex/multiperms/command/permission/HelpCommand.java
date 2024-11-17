package com.dev7ex.multiperms.command.permission;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@BukkitCommandProperties(name = "help", permission = "multiperms.command.permission")
public class HelpCommand extends BukkitCommand {

    private final DefaultTranslationProvider translationProvider;

    public HelpCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.translationProvider = plugin.getTranslationProvider();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        this.translationProvider.getMessageList(commandSender, "commands.permission.help.message")
                .forEach(message -> commandSender.sendMessage(
                        message.replaceAll("%prefix%", super.getConfiguration().getPrefix())));
    }

}
