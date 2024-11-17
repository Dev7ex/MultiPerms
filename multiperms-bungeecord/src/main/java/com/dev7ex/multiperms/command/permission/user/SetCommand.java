package com.dev7ex.multiperms.command.permission.user;

import com.dev7ex.common.bungeecord.command.BungeeCommand;
import com.dev7ex.common.bungeecord.command.BungeeCommandProperties;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bungeecord.event.user.PermissionUserGroupSetEvent;
import com.dev7ex.multiperms.api.bungeecord.user.BungeePermissionUser;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUser;
import com.dev7ex.multiperms.api.user.PermissionUserProperty;
import com.dev7ex.multiperms.group.GroupProvider;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import com.dev7ex.multiperms.user.UserProvider;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@BungeeCommandProperties(name = "set", permission = "multiperms.command.permission.user.set")
public class SetCommand extends BungeeCommand implements TabExecutor {

    private final GroupProvider groupProvider;
    private final DefaultTranslationProvider translationProvider;
    private final UserProvider userProvider;

    public SetCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.groupProvider = plugin.getGroupProvider();
        this.translationProvider = plugin.getTranslationProvider();
        this.userProvider = plugin.getUserProvider();
    }


    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 4) {
            commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "commands.permission.user.set.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }
        final BungeePermissionUser user = this.userProvider.getUser(arguments[1])
                .orElseThrow();

        if (this.groupProvider.getGroup(arguments[3].toLowerCase()).isEmpty()) {
            commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "general.group.not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%group_name%", arguments[2])));
            return;
        }
        final PermissionGroup group = this.groupProvider.getGroup(arguments[3].toLowerCase())
                .get();

        if (user.getGroup().getName().equalsIgnoreCase(arguments[3])) {
            commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "commands.permission.user.set.user-has-group")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%colored_user_name%", user.getColoredName())
                    .replaceAll("%group_name%", group.getName())
                    .replaceAll("%colored_group_name%", group.getColoredDisplayName())));
            return;
        }
        user.getSubGroups().removeIf(removeIf -> user.getSubGroups().contains(group));

        ProxyServer.getInstance().getPluginManager().callEvent(new PermissionUserGroupSetEvent(user, group));
        user.setGroup(group);
        this.userProvider.saveUser(user, PermissionUserProperty.GROUP);

        commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "commands.permission.user.set.successfully-set")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%colored_user_name%", user.getColoredName())
                .replaceAll("%group_name%", group.getName())
                .replaceAll("%colored_group_name%", group.getColoredDisplayName())));
    }

    @Override
    public final Iterable<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 4) {
            return Collections.emptyList();
        }

        if (this.userProvider.getUser(arguments[1]).isEmpty()) {
            return Collections.emptyList();
        }
        final PermissionUser user = this.userProvider.getUser(arguments[1])
                .get();

        return this.groupProvider.getGroups()
                .values()
                .stream()
                .filter(permissionGroup -> permissionGroup.getIdentification() != user.getGroup().getIdentification())
                .map(PermissionGroup::getName)
                .toList();
    }

}
