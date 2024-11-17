package com.dev7ex.multiperms.api;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Provides the configuration settings for the MultiPerms API.
 * This interface defines various options related to the system's
 * prefixes, chat format, and feature toggles.
 *
 * @author Dev7ex
 * @since 07.03.2023
 */
public interface MultiPermsApiConfiguration {

    /**
     * Retrieves the prefix used within the MultiPerms system.
     *
     * @return A {@link String} representing the prefix, typically displayed in chat or log messages.
     */
    String getPrefix();

    /**
     * Retrieves the default locale used by the MultiPerms system.
     *
     * @return A {@link Locale} object representing the system's default locale
     *         for messages and other localized content.
     */
    Locale getDefaultLocale();

    /**
     * Retrieves the time format used within the MultiPerms system.
     *
     * @return A {@link SimpleDateFormat} representing the time format for
     *         displaying date and time information.
     */
    SimpleDateFormat getTimeFormat();

}

