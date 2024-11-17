package com.dev7ex.multiperms.api.bukkit.scoreboard;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Provides methods for managing and updating a player's scoreboard, including name tags and team entries.
 * Typically used to integrate permissions-based team management into the scoreboard.
 *
 * @author Dev7ex
 * @since 09.11.2024
 */
public interface BukkitScoreboardProvider {

    /**
     * Initializes the scoreboard for the specified player.
     *
     * @param player the player for whom to initialize the scoreboard, not null
     */
    void initializeScoreboard(@NotNull final Player player);

    /**
     * Updates the name tags for the specified player based on their permission user information.
     *
     * @param player the player whose name tags will be updated, not null
     * @param user the permission user containing details used for updating name tags, not null
     */
    void updateNameTags(@NotNull final Player player, @NotNull final PermissionUser user);

    /**
     * Updates the name tags for the specified player after a delay, based on their permission user information.
     *
     * @param player the player whose name tags will be updated, not null
     * @param user the permission user containing details used for updating name tags, not null
     * @param delay the delay in milliseconds before updating the name tags
     */
    void updateNameTagsDelayed(@NotNull final Player player, @NotNull final PermissionUser user, final long delay);

    /**
     * Adds a player to a team entry with another player, based on the specified entry group.
     *
     * @param entry the player being added to the team entry, not null
     * @param other the player with whom the entry player will be associated in the team, not null
     * @param entryGroup the permission group determining the team's attributes, not null
     */
    void addTeamEntry(@NotNull final Player entry, @NotNull final Player other, @NotNull final PermissionGroup entryGroup);

    /**
     * Retrieves the team name associated with the specified permission group.
     *
     * @param group the permission group for which to retrieve the team name, not null
     * @return the name of the team associated with the specified group
     */
    String getFormattedTeamName(@NotNull final PermissionGroup group);
}

