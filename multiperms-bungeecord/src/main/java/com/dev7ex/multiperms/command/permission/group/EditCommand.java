package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bungeecord.command.ProxyCommand;
import com.dev7ex.common.bungeecord.command.ProxyCommandProperties;
import com.dev7ex.common.bungeecord.plugin.ProxyPlugin;
import com.dev7ex.common.collect.map.ParsedMap;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.event.group.PermissionGroupEditEvent;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroupProperty;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@ProxyCommandProperties(name = "edit", permission = "multiperms.command.permission.group.edit")
public class EditCommand extends ProxyCommand implements TabExecutor {

    public EditCommand(@NotNull final ProxyPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 5) {
            commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.group.edit.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }
        final PermissionGroupProvider groupProvider = MultiPermsPlugin.getInstance().getGroupProvider();
        final PermissionGroupConfiguration groupConfiguration = MultiPermsPlugin.getInstance().getGroupConfiguration();

        if (groupProvider.getGroup(arguments[2].toLowerCase()).isEmpty()) {
            commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.general.group-not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }
        final PermissionGroup permissionGroup = groupProvider.getGroup(arguments[2].toLowerCase()).get();
        final Optional<PermissionGroupProperty> propertyOptional = PermissionGroupProperty.fromString(arguments[3].toUpperCase());
        final ParsedMap<PermissionGroupProperty, Object> groupData = new ParsedMap<>();

        if (propertyOptional.isEmpty()) {
            commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.group.edit.property-not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }
        final PermissionGroupProperty property = propertyOptional.get();

        final PermissionGroupEditEvent event = new PermissionGroupEditEvent(permissionGroup, property, arguments[4]);
        ProxyServer.getInstance().getPluginManager().callEvent(event);

        if (property == PermissionGroupProperty.PERMISSIONS) {
            commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.general.function-not-available")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }

        switch (property) {
            case DISPLAY_NAME -> {
                permissionGroup.setDisplayName(arguments[4]);
                groupData.put(PermissionGroupProperty.DISPLAY_NAME, arguments[4]);
                break;
            }
            case TABLIST_PREFIX -> {
                permissionGroup.setTablistPrefix(arguments[4]);
                groupData.put(PermissionGroupProperty.TABLIST_PREFIX, arguments[4]);
                break;
            }
            case COLOR -> {
                if (ChatColor.getByChar(arguments[4].replaceAll("§", "").charAt(0)) == null) {
                    commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.group.edit.invalid-color")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
                    return;
                }
                permissionGroup.setColor(arguments[4].charAt(0));
                groupData.put(PermissionGroupProperty.COLOR, arguments[4].charAt(0));
                break;
            }
            case PRIORITY -> {
                permissionGroup.setPriority(Integer.parseInt(arguments[4]));
                groupData.put(PermissionGroupProperty.PRIORITY, arguments[4]);
                break;
            }
            case CHAT_PREFIX -> {
                permissionGroup.setChatPrefix(arguments[4]);
                groupData.put(PermissionGroupProperty.CHAT_PREFIX, arguments[4]);
                break;
            }
        }
        commandSender.sendMessage(new TextComponent(super.getConfiguration().getString("messages.commands.permission.group.edit.successfully-edited")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%group_property%", property.toString())
                .replaceAll("%value%", arguments[4])));
        groupProvider.saveGroup(permissionGroup);
    }

    @Override
    public final Iterable<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length == 3) {
            return MultiPermsPlugin.getInstance().getGroupProvider().getGroups().values().stream().map(PermissionGroup::getName).toList();
        }

        if (arguments.length == 4) {
            return PermissionGroupProperty.toStringList();
        }

        if ((arguments.length == 5) && (arguments[3].equalsIgnoreCase(PermissionGroupProperty.COLOR.toString()))) {
            return Arrays.stream(ChatColor.values()).map(ChatColor::getName).toList();
        }
        return Collections.emptyList();
    }

}
