package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.CommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@CommandProperties(name = "list", permission = "multiperms.command.permission.group.list")
public class ListCommand extends BukkitCommand {

    public ListCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 2) {
            commandSender.sendMessage(super.getPrefix() + "§cUsage: /permission group list");
            return true;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        final List<PermissionGroup> groups = new ArrayList<>(MultiPermsPlugin.getInstance().getGroupProvider().getGroups().values());
        Collections.sort(groups);

        for (final PermissionGroup group : groups) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(ChatColor.GRAY);
                stringBuilder.append(", ");
            }
            stringBuilder.append(group.getColoredDisplayName());
        }
        commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.list.message")
                .replaceAll("%prefix%", super.getPrefix())
                .replaceAll("%colored_group_names%", stringBuilder.toString()));
        return true;
    }

}
