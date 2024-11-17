package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.common.util.Numbers;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bukkit.event.group.PermissionGroupEditEvent;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupProperty;
import com.dev7ex.multiperms.group.GroupProvider;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dev7ex
 * @since 03.07.2023
 *
 * Permission: multiperms.command.permission.group.edit
 * Usage: /permission group edit <Group> <Property> <Value>
 *
 */
@BukkitCommandProperties(name = "edit", permission = "multiperms.command.permission.group.edit")
public class EditCommand extends BukkitCommand implements BukkitTabCompleter {

    private final GroupProvider groupProvider;
    private final DefaultTranslationProvider translationProvider;

    public EditCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);
        
        this.groupProvider = plugin.getGroupProvider();
        this.translationProvider = plugin.getTranslationProvider();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 5) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.group.edit.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }

        if (this.groupProvider.getGroup(arguments[2].toLowerCase()).isEmpty()) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "general.group.not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%group_name%", arguments[2]));
            return;
        }
        final PermissionGroup group = this.groupProvider.getGroup(arguments[2].toLowerCase())
                .get();

        if (PermissionGroupProperty.fromString(arguments[3].toUpperCase()).isEmpty()) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.group.edit.property-not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%group_property%", arguments[3]));
            return;
        }
        final PermissionGroupProperty property = PermissionGroupProperty.fromString(arguments[3].toUpperCase()).get();
        final PermissionGroupEditEvent event = new PermissionGroupEditEvent(group, property, arguments[4]);
        Bukkit.getPluginManager().callEvent(event);

        if (property == PermissionGroupProperty.PERMISSIONS) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.group.edit.property-locked")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%group_property%", PermissionGroupProperty.PERMISSIONS.toString()));
            return;
        }

        if (property == PermissionGroupProperty.IDENTIFICATION) {
            commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.group.edit.property-locked")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%group_property%", PermissionGroupProperty.IDENTIFICATION.toString()));
            return;
        }

        switch (property) {
            case CHAT_PREFIX -> group.setChatPrefix(arguments[4].replaceAll("&", "§").replace("_", " "));

            case COLOR -> {
                if (arguments[4].length() > 1) {
                    commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.group.edit.invalid-color")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%color_argument%", arguments[4]));
                    return;
                }

                if (ChatColor.getByChar(arguments[4].replaceAll("§", "").charAt(0)) == null) {
                    commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.group.edit.invalid-color")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%color_argument%", arguments[4]));
                    return;
                }
                group.setColor(arguments[4].charAt(0));
            }

            case DISPLAY_NAME -> group.setDisplayName(arguments[4].replaceAll("&", "§").replace("_", " "));

            case IDENTIFICATION -> {
                if (!Numbers.isInteger(arguments[4])) {
                    commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "general.invalid-number")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
                    return;
                }
                final int identification = Integer.parseInt(arguments[4]);

                if (this.groupProvider.getGroup(identification).isPresent()) {
                    commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "general.group.already-exists-id")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                            .replaceAll("%colored_group_name%", group.getColoredDisplayName())
                            .replaceAll("%group_name%", group.getName()));
                    return;
                }
                group.setIdentification(identification);
            }

            case NAME -> {
                if (this.groupProvider.getGroup(arguments[4]).isPresent()) {
                    commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "general.group.already-exists")
                            .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
                    return;
                }
                group.setName(arguments[4].toLowerCase());
            }

            case PRIORITY -> group.setPriority(Integer.parseInt(arguments[4]));

            case TABLIST_PREFIX -> {
                group.setTablistPrefix(arguments[4].replaceAll("&", "§").replace("_", " "));
            }
        }
        commandSender.sendMessage(this.translationProvider.getMessage(commandSender, "commands.permission.group.edit.successfully-edited")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%group_property%", property.toString())
                .replaceAll("%value%", arguments[4])
                .replaceAll("%colored_group_name%", group.getColoredDisplayName())
                .replaceAll("%group_name%", group.getName()));;
        this.groupProvider.saveGroup(group);
    }

    @Override
    public final List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length == 3) {
            return this.groupProvider.getGroups()
                    .values()
                    .stream()
                    .map(PermissionGroup::getName)
                    .toList();
        }

        if (this.groupProvider.getGroup(arguments[2].toLowerCase()).isEmpty()) {
            return Collections.emptyList();
        }

        if (arguments.length == 4) {
            return PermissionGroupProperty.toStringList()
                    .stream()
                    .filter(property -> !property.equals(PermissionGroupProperty.PERMISSIONS.toString()))
                    .filter(property -> !property.equals(PermissionGroupProperty.IDENTIFICATION.toString()))
                    .collect(Collectors.toList());
        }

        if (arguments.length == 5) {
            final PermissionGroup group = this.groupProvider.getGroup(arguments[2].toLowerCase())
                    .get();

            switch (arguments[3]) {
                case "COLOR": {
                    return Arrays.stream(ChatColor.values())
                            .map(ChatColor::getChar)
                            .map(Object::toString)
                            .collect(Collectors.toList());
                }
                case "PRIORITY": {
                    return this.groupProvider.getGroups()
                            .values()
                            .stream()
                            .map(permissionGroup -> permissionGroup.getPriority() + "-" + permissionGroup.getName())
                            .collect(Collectors.toList());
                }
                case "TABLIST_PREFIX":
                    return List.of(
                            "&8[&%color_code%%group_name%&8]_&%color_code%".replaceAll("%group_name%", group.getDisplayName()).replaceAll("%color_code%", String.valueOf(group.getColor()))
                            , "&%color_code%%group_name%&8_|_&%color_code%".replaceAll("%group_name%", group.getDisplayName()).replaceAll("%color_code%", String.valueOf(group.getColor())));

                case "CHAT_PREFIX":
                    return List.of(
                            "&8[&%color_code%%group_name%&8]&%color_code%".replaceAll("%group_name%", group.getDisplayName()).replaceAll("%color_code%", String.valueOf(group.getColor()))
                            , "&%color_code%%group_name%&8_|&%color_code%".replaceAll("%group_name%", group.getDisplayName()).replaceAll("%color_code%", String.valueOf(group.getColor())));

            }
        }
        return Collections.emptyList();
    }

}
