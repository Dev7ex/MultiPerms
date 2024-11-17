package com.dev7ex.multiperms.api.bungeecord;

import com.dev7ex.common.bungeecord.command.BungeeCommand;
import com.dev7ex.multiperms.api.MultiPermsApi;
import com.dev7ex.multiperms.api.bungeecord.hook.BungeePermissionHookProvider;
import com.dev7ex.multiperms.api.bungeecord.translation.BungeeTranslationProvider;
import com.dev7ex.multiperms.api.bungeecord.user.BungeePermissionUserProvider;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
public interface MultiPermsBungeeApi extends MultiPermsApi {

    /**
     * Retrieves the configuration for the MultiPerms system in BungeeCord.
     *
     * @return A {@link MultiPermsBungeeApiConfiguration} object containing the plugin's configuration settings.
     *         This includes system settings like permissions, translations, and other BungeeCord-specific configurations.
     */
    @Override
    @NotNull
    MultiPermsBungeeApiConfiguration getConfiguration();

    /**
     * Retrieves the permission command used within the MultiPerms system on BungeeCord.
     *
     * @return A {@link BungeeCommand} representing the command used to manage permissions across BungeeCord servers.
     *         This command typically handles permission-related tasks such as syncing across servers or adjusting player permissions.
     */
    @NotNull
    BungeeCommand getPermissionCommand();

    /**
     * Retrieves the permission hook provider used by the MultiPerms system on BungeeCord.
     *
     * @return A {@link BungeePermissionHookProvider} that manages integration with external permission systems
     *         such as PermissionsEx, LuckPerms, or other BungeeCord-compatible permission systems.
     */
    @Override
    @NotNull
    BungeePermissionHookProvider getPermissionHookProvider();

    /**
     * Retrieves the translation provider for the MultiPerms system in BungeeCord.
     *
     * @return A {@link BungeeTranslationProvider} that handles the translation and localization of messages
     *         within the plugin, allowing messages to be displayed in different languages based on the player's locale.
     */
    @Override
    @NotNull
    BungeeTranslationProvider getTranslationProvider();

    /**
     * Retrieves the user provider for the MultiPerms system on BungeeCord.
     *
     * @return A {@link BungeePermissionUserProvider} that provides user-related data, including player permissions, ranks,
     *         and other player-specific details for use within the MultiPerms system across BungeeCord.
     */
    @Override
    @NotNull
    BungeePermissionUserProvider getUserProvider();

}
