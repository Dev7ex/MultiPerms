package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bungeecord.command.BungeeCommand;
import com.dev7ex.common.bungeecord.command.BungeeCommandProperties;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bungeecord.event.group.PermissionGroupDeleteEvent;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.group.GroupConfiguration;
import com.dev7ex.multiperms.group.GroupProvider;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
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
@BungeeCommandProperties(name = "delete", permission = "multiperms.command.permission.group.delete")
public class DeleteCommand extends BungeeCommand implements TabExecutor {

    private final GroupConfiguration groupConfiguration;
    private final GroupProvider groupProvider;
    private final DefaultTranslationProvider translationProvider;

    public DeleteCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.groupConfiguration = plugin.getGroupConfiguration();
        this.groupProvider = plugin.getGroupProvider();
        this.translationProvider = plugin.getTranslationProvider();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 3) {
            commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "commands.permission.group.delete.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }

        if (this.groupProvider.getGroup(arguments[2].toLowerCase()).isEmpty()) {
            commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "general.group.not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%group_name%", arguments[2])));
            return;
        }
        final PermissionGroup group = this.groupProvider.getGroup(arguments[2].toLowerCase())
                .get();

        if (group.getIdentification() == 0) {
            commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "general.group.delete-locked")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%group_name%", group.getName())
                    .replaceAll("%colored_group_name%", group.getColoredDisplayName())));
            return;
        }
        final PermissionGroupDeleteEvent event = new PermissionGroupDeleteEvent(group);

        ProxyServer.getInstance().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }
        this.groupProvider.unregister(group.getIdentification());
        this.groupConfiguration.remove(group.getIdentification());
        commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "commands.permission.group.delete.successfully-deleted")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%group_name%", group.getName())
                .replaceAll("%colored_group_name%", group.getColoredDisplayName())));
    }

    @Override
    public final Iterable<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 3) {
            return Collections.emptyList();
        }
        return this.groupProvider.getGroups()
                .values()
                .stream()
                .filter(group -> group.getIdentification() != 0)
                .map(PermissionGroup::getName)
                .toList();
    }

}

