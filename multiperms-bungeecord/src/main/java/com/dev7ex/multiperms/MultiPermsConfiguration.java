package com.dev7ex.multiperms;

import com.dev7ex.common.bungeecord.plugin.ConfigurablePlugin;
import com.dev7ex.common.io.file.configuration.ConfigurationProperties;
import com.dev7ex.common.io.file.configuration.YamlConfiguration;
import com.dev7ex.multiperms.api.bungeecord.MultiPermsBungeeApiConfiguration;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Locale;

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

        boolean hasChanges = false;

        for (final MultiPermsBungeeApiConfiguration.Entry entry : MultiPermsBungeeApiConfiguration.Entry.values()) {
            if ((entry.isRemoved()) && (super.getFileConfiguration().contains(entry.getPath()))) {
                super.getFileConfiguration().set(entry.getPath(), null);
                MultiPermsPlugin.getInstance().getLogger().info("Remove unnecessary config entry: " + entry.getPath());
                hasChanges = true;
            }

            if ((entry.isRemoved()) || (super.getFileConfiguration().contains(entry.getPath()))) {
                continue;
            }

            MultiPermsPlugin.getInstance().getLogger().info("Adding missing config entry: " + entry.getPath());
            super.getFileConfiguration().set(entry.getPath(), entry.getDefaultValue());
            hasChanges = true;
        }

        if (hasChanges) {
            super.saveFile();
        }
    }

    @Override
    public Locale getDefaultLocale() {
        final String[] parts = super.getFileConfiguration().getString(Entry.SETTINGS_DEFAULT_LOCALE.getPath()).split("_");

        if (parts.length != 2) {
            return new Locale("en", "us");
        }
        return new Locale(parts[0], parts[1]);
    }

    @Override
    public SimpleDateFormat getTimeFormat() {
        return new SimpleDateFormat(super.getFileConfiguration().getString(Entry.SETTINGS_TIME_FORMAT.getPath()));
    }

}
