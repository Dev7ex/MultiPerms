package com.dev7ex.multiperms.api;

import com.dev7ex.common.bungeecord.plugin.ConfigurablePlugin;
import com.dev7ex.common.bungeecord.plugin.configuration.DefaultPluginConfiguration;
import com.dev7ex.common.io.file.configuration.Configuration;
import com.dev7ex.common.io.file.configuration.ConfigurationHolder;
import com.dev7ex.multiperms.api.MultiPermsApiConfiguration;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
public abstract class MultiPermsBungeeApiConfiguration extends DefaultPluginConfiguration implements MultiPermsApiConfiguration {

    public MultiPermsBungeeApiConfiguration(@NotNull final ConfigurablePlugin plugin) {
        super(plugin);
    }

    @Getter(AccessLevel.PUBLIC)
    public enum Entry {

        PREFIX("prefix", "§8[§bMultiPerms§8]§r", false),
        NO_PERMISSION("no-permission",
                "§cIm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that is in error.", false),
        NO_CONSOLE_COMMAND("no-console-command", "%prefix% §cThis command can only performed by the console", false),
        NO_PLAYER_COMMAND("no-player-command", "%prefix% §cThis command can only performed by a player", false),
        NO_PLAYER_FOUND("no-player-found", "%prefix% §cThis player could not be found", false),

        SETTINGS_CHAT_ENABLED("settings.chat-enabled", true, false),
        SETTINGS_CHAT_FORMAT("settings.chat-format", "%prefix% %name% §8» §7%message%", false);

        private final String path;
        private final Object defaultValue;
        private final boolean removed;

        Entry(@NotNull final String path, @NotNull final Object defaultValue, final boolean removed) {
            this.path = path;
            this.defaultValue = defaultValue;
            this.removed = removed;
        }
    }

}
