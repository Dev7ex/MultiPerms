package com.dev7ex.multiperms.api;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.08.2023
 */
@Getter(AccessLevel.PUBLIC)
public enum MultiPermsApiConfigurationEntry {

    PREFIX("prefix"),
    NO_PERMISSION("no-permission"),
    NO_CONSOLE_COMMAND("no-console-command"),
    NO_PLAYER_COMMAND("no-player-command"),
    NO_PLAYER_FOUND("no-player-found");

    private final String storagePath;

    MultiPermsApiConfigurationEntry(@NotNull final String storagePath) {
        this.storagePath = storagePath;
    }

}
