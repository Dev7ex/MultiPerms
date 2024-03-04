package com.dev7ex.multiperms.command.permission.user;

import com.dev7ex.common.bungeecord.command.ProxyCommand;
import com.dev7ex.common.bungeecord.command.ProxyCommandProperties;
import com.dev7ex.common.bungeecord.plugin.ProxyPlugin;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.event.user.PermissionUserGroupSetEvent;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import com.dev7ex.multiperms.api.user.PermissionUser;
import com.dev7ex.multiperms.api.user.PermissionUserProperty;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@ProxyCommandProperties(name = "set", permission = "multiperms.command.permission.user.set")
public class SetCommand extends ProxyCommand implements TabExecutor {

    public SetCommand(@NotNull final ProxyPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        // /permission user <User> set <Group>
        if (arguments.length != 4) {
            commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.user.set.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }
        final PermissionUser user = MultiPermsPlugin.getInstance().getUserProvider().getUser(arguments[1]).orElseThrow();
        final PermissionGroupProvider groupProvider = MultiPermsPlugin.getInstance().getGroupProvider();

        if (groupProvider.getGroup(arguments[3].toLowerCase()).isEmpty()) {
            commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.general.group-not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }
        final PermissionGroup group = groupProvider.getGroup(arguments[3].toLowerCase()).get();

        if (user.getGroup().getName().equalsIgnoreCase(arguments[3])) {
            commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.user.set.user-has-group")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%colored_user_name%", user.getColoredName())
                    .replaceAll("%colored_group_name%", group.getColoredDisplayName())));
            return;
        }

        user.getSubGroups().removeIf(removeIf -> user.getSubGroups().contains(group));

        ProxyServer.getInstance().getPluginManager().callEvent(new PermissionUserGroupSetEvent(user, group));
        user.setGroup(group);
        MultiPermsPlugin.getInstance().getUserProvider().saveUser(user, PermissionUserProperty.GROUP);
        commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.user.set.successfully-set")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%colored_user_name%", user.getColoredName())
                .replaceAll("%colored_group_name%", group.getColoredDisplayName())));
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 4) {
            return Collections.emptyList();
        }
        return MultiPermsPlugin.getInstance().getGroupProvider().getGroups().values().stream().map(PermissionGroup::getName).toList();
    }

}
