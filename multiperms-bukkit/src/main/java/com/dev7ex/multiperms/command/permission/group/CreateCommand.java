package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.common.util.Numbers;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.group.PermissionGroupConfiguration;
import com.dev7ex.multiperms.api.group.PermissionGroupProvider;
import com.dev7ex.multiperms.group.Group;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@BukkitCommandProperties(name = "create", permission = "multiperms.command.permission.group.create")
public class CreateCommand extends BukkitCommand {

    public CreateCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 4) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.create.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        final PermissionGroupProvider groupProvider = MultiPermsPlugin.getInstance().getGroupProvider();
        final PermissionGroupConfiguration groupConfiguration = MultiPermsPlugin.getInstance().getGroupConfiguration();

        if (groupProvider.getGroup(arguments[2].toLowerCase()).isPresent()) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.create.group-already-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }

        if (!Numbers.isInteger(arguments[3])) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.general.invalid-identification")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        final int groupIdentification = Integer.parseInt(arguments[3]);

        if (groupProvider.getGroup(groupIdentification).isPresent()) {
            commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.create.group-already-exists-id")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        
        final PermissionGroup group = Group.builder()
                .setIdentification(groupIdentification)
                .setName(arguments[2].toLowerCase())
                .setPermissions(Collections.emptyList())
                .setDisplayName(arguments[2])
                .setPriority(0)
                .setColor('7')
                .setChatPrefix("§8[§7" + arguments[2] + "§8]§7")
                .setTablistPrefix("§8[§7" + arguments[2] + "§8]")
                .build();

        groupProvider.register(group);
        groupConfiguration.add(group);
        commandSender.sendMessage(super.getConfiguration().getString("messages.commands.permission.group.create.successfully-created")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%colored_group_name%", group.getColoredDisplayName()));
        return;
    }

}
