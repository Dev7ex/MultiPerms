package com.dev7ex.multiperms.command.permission;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiperms.MultiPermsPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 09.11.2024
 */
@BukkitCommandProperties(name = "version", permission = "multiperms.command.permission.version")
public class VersionCommand extends BukkitCommand {

    public VersionCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        final MultiPermsPlugin plugin = MultiPermsPlugin.getInstance();

        commandSender.sendMessage(" ");
        commandSender.sendMessage("§f§m                    §r§r " + super.getConfiguration().getPrefix() + " §f§m                    ");
        commandSender.sendMessage(" ");
        commandSender.sendMessage("§8» §2Version: §a" + plugin.getDescription().getVersion());
        commandSender.sendMessage("§8» §2Authors: " + this.getAuthors());
        commandSender.sendMessage("§8» §2Support: §ahttps://discord.gg/ta33bbA8eF");
        commandSender.sendMessage("§8» §2Wiki: §ahttps://github.com/Dev7ex/MultiPerms/wiki");
        commandSender.sendMessage("§8» §2Report Bug: §ahttps://github.com/Dev7ex/MultiPerms/issues");
        commandSender.sendMessage(" ");
        commandSender.sendMessage("§f§m                    §r§r " + super.getConfiguration().getPrefix() + " §f§m                    ");
        commandSender.sendMessage(" ");
    }

    private String getAuthors() {
        final StringBuilder stringBuilder = new StringBuilder();

        for (final String author : MultiPermsPlugin.getInstance().getDescription().getAuthors()) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(ChatColor.GRAY);
                stringBuilder.append(", ");
            }
            stringBuilder.append(ChatColor.GREEN).append(author);
        }
        return stringBuilder.toString();
    }

}