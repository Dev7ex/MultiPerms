package com.dev7ex.multiperms;

import com.dev7ex.common.bungeecord.plugin.ConfigurablePlugin;
import com.dev7ex.common.io.file.configuration.ConfigurationProperties;
import com.dev7ex.common.io.file.configuration.YamlConfiguration;
import com.dev7ex.multiperms.api.MultiPermsBungeeApiConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@ConfigurationProperties(fileName = "config.yml", provider = YamlConfiguration.class)
public class MultiPermsConfiguration extends MultiPermsBungeeApiConfiguration {

    public MultiPermsConfiguration(@NotNull final ConfigurablePlugin plugin) {
        super(plugin);
    }

    @Override
    public void load() {
        super.load();

        for (final MultiPermsBungeeApiConfiguration.Entry entry : MultiPermsBungeeApiConfiguration.Entry.values()) {
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
        return null;
    }

    @Override
    public boolean isChatEnabled() {
        return false;
    }

    @Override
    public boolean isTablistEnabled() {
        return false;
    }

    @Override
    public boolean isBasicRightsEnabled() {
        return false;
    }

}
