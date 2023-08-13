package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.CommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.common.map.ParsedMap;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bukkit.event.group.PermissionGroupEditEvent;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroupProperty;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@CommandProperties(name = "edit", permission = "multiperms.command.permission.group.edit")
public class EditCommand extends BukkitCommand implements TabCompleter {

    public EditCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 5) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.edit.usage")
                    .replaceAll("%prefix%", super.getPrefix()));
            return true;
        }
        final PermissionGroupProvider groupProvider = MultiPermsPlugin.getInstance().getGroupProvider();
        final PermissionGroupConfiguration groupConfiguration = MultiPermsPlugin.getInstance().getGroupConfiguration();

        if (groupProvider.getGroup(arguments[2].toLowerCase()).isEmpty()) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.general.group-not-exists")
                    .replaceAll("%prefix%", super.getPrefix()));
            return true;
        }
        final PermissionGroup permissionGroup = groupProvider.getGroup(arguments[2].toLowerCase()).get();
        final Optional<PermissionGroupProperty> propertyOptional = PermissionGroupProperty.fromString(arguments[3].toUpperCase());
        final ParsedMap<PermissionGroupProperty, Object> groupData = new ParsedMap<>();

        if (propertyOptional.isEmpty()) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.edit.property-not-exists")
                    .replaceAll("%prefix%", super.getPrefix()));
            return true;
        }
        final PermissionGroupProperty property = propertyOptional.get();

        final PermissionGroupEditEvent event = new PermissionGroupEditEvent(permissionGroup, property, arguments[4]);
        Bukkit.getPluginManager().callEvent(event);

        if (property == PermissionGroupProperty.PERMISSIONS) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.general.function-not-available")
                    .replaceAll("%prefix%", super.getPrefix()));
            return true;
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
                    commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.edit.invalid-color")
                            .replaceAll("%prefix%", super.getPrefix()));
                    return true;
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
        commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.edit.successfully-edited")
                .replaceAll("%prefix%", super.getPrefix())
                .replaceAll("%group_property%", property.toString())
                .replaceAll("%value%", arguments[4]));
        groupProvider.saveGroup(permissionGroup);
        return true;
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final Command command,
                                            @NotNull final String commandLabel, @NotNull final String[] arguments) {
        if (arguments.length == 3) {
            return MultiPermsPlugin.getInstance().getGroupProvider().getGroups().values().stream().map(PermissionGroup::getName).toList();
        }

        if (arguments.length == 4) {
            return PermissionGroupProperty.toStringList();
        }

        if ((arguments.length == 5) && (arguments[3].equalsIgnoreCase(PermissionGroupProperty.COLOR.toString()))) {
            return Arrays.stream(ChatColor.values()).map(ChatColor::getChar).map(Object::toString).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
