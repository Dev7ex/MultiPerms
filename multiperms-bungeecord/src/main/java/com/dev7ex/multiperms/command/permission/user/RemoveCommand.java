package com.dev7ex.multiperms.command.permission.user;

import com.dev7ex.common.bungeecord.command.BungeeCommand;
import com.dev7ex.common.bungeecord.command.BungeeCommandProperties;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUser;
import com.dev7ex.multiperms.group.GroupProvider;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import com.dev7ex.multiperms.user.UserProvider;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@BungeeCommandProperties(name = "remove", permission = "multiperms.command.user.remove")
public class RemoveCommand extends BungeeCommand implements TabExecutor {

    private final GroupProvider groupProvider;
    private final DefaultTranslationProvider translationProvider;
    private final UserProvider userProvider;

    public RemoveCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.groupProvider = plugin.getGroupProvider();
        this.translationProvider = plugin.getTranslationProvider();
        this.userProvider = plugin.getUserProvider();
    }


    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 5) {
            commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "commands.permission.user.remove.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }
        final PermissionUser user = this.userProvider.getUser(arguments[1]).orElseThrow();

        switch (arguments[3]) {
            case "group":
                if (this.groupProvider.getGroup(arguments[4].toLowerCase()).isEmpty()) {
                    commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "general.group.not-exists")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%group_name%", arguments[4].toLowerCase())));
                    return;
                }
                final PermissionGroup group = this.groupProvider.getGroup(arguments[4].toLowerCase())
                        .get();

                if (user.getGroup().getIdentification() == group.getIdentification()) {
                    commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "commands.permission.user.remove.group.main-group")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName())
                            .replaceAll("%colored_group_name%", group.getColoredDisplayName())
                            .replaceAll("%group_name%", group.getName())));
                    return;
                }

                if (!user.getSubGroups().contains(group)) {
                    commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "commands.permission.user.remove.group.user-has-group-not")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName())
                            .replaceAll("%colored_group_name%", group.getColoredDisplayName())
                            .replaceAll("%group_name%", group.getName())));
                    return;
                }
                user.getSubGroups().remove(group);
                commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "commands.permission.user.remove.group.successfully-removed")
                        .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                        .replaceAll("%colored_user_name%", user.getColoredName())
                        .replaceAll("%colored_group_name%", group.getColoredDisplayName())
                        .replaceAll("%group_name%", group.getName())));
                break;

            case "permission":
                if (!user.hasPermission(arguments[4])) {
                    commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "commands.permission.user.remove.permission.user-has-permission-not")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName())
                            .replaceAll("%premission%", arguments[4])));
                    return;
                }
                user.getConfiguration().removePermission(arguments[4]);
                user.removePermission(arguments[4]);
                commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "commands.permission.user.remove.permission.successfully-removed")
                        .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                        .replaceAll("%colored_user_name%", user.getColoredName())
                        .replaceAll("%permission%", arguments[4])));
                break;
        }
    }

    @Override
    public final Iterable<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if ((arguments.length < 4) || (arguments.length > 5)) {
            return Collections.emptyList();
        }

        if (arguments.length == 4) {
            return List.of("group", "permission");
        }

        if (this.userProvider.getUser(arguments[1]).isEmpty()) {
            return Collections.emptyList();
        }
        final PermissionUser user = this.userProvider.getUser(arguments[1])
                .get();

        return switch (arguments[3]) {
            case "group" -> user.getSubGroups()
                    .stream()
                    .map(PermissionGroup::getName)
                    .collect(Collectors.toList());
            case "permission" -> user.getAllPermissions();
            default -> Collections.emptyList();
        };
    }

}
