package com.dev7ex.multiperms.command.permission;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@BukkitCommandProperties(name = "help", permission = "multiperms.command.permission")
public class HelpCommand extends BukkitCommand {

    public HelpCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        super.getConfiguration().getStringList("messages.commands.permission.help.message").forEach(message -> {
            commandSender.sendMessage(message.replaceAll("%prefix%", super.getConfiguration().getPrefix()));
        });
        return;
    }

}
