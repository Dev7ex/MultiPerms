package com.dev7ex.multiperms.api;

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
     * @return A {@link String} representing the prefix.
     */
    String getPrefix();

    /**
     * Retrieves the format used for chat messages within the system.
     *
     * @return A {@link String} representing the chat format.
     */
    String getChatFormat();

    /**
     * Checks whether the chat functionality is enabled.
     *
     * @return A {@code boolean} value indicating whether chat is enabled.
     */
    boolean isChatEnabled();

    /**
     * Checks whether the tab list functionality is enabled.
     *
     * @return A {@code boolean} value indicating whether the tab list is enabled.
     */
    boolean isTablistEnabled();

    /**
     * Checks whether the basic rights functionality is enabled.
     *
     * @return A {@code boolean} value indicating whether basic rights are enabled.
     */
    boolean isBasicRightsEnabled();

}

