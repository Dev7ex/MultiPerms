package com.dev7ex.multiperms.command.permission;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 08.08.2023
 */
@BukkitCommandProperties(name = "reload", permission = "multiperms.command.permission.reload")
public class ReloadCommand extends BukkitCommand {

    private final DefaultTranslationProvider translationProvider;

    public ReloadCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.translationProvider = plugin.getTranslationProvider();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 1) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.reload.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        super.getConfiguration().load();

        commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.reload.message")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
    }

}
