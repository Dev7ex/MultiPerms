package com.dev7ex.multiperms.command.permission.user;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.user.PermissionUser;
import com.dev7ex.multiperms.api.user.PermissionUserProperty;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import com.dev7ex.multiperms.user.UserProvider;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author Dev7ex
 * @since 03.07.2023
 *
 * Permission: multiperms.command.permission.group.add
 * Usage: /permission user <User> clear <Group | Permission>
 *
 */
@BukkitCommandProperties(name = "clear", permission = "multiperms.command.permission.user.clear")
public class ClearCommand extends BukkitCommand implements BukkitTabCompleter {

    private final DefaultTranslationProvider translationProvider;
    private final UserProvider userProvider;

    public ClearCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.translationProvider = plugin.getTranslationProvider();
        this.userProvider = plugin.getUserProvider();
    }


    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 4) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.user.clear.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        final PermissionUser user = this.userProvider.getUser(arguments[1]).orElseThrow();

        switch (arguments[3]) {
            case "group":
                if (user.getSubGroups().isEmpty()) {
                    commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.user.clear.group.user-groups-empty")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName()));
                    return;
                }
                user.getSubGroups().clear();
                user.getConfiguration().write(PermissionUserProperty.SUB_GROUPS, Collections.emptyList());
                commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.user.clear.group.successfully-cleared")
                        .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                        .replaceAll("%colored_user_name%", user.getColoredName()));
                return;

            case "permission":
                if (user.getPermissions().isEmpty()) {
                    commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.user.clear.permission.user-permissions-empty")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName()));
                    return;
                }
                user.getPermissions().clear();
                user.getConfiguration().write(PermissionUserProperty.PERMISSION, Collections.emptyList());
                commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.user.clear.permission.successfully-cleared")
                        .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                        .replaceAll("%colored_user_name%", user.getColoredName()));
                break;

            default:
                commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.user.clear.usage")
                        .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
        }
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length == 4) {
            return List.of("group", "permission");
        }
        return Collections.emptyList();
    }

}
