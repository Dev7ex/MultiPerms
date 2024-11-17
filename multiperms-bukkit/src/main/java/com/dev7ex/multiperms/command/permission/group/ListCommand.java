package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.group.GroupProvider;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dev7ex
 * @since 03.07.2023
 *
 * Permission: multiperms.command.permission.group.list
 * Usage: /permission group list
 *
 */
@BukkitCommandProperties(name = "list", permission = "multiperms.command.permission.group.list")
public class ListCommand extends BukkitCommand {

    private final GroupProvider groupProvider;
    private final DefaultTranslationProvider translationProvider;

    public ListCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.groupProvider = plugin.getGroupProvider();
        this.translationProvider = plugin.getTranslationProvider();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 2) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.group.list.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        final List<PermissionGroup> groups = new ArrayList<>(this.groupProvider.getGroups().values());
        Collections.sort(groups);

        for (final PermissionGroup group : groups) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(ChatColor.GRAY);
                stringBuilder.append(", ");
            }
            stringBuilder.append(group.getColoredDisplayName());
        }
        commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.group.list.message")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%colored_group_names%", stringBuilder.toString()));
    }

}
