package com.dev7ex.multiperms.command.permission.user;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.multiperms.MultiPermsConfiguration;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bukkit.event.user.PermissionUserGroupSetEvent;
import com.dev7ex.multiperms.api.bukkit.user.BukkitPermissionUser;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUser;
import com.dev7ex.multiperms.api.user.PermissionUserProperty;
import com.dev7ex.multiperms.group.GroupProvider;
import com.dev7ex.multiperms.scoreboard.ScoreboardProvider;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import com.dev7ex.multiperms.user.UserProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Dev7ex
 * @since 03.07.2023
 *
 * Permission: multiperms.command.permission.user.set
 * Usage: /permission group user <User> set <Group>
 *
 */
@BukkitCommandProperties(name = "set", permission = "multiperms.command.permission.user.set")
public class SetCommand extends BukkitCommand implements BukkitTabCompleter {

    private final MultiPermsConfiguration configuration;
    private final GroupProvider groupProvider;
    private final ScoreboardProvider scoreboardProvider;
    private final DefaultTranslationProvider translationProvider;
    private final UserProvider userProvider;

    public SetCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.configuration = plugin.getConfiguration();
        this.groupProvider = plugin.getGroupProvider();
        this.scoreboardProvider = plugin.getScoreboardProvider();
        this.translationProvider = plugin.getTranslationProvider();
        this.userProvider = plugin.getUserProvider();
    }


    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 4) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.user.set.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        final BukkitPermissionUser user = this.userProvider.getUser(arguments[1])
                .orElseThrow();

        if (this.groupProvider.getGroup(arguments[3].toLowerCase()).isEmpty()) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "general.group.not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%group_name%", arguments[2]));
            return;
        }
        final PermissionGroup group = this.groupProvider.getGroup(arguments[3].toLowerCase())
                .get();

        if (user.getGroup().getName().equalsIgnoreCase(arguments[3])) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.user.set.user-has-group")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%colored_user_name%", user.getColoredName())
                    .replaceAll("%group_name%", group.getName())
                    .replaceAll("%colored_group_name%", group.getColoredDisplayName()));
            return;
        }
        user.getSubGroups().removeIf(removeIf -> user.getSubGroups().contains(group));

        Bukkit.getPluginManager().callEvent(new PermissionUserGroupSetEvent(user, group));
        user.setGroup(group);
        this.userProvider.saveUser(user, PermissionUserProperty.GROUP);

        if (this.configuration.isTablistEnabled()) {
            this.scoreboardProvider.updateNameTags(Objects.requireNonNull(Bukkit.getPlayer(user.getUniqueId())), user);
        }
        commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.user.set.successfully-set")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%colored_user_name%", user.getColoredName())
                .replaceAll("%group_name%", group.getName())
                .replaceAll("%colored_group_name%", group.getColoredDisplayName()));
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
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
