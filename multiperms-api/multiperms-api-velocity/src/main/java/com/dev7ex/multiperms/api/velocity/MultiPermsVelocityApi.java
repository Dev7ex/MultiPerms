package com.dev7ex.multiperms.api.velocity;

import com.dev7ex.common.velocity.command.VelocityCommand;
import com.dev7ex.multiperms.api.MultiPermsApi;
import com.dev7ex.multiperms.api.velocity.hook.VelocityPermissionHookProvider;
import com.dev7ex.multiperms.api.velocity.translation.VelocityTranslationProvider;
import com.dev7ex.multiperms.api.velocity.user.VelocityPermissionUserProvider;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
public interface MultiPermsVelocityApi extends MultiPermsApi {

    /**
     * Retrieves the configuration for the MultiPerms system in Velocity.
     *
     * @return A {@link MultiPermsVelocityApiConfiguration} object containing the plugin's configuration settings.
     *         This includes system settings like permissions, translations, and other Velocity-specific configurations.
     */
    @Override
    @NotNull
    MultiPermsVelocityApiConfiguration getConfiguration();

    /**
     * Retrieves the permission command used within the MultiPerms system on Velocity.
     *
     * @return A {@link VelocityCommand} representing the command used to manage permissions across Velocity servers.
     *         This command typically handles permission-related tasks such as syncing across servers or adjusting player permissions.
     */
    @NotNull
    VelocityCommand getPermissionCommand();

    /**
     * Retrieves the permission hook provider used by the MultiPerms system on Velocity.
     *
     * @return A {@link VelocityPermissionHookProvider} that manages integration with external permission systems
     *         such as PermissionsEx, LuckPerms, or other Velocity-compatible permission systems.
     */
    @Override
    @NotNull
    VelocityPermissionHookProvider getPermissionHookProvider();

    /**
     * Retrieves the translation provider for the MultiPerms system in Velocity.
     *
     * @return A {@link VelocityTranslationProvider} that handles the translation and localization of messages
     *         within the plugin, allowing messages to be displayed in different languages based on the player's locale.
     */
    @Override
    @NotNull
    VelocityTranslationProvider getTranslationProvider();

    /**
     * Retrieves the user provider for the MultiPerms system on Velocity.
     *
     * @return A {@link VelocityPermissionUserProvider} that provides user-related data, including player permissions, ranks,
     *         and other player-specific details for use within the MultiPerms system across Velocity.
     */
    @Override
    @NotNull
    VelocityPermissionUserProvider getUserProvider();

}
