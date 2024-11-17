package com.dev7ex.multiperms.api.bukkit;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.multiperms.api.MultiPermsApi;
import com.dev7ex.multiperms.api.bukkit.hook.BukkitPermissionHookProvider;
import com.dev7ex.multiperms.api.bukkit.scoreboard.BukkitScoreboardProvider;
import com.dev7ex.multiperms.api.bukkit.translation.BukkitTranslationProvider;
import com.dev7ex.multiperms.api.bukkit.user.BukkitPermissionUserProvider;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface MultiPermsBukkitApi extends MultiPermsApi {

    /**
     * Retrieves the permission command used within the MultiPerms system.
     *
     * @return A {@link BukkitCommand} that represents the command used to manage permissions.
     *         This command is typically used for permission-related tasks within the plugin.
     */
    @NotNull
    BukkitCommand getPermissionCommand();

    /**
     * Retrieves the configuration for the MultiPerms system.
     *
     * @return A {@link MultiPermsBukkitApiConfiguration} object containing the plugin's configuration settings.
     *         This configuration includes various system settings such as chat, permissions, and more.
     */
    @Override
    @NotNull
    MultiPermsBukkitApiConfiguration getConfiguration();

    /**
     * Retrieves the permission hook provider used by the MultiPerms system.
     *
     * @return A {@link BukkitPermissionHookProvider} that manages integration with external permission systems
     *         such as PermissionsEx, GroupManager, or others.
     */
    @Override
    @NotNull
    BukkitPermissionHookProvider getPermissionHookProvider();

    /**
     * Retrieves the scoreboard provider used by the MultiPerms system.
     *
     * @return A {@link BukkitScoreboardProvider} that provides functionality for managing scoreboards
     *         in the plugin, including creating and updating player scoreboards.
     */
    @NotNull
    BukkitScoreboardProvider getScoreboardProvider();

    /**
     * Retrieves the translation provider for the MultiPerms system.
     *
     * @return A {@link BukkitTranslationProvider} that handles translations and localization of messages
     *         within the plugin, ensuring proper message display based on the player's locale.
     */
    @Override
    @NotNull
    BukkitTranslationProvider getTranslationProvider();

    /**
     * Retrieves the user provider used by the MultiPerms system.
     *
     * @return A {@link BukkitPermissionUserProvider} that provides user-related data, including user permissions,
     *         ranks, and other player-specific details.
     */
    @Override
    @NotNull
    BukkitPermissionUserProvider getUserProvider();


}
