package com.dev7ex.multiperms.api.bungeecord;

import com.dev7ex.common.bungeecord.plugin.ConfigurablePlugin;
import com.dev7ex.common.bungeecord.plugin.configuration.DefaultPluginConfiguration;
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

        PREFIX("prefix", "§8[§aMultiPerms§8]§r", false),

        SETTINGS_DEFAULT_LOCALE("settings.locale.default", "en_US", false),
        SETTINGS_TIME_FORMAT("settings.time-format", "dd.MM.yyyy HH:mm:ss", false);

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
