package com.dev7ex.multiperms.command.permission;

import com.dev7ex.common.bungeecord.command.BungeeCommand;
import com.dev7ex.common.bungeecord.command.BungeeCommandProperties;
import com.dev7ex.common.bungeecord.plugin.BungeePlugin;
import com.dev7ex.multiperms.MultiPermsPlugin;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 23.11.2024
 */
@BungeeCommandProperties(name = "version", permission = "multiperms.command.permission.version")
public class VersionCommand extends BungeeCommand {

    public VersionCommand(@NotNull final BungeePlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        final MultiPermsPlugin plugin = MultiPermsPlugin.getInstance();

        commandSender.sendMessage(new TextComponent(" "));
        commandSender.sendMessage(new TextComponent("§f§m                    §r§r " + super.getConfiguration().getPrefix() + " §f§m                    "));
        commandSender.sendMessage(new TextComponent(" "));
        commandSender.sendMessage(new TextComponent("§8» §2Version: §a" + plugin.getDescription().getVersion()));
        commandSender.sendMessage(new TextComponent("§8» §2Authors: " + plugin.getDescription().getAuthor()));
        commandSender.sendMessage(new TextComponent("§8» §2Support: §ahttps://discord.gg/ta33bbA8eF"));
        commandSender.sendMessage(new TextComponent("§8» §2Wiki: §ahttps://github.com/Dev7ex/MultiPerms/wiki"));
        commandSender.sendMessage(new TextComponent("§8» §2Report Bug: §ahttps://github.com/Dev7ex/MultiPerms/issues"));
        commandSender.sendMessage(new TextComponent(" "));
        commandSender.sendMessage(new TextComponent("§f§m                    §r§r " + super.getConfiguration().getPrefix() + " §f§m                    "));
        commandSender.sendMessage(new TextComponent(" "));
    }

}