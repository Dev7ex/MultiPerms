package com.dev7ex.multiperms.command.permission.user;

import com.dev7ex.common.bungeecord.command.ProxyCommand;
import com.dev7ex.common.bungeecord.command.ProxyCommandProperties;
import com.dev7ex.common.bungeecord.plugin.ProxyPlugin;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUser;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@ProxyCommandProperties(name = "info", permission = "multiperms.command.permission.user.info")
public class InfoCommand extends ProxyCommand {

    public InfoCommand(@NotNull final ProxyPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        final PermissionUser user = MultiPermsPlugin.getInstance().getUserProvider().getUser(arguments[1]).orElseThrow();

        super.getConfiguration().getStringList("messages.commands.permission.user.info.message").forEach(message -> {
            commandSender.sendMessage(new TextComponent(message.replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%unique_id%", user.getUniqueId().toString())
                    .replaceAll("%colored_user_name%", user.getColoredName())
                    .replaceAll("%colored_group_name%", user.getGroup().getColoredDisplayName())
                    .replaceAll("%colored_group_names%", this.formatSubGroups(user))
                    .replaceAll("%permissions%", this.formatPermissions(user))));
        });
        return;
    }

    public final String formatSubGroups(final PermissionUser user) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (user.getSubGroups().isEmpty()) {
            return "{}";
        }

        for (final PermissionGroup group : user.getSubGroups()) {
            if (!stringBuilder.isEmpty()) {
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
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(ChatColor.GRAY);
                stringBuilder.append(", ");
            }
            stringBuilder.append(ChatColor.GREEN).append(permission);
        }

        return stringBuilder.toString();
    }

}
