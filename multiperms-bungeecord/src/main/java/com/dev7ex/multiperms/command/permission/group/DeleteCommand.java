package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bungeecord.command.ProxyCommand;
import com.dev7ex.common.bungeecord.command.ProxyCommandProperties;
import com.dev7ex.common.bungeecord.plugin.ProxyPlugin;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.event.group.PermissionGroupDeleteEvent;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
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
@ProxyCommandProperties(name = "delete", permission = "multiperms.command.permission.group.delete")
public class DeleteCommand extends ProxyCommand implements TabExecutor {

    public DeleteCommand(@NotNull final ProxyPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 3) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.create.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        final PermissionGroupProvider groupProvider = MultiPermsPlugin.getInstance().getGroupProvider();
        final PermissionGroupConfiguration groupConfiguration = MultiPermsPlugin.getInstance().getGroupConfiguration();

        if (groupProvider.getGroup(arguments[2].toLowerCase()).isEmpty()) {
            commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.general.group-not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }
        final PermissionGroup group = groupProvider.getGroup(arguments[2].toLowerCase()).get();
        final PermissionGroupDeleteEvent event = new PermissionGroupDeleteEvent(group);

        ProxyServer.getInstance().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }
        groupProvider.unregister(group.getIdentification());
        groupConfiguration.remove(group.getIdentification());
        commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.group.delete.successfully-deleted")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%colored_group_name%", group.getColoredDisplayName())));
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 3) {
            return Collections.emptyList();
        }
        return MultiPermsPlugin.getInstance().getGroupProvider().getGroups().values().stream().map(PermissionGroup::getName).toList();
    }

}

