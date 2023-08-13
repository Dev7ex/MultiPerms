package com.dev7ex.multiperms.command.permission;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.CommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@CommandProperties(name = "help", permission = "multiperms.command.permission")
public class HelpCommand extends BukkitCommand {

    public HelpCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        super.getConfiguration().getStringList("messages.commands.permission.help.message").forEach(message -> {
            commandSender.sendMessage(message.replaceAll("%prefix%", super.getPrefix()));
        });
        return true;
    }

}
