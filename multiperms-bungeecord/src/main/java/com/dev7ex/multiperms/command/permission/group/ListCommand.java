package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bungeecord.command.ProxyCommand;
import com.dev7ex.common.bungeecord.command.ProxyCommandProperties;
import com.dev7ex.common.bungeecord.plugin.ProxyPlugin;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@ProxyCommandProperties(name = "list", permission = "multiperms.command.permission.group.list")
public class ListCommand extends ProxyCommand {

    public ListCommand(@NotNull final ProxyPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 2) {
            commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.group.list.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        final List<PermissionGroup> groups = new ArrayList<>(MultiPermsPlugin.getInstance().getGroupProvider().getGroups().values());
        Collections.sort(groups);

        for (final PermissionGroup group : groups) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(ChatColor.GRAY);
                stringBuilder.append(", ");
            }
            stringBuilder.append(group.getColoredDisplayName());
        }
        commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.group.list.message")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%colored_group_names%", stringBuilder.toString())));
    }

}
