package com.dev7ex.multiperms.command.permission.group;

import com.dev7ex.common.bungeecord.command.BungeeCommand;
import com.dev7ex.common.bungeecord.command.BungeeCommandProperties;
import com.dev7ex.common.util.Numbers;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.group.Group;
import com.dev7ex.multiperms.group.GroupConfiguration;
import com.dev7ex.multiperms.group.GroupProvider;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@BungeeCommandProperties(name = "create", permission = "multiperms.command.permission.group.create")
public class CreateCommand extends BungeeCommand {

    private final GroupConfiguration groupConfiguration;
    private final GroupProvider groupProvider;
    private final DefaultTranslationProvider translationProvider;

    public CreateCommand(@NotNull final MultiPermsPlugin plugin) {
        super(plugin);

        this.groupConfiguration = plugin.getGroupConfiguration();
        this.groupProvider = plugin.getGroupProvider();
        this.translationProvider = plugin.getTranslationProvider();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length != 4) {
            commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "commands.permission.group.create.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }

        if (this.groupProvider.getGroup(arguments[2].toLowerCase()).isPresent()) {
            final PermissionGroup group = this.groupProvider.getGroup(arguments[2].toLowerCase())
                    .get();

            commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "general.group.already-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%group_name%", group.getName())
                    .replaceAll("%colored_group_name%", group.getColoredDisplayName())));
            return;
        }

        if (!Numbers.isInteger(arguments[3])) {
            commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "general.invalid-number")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())));
            return;
        }
        final int groupIdentification = Integer.parseInt(arguments[3]);

        if (this.groupProvider.getGroup(groupIdentification).isPresent()) {
            final PermissionGroup group = this.groupProvider.getGroup(groupIdentification)
                    .get();

            commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "general.group.already-exists-id")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%group_name%", group.getName())
                    .replaceAll("%colored_group_name%", group.getColoredDisplayName())));
            return;
        }
        final String displayName = arguments[2].substring(0, 1).toUpperCase() + arguments[2].substring(1).toLowerCase();
        final PermissionGroup group = Group.builder()
                .setIdentification(groupIdentification)
                .setName(arguments[2].toLowerCase())
                .setPermissions(Collections.emptyList())
                .setDisplayName(displayName)
                .setPriority(0)
                .setColor('7')
                .setChatPrefix("§8[§7" + displayName + "§8]§7")
                .setTablistPrefix("§8[§7" + displayName + "§8] ")
                .build();

        this.groupProvider.register(group);
        this.groupConfiguration.add(group);
        commandSender.sendMessage(new TextComponent(this.translationProvider.getMessage(commandSender, "commands.permission.group.create.successfully-created")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%group_name%", group.getName())
                .replaceAll("%colored_group_name%", group.getColoredDisplayName())));
    }

}