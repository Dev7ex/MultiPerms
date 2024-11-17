package com.dev7ex.multiperms.command.permission.user;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.multiperms.MultiPermsConfiguration;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUser;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import com.dev7ex.multiperms.user.UserProvider;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * @author Dev7ex
 * @since 03.07.2023
 *
 * Permission: multiperms.command.permission.user.info
 * Usage: /permission user <User> info
 *
 */
@BukkitCommandProperties(name = "info", permission = "multiperms.command.permission.user.info")
public class InfoCommand extends BukkitCommand {

    private final MultiPermsConfiguration configuration;
    private final DefaultTranslationProvider translationProvider;
    private final UserProvider userProvider;

    public InfoCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);
        this.configuration = plugin.getConfiguration();
        this.translationProvider = plugin.getTranslationProvider();
        this.userProvider = plugin.getUserProvider();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 3) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.user.info.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        final PermissionUser user = this.userProvider.getUser(arguments[1])
                .orElseThrow();

        this.translationProvider.getMessageList(commandSender, "commands.permission.user.info.message").forEach(message -> {
            commandSender.sendMessage(message.replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%first_login%", this.configuration.getTimeFormat().format(new Date(user.getFirstLogin())))
                    .replaceAll("%last_login%", this.configuration.getTimeFormat().format(new Date(user.getLastLogin())))
                    .replaceAll("%first_login_timestamp%", String.valueOf(user.getFirstLogin()))
                    .replaceAll("%last_login_timestamp%", String.valueOf(user.getLastLogin()))
                    .replaceAll("%unique_id%", user.getUniqueId().toString())
                    .replaceAll("%colored_user_name%", user.getColoredName())
                    .replaceAll("%colored_group_name%", user.getGroup().getColoredDisplayName())
                    .replaceAll("%colored_group_names%", this.formatSubGroups(user))
                    .replaceAll("%permissions%", this.formatPermissions(user)));
        });
    }

    public final String formatSubGroups(@NotNull final PermissionUser user) {
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

    public final String formatPermissions(@NotNull final PermissionUser user) {
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
