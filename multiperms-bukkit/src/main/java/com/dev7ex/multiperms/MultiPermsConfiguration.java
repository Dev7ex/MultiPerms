package com.dev7ex.multiperms;

import com.dev7ex.common.io.file.configuration.ConfigurationHolder;
import com.dev7ex.common.io.file.configuration.ConfigurationProperties;
import com.dev7ex.common.io.file.configuration.YamlConfiguration;
import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApiConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@ConfigurationProperties(fileName = "config.yml", provider = YamlConfiguration.class)
public class MultiPermsConfiguration extends MultiPermsBukkitApiConfiguration {

    public MultiPermsConfiguration(@NotNull final ConfigurationHolder configurationHolder) {
        super(configurationHolder);
    }

    @Override
    public void load() {
        super.load();

        for (final MultiPermsBukkitApiConfiguration.Entry entry : MultiPermsBukkitApiConfiguration.Entry.values()) {
            if ((entry.isRemoved()) && (super.getFileConfiguration().contains(entry.getPath()))) {
                super.getFileConfiguration().set(entry.getPath(), null);
                MultiPermsPlugin.getInstance().getLogger().info("Remove unnecessary config entry: " + entry.getPath());
            }

            if ((entry.isRemoved()) || (super.getFileConfiguration().contains(entry.getPath()))) {
                continue;
            }

            MultiPermsPlugin.getInstance().getLogger().info("Adding missing config entry: " + entry.getPath());
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
