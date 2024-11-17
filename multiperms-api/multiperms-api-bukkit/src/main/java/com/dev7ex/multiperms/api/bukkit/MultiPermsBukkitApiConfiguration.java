package com.dev7ex.multiperms.api.bukkit;

import com.dev7ex.common.bukkit.plugin.configuration.DefaultPluginConfiguration;
import com.dev7ex.common.io.file.configuration.ConfigurationHolder;
import com.dev7ex.multiperms.api.MultiPermsApiConfiguration;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 10.01.2024
 */
public abstract class MultiPermsBukkitApiConfiguration extends DefaultPluginConfiguration implements MultiPermsApiConfiguration {

    public MultiPermsBukkitApiConfiguration(@NotNull final ConfigurationHolder configurationHolder) {
        super(configurationHolder);
    }

    /**
     * Checks whether the chat functionality is enabled.
     *
     * @return A {@code boolean} value indicating whether chat is enabled.
     *         If {@code true}, the system will manage chat formatting and handling.
     */
    public abstract boolean isChatEnabled();

    /**
     * Retrieves the format used for chat messages within the system.
     *
     * @return A {@link String} representing the chat format.
     *         Placeholders such as {@code %prefix%}, {@code %name%}, and {@code %message%}
     *         may be included for dynamic message construction.
     */
    public abstract String getChatFormat();

    /**
     * Checks if permission-based actions are enabled.
     *
     * @return {@code true} if actions are permission-restricted, {@code false} otherwise.
     */
    public abstract boolean isPermissionBasedActionsEnabled();

    /**
     * Checks whether the tab list functionality is enabled.
     *
     * @return A {@code boolean} value indicating whether the tab list is enabled.
     */
    public abstract boolean isTablistEnabled();

    @Getter(AccessLevel.PUBLIC)
    public enum Entry {

        PREFIX("prefix", "§8[§aMultiPerms§8]§r", false),

        SETTINGS_CHAT_ENABLED("settings.chat.enabled", false, false),
        SETTINGS_CHAT_FORMAT("settings.chat.format", "%prefix% %name% §8» §7%message%", false),
        SETTINGS_DEFAULT_LOCALE("settings.locale.default", "en_US", false),
        SETTINGS_PERMISSION_BASED_ACTIONS_ENABLED("settings.permission-based-actions.enabled", false, false),
        SETTINGS_TABLIST_ENABLED("settings.tablist.enabled", true, false),
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
