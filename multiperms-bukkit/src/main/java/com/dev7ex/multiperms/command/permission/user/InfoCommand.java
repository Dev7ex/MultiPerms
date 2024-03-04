package com.dev7ex.multiperms.command.permission.user;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUser;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@BukkitCommandProperties(name = "info", permission = "multiperms.command.permission.user.info")
public class InfoCommand extends BukkitCommand {

    public InfoCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        final PermissionUser user = MultiPermsPlugin.getInstance().getUserProvider().getUser(arguments[1]).orElseThrow();

        super.getConfiguration().getStringList("messages.commands.permission.user.info.message").forEach(message -> {
            commandSender.sendMessage(message.replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%unique_id%", user.getUniqueId().toString())
                    .replaceAll("%colored_user_name%", user.getColoredName())
                    .replaceAll("%colored_group_name%", user.getGroup().getColoredDisplayName())
                    .replaceAll("%colored_group_names%", this.formatSubGroups(user))
                    .replaceAll("%permissions%", this.formatPermissions(user)));
        });
        return;
    }

    public final String formatSubGroups(final PermissionUser user) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (user.getSubGroups().isEmpty()) {
            return "{}";
        }

        for (final PermissionGroup group : user.getSubGroups()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(ChatColor.GRAY);
                stringBuilder.append(", ");
            }
            stringBuilder.append(group.getColoredDisplayName());
        }
        return stringBuilder.toString();
    }

    public final String formatPermissions(final PermissionUser user) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (user.getPermissions().isEmpty()) {
            return "{}";
        }


        for (final String permission : user.getPermissions()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(ChatColor.GRAY);
                stringBuilder.append(", ");
            }
            stringBuilder.append(ChatColor.GREEN + permission);
        }

        return stringBuilder.toString();
    }

}
