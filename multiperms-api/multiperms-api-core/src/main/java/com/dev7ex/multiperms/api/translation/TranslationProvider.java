package com.dev7ex.multiperms.api.translation;

import com.dev7ex.common.io.file.configuration.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Dev7ex
 * @since 24.09.2024
 *
 * Provides methods for managing translations and localized messages for entities.
 *
 * @param <E> The type of the entity for which translations are provided.
 */
public interface TranslationProvider<E> {

    /**
     * Gets the translation configurations for all available locales.
     *
     * @return A map of {@link Locale} to {@link FileConfiguration}.
     */
    Map<Locale, FileConfiguration> getTranslationConfigurations();

    /**
     * Registers a translation file for a specific locale.
     *
     * @param locale The locale to register.
     * @param file The translation {@link File} to register.
     */
    void register(@NotNull final Locale locale, @NotNull final File file);

    /**
     * Unregisters the translation for a specific locale.
     *
     * @param locale The locale to unregister.
     */
    void unregister(@NotNull final Locale locale);

    /**
     * Gets the default locale used for translations.
     *
     * @return The default {@link Locale}.
     */
    Locale getDefaultLocale();

    /**
     * Retrieves a translated message for a given entity and path.
     *
     * @param entity The entity for which the message is retrieved.
     * @param path The message path.
     * @return The translated message.
     */
    String getMessage(@NotNull final E entity, @NotNull final String path);

    /**
     * Retrieves a list of translated messages for a given entity and path.
     *
     * @param entity The entity for which the message list is retrieved.
     * @param path The message path.
     * @return A list of translated messages.
     */
    List<String> getMessageList(@NotNull final E entity, @NotNull final String path);
}

