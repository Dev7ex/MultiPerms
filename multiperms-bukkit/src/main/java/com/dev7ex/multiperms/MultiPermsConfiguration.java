package com.dev7ex.multiperms;

import com.dev7ex.common.bukkit.configuration.ConfigurationProperties;
import com.dev7ex.common.bukkit.plugin.configuration.DefaultPluginConfiguration;
import com.dev7ex.multiperms.api.MultiPermsApiConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@ConfigurationProperties(fileName = "config.yml")
public class MultiPermsConfiguration extends DefaultPluginConfiguration implements MultiPermsApiConfiguration {

    public MultiPermsConfiguration(@NotNull final Plugin plugin) {
        super(plugin);
    }

    @Override
    public String getChatFormat() {
        return super.getString("settings.chat-format");
    }

    @Override
    public boolean isChatEnabled() {
        return super.getBoolean("settings.chat-enabled");
    }

    @Override
    public boolean isTablistEnabled() {
        return super.getBoolean("settings.tablist-enabled");
    }

}
