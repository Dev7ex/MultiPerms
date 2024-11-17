package com.dev7ex.multiperms.translation;

import com.dev7ex.common.bungeecord.plugin.BungeePlugin;
import com.dev7ex.common.bungeecord.plugin.module.PluginModule;
import com.dev7ex.common.io.file.Files;
import com.dev7ex.common.io.file.configuration.ConfigurationProvider;
import com.dev7ex.common.io.file.configuration.FileConfiguration;
import com.dev7ex.common.io.file.configuration.JsonConfiguration;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bungeecord.translation.BungeeTranslationProvider;
import lombok.AccessLevel;
import lombok.Getter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Dev7ex
 * @since 24.09.2024
 */
@Getter(AccessLevel.PUBLIC)
public class DefaultTranslationProvider implements PluginModule, BungeeTranslationProvider {

    private final BungeePlugin plugin;
    private final Map<Locale, FileConfiguration> translationConfigurations = new HashMap<>();
    private final Locale defaultLocale;
    private final String defaultMessage = "§cMessage %s not found";

    public DefaultTranslationProvider(@NotNull final MultiPermsPlugin plugin) {
        this.plugin = plugin;
        this.defaultLocale = plugin.getConfiguration().getDefaultLocale();
    }

    @Override
    public void onEnable() {
        this.plugin.saveResource("language/de_DE.json", true);
        this.plugin.saveResource("language/en_US.json", true);

        final File languageFolder = this.plugin.getSubFolder("language");

        for (final File file : Files.getFiles(languageFolder)) {
            if ((file.isFile()) && (file.getName().endsWith(".json"))) {
                final String[] parts = file.getName().replace(".json", "").split("_");

                if (parts.length == 2) {
                    final Locale locale = new Locale(parts[0], parts[1]);

                    try {
                        final FileConfiguration config = ConfigurationProvider.getProvider(JsonConfiguration.class).load(file);
                        this.translationConfigurations.put(locale, config);

                    } catch (final IOException exception) {
                        this.plugin.getLogger().warning(String.format("Failed to load configuration for locale '%s': %s", locale, exception.getMessage()));
                    }

                } else {
                    this.plugin.getLogger().warning("Invalid filename format for locale file: " + file.getName());
                }
            }
        }
        this.plugin.getLogger().info(String.format("Loaded [%d] translations", this.translationConfigurations.size()));
    }

    @Override
    public void onDisable() {
        this.translationConfigurations.clear();
    }

    @Override
    public void register(@NotNull final Locale locale, @NotNull final File file) {
        try {
            final FileConfiguration configuration = ConfigurationProvider.getProvider(JsonConfiguration.class).load(file);
            this.translationConfigurations.put(locale, configuration);

        } catch (final IOException exception) {
            this.plugin.getLogger().warning(String.format("Failed to load translation file for locale '%s' from file '%s': %s",
                    locale, file.getName(), exception.getMessage()));
        }
    }

    @Override
    public void unregister(@NotNull final Locale locale) {
        this.translationConfigurations.remove(locale);
    }

    @Override
    public String getMessage(@NotNull final CommandSender entity, @NotNull final String path) {
        if (entity instanceof ProxiedPlayer player) {
            final Locale locale = player.getLocale();

            if (this.translationConfigurations.containsKey(locale)) {
                return this.translationConfigurations.get(locale).getString(path, String.format(this.defaultMessage, path));
            }
        }
        return this.translationConfigurations.get(this.defaultLocale)
                .getString(path, String.format(this.defaultMessage, path));
    }

    @Override
    public List<String> getMessageList(@NotNull final CommandSender entity, @NotNull final String path) {
        if (entity instanceof ProxiedPlayer player) {
            final Locale locale = player.getLocale();

            if (this.translationConfigurations.containsKey(locale)) {
                final List<String> stringList = this.translationConfigurations.get(this.defaultLocale).getStringList(path);

                if ((stringList == null) || (stringList.isEmpty())) {
                    return List.of(String.format(this.defaultMessage, path));
                }
                return this.translationConfigurations.get(locale).getStringList(path);
            }
        }
        final List<String> stringList = this.translationConfigurations.get(this.defaultLocale).getStringList(path);

        if ((stringList == null) || (stringList.isEmpty())) {
            return List.of(String.format(this.defaultMessage, path));
        }
        return this.translationConfigurations.get(this.defaultLocale).getStringList(path);
    }

}
