package com.dev7ex.multiperms.command.permission.user;

import com.dev7ex.common.bungeecord.command.ProxyCommand;
import com.dev7ex.common.bungeecord.command.ProxyCommandProperties;
import com.dev7ex.common.bungeecord.plugin.ProxyPlugin;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import com.dev7ex.multiperms.api.user.PermissionUser;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@ProxyCommandProperties(name = "add", permission = "multiperms.command.permission.user.add")
public class AddCommand extends ProxyCommand implements TabExecutor {

    public AddCommand(@NotNull final ProxyPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 5) {
            commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.user.add.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }

        final PermissionGroupProvider groupProvider = MultiPermsPlugin.getInstance().getGroupProvider();
        final PermissionUser user = MultiPermsPlugin.getInstance().getUserProvider().getUser(arguments[1]).orElseThrow();

        switch (arguments[3]) {
            case "group":
                if (groupProvider.getGroup(arguments[4].toLowerCase()).isEmpty()) {
                    commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.general.group-not-exists")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
                    return;
                }
                final PermissionGroup group = groupProvider.getGroup(arguments[4].toLowerCase()).get();

                if (user.getGroup().getIdentification() == group.getIdentification()) {
                    commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.user.add.group.main-group")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName())));
                    return;
                }

                if (user.getSubGroups().contains(group)) {
                    commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.user.add.group.user-has-group")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName())));
                    return;
                }
                user.getSubGroups().add(group);
                commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.user.add.group.successfully-added")
                        .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                        .replaceAll("%colored_user_name%", user.getColoredName())
                        .replaceAll("%colored_group_name%", group.getColoredDisplayName())));
                break;

            case "permission":
                if (user.hasPermission(arguments[4])) {
                    commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.user.add.permission.user-has-permission")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%colored_user_name%", user.getColoredName())
                            .replaceAll("%permission%", arguments[4])));
                    return;
                }
                user.getConfiguration().addPermission(arguments[4]);
                user.addPermission(arguments[4]);
                commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.user.add.permission.successfully-added")
                        .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                        .replaceAll("%colored_user_name%", user.getColoredName())
                        .replaceAll("%permission%", arguments[4])));
                break;

            default:
                commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.user.add.usage")
                        .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
        }
    }

    @Override
    public final Iterable<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length < 4 || arguments.length > 5) {
            return Collections.emptyList();
        }

        if (arguments.length == 4) {
            return List.of("group", "permission");
        }

        switch (arguments[3]) {
            case "group":
                return MultiPermsPlugin.getInstance().getGroupProvider().getGroups().values().stream().map(PermissionGroup::getName).toList();

            case "permission":
                return MultiPermsPlugin.getInstance().getPermissionHookProvider().getAllPermissions();

            default:
                return Collections.emptyList();
        }
    }

}
