package com.dev7ex.multiperms;

import com.dev7ex.common.bukkit.configuration.ConfigurationProperties;
import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApiConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@ConfigurationProperties(fileName = "config.yml")
public class MultiPermsConfiguration extends MultiPermsBukkitApiConfiguration {

    public MultiPermsConfiguration(@NotNull final Plugin plugin) {
        super(plugin);
    }

    @Override
    public void load() {
        super.load();

        for (final MultiPermsBukkitApiConfiguration.Entry entry : MultiPermsBukkitApiConfiguration.Entry.values()) {
            if ((entry.isRemoved()) && (super.getFileConfiguration().contains(entry.getPath()))) {
                super.getFileConfiguration().set(entry.getPath(), null);
                super.getPlugin().getLogger().info("Remove unnecessary config entry: " + entry.getPath());
            }

            if ((entry.isRemoved()) || (super.getFileConfiguration().contains(entry.getPath()))) {
                continue;
            }

            super.getPlugin().getLogger().info("Adding missing config entry: " + entry.getPath());
            super.getFileConfiguration().set(entry.getPath(), entry.getDefaultValue());
        }
        super.saveFile();
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

    @Override
    public boolean isBasicRightsEnabled() {
        return super.getBoolean("settings.basic-rights-enabled");
    }

}
