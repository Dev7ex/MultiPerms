package com.dev7ex.multiperms;

import com.dev7ex.common.io.file.configuration.ConfigurationHolder;
import com.dev7ex.common.io.file.configuration.ConfigurationProperties;
import com.dev7ex.common.io.file.configuration.YamlConfiguration;
import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApiConfiguration;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Locale;

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

        boolean hasChanges = false;

        for (final MultiPermsBukkitApiConfiguration.Entry entry : MultiPermsBukkitApiConfiguration.Entry.values()) {
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
    public String getChatFormat() {
        return super.getString(Entry.SETTINGS_CHAT_FORMAT.getPath());
    }

    @Override
    public boolean isChatEnabled() {
        return super.getBoolean(Entry.SETTINGS_CHAT_ENABLED.getPath());
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
    public boolean isPermissionBasedActionsEnabled() {
        return super.getBoolean(Entry.SETTINGS_PERMISSION_BASED_ACTIONS_ENABLED.getPath());
    }

    @Override
    public boolean isTablistEnabled() {
        return super.getBoolean(Entry.SETTINGS_TABLIST_ENABLED.getPath());
    }

    @Override
    public SimpleDateFormat getTimeFormat() {
        return new SimpleDateFormat(super.getFileConfiguration().getString(Entry.SETTINGS_TIME_FORMAT.getPath()));
    }

}
