package com.dev7ex.multiperms.scoreboard;

import com.dev7ex.common.bukkit.plugin.module.PluginModule;
import com.dev7ex.multiperms.MultiPermsPlugin;
import com.dev7ex.multiperms.api.bukkit.scoreboard.BukkitScoreboardProvider;
import com.dev7ex.multiperms.api.group.PermissionGroup;
import com.dev7ex.multiperms.api.user.PermissionUser;
import com.dev7ex.multiperms.group.GroupProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public record ScoreboardProvider(GroupProvider groupProvider) implements PluginModule, BukkitScoreboardProvider {

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Override
    public void initializeScoreboard(@NotNull final Player player) {
        if (!player.getScoreboard().equals(Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard())) {
            return;
        }
        player.setScoreboard(Objects.requireNonNull(player.getServer().getScoreboardManager()).getNewScoreboard());
    }

    @Override
    public void updateNameTags(@NotNull final Player player, @NotNull final PermissionUser user) {
        this.initializeScoreboard(player);

        for (final Player targetPlayer : Bukkit.getOnlinePlayers()) {
            final PermissionUser targetUser = MultiPermsPlugin.getInstance()
                    .getUserProvider()
                    .getUser(targetPlayer.getUniqueId())
                    .orElseThrow();

            this.initializeScoreboard(targetPlayer);
            this.addTeamEntry(player, targetPlayer, user.getGroup());
            this.addTeamEntry(targetPlayer, player, targetUser.getGroup());
        }
    }


    @Override
    public void updateNameTagsDelayed(@NotNull final Player player, @NotNull final PermissionUser user, final long delay) {
        Bukkit.getScheduler()
                .runTaskLater(MultiPermsPlugin.getInstance(), () -> this.updateNameTags(player, user), delay);
    }

    @Override
    public void addTeamEntry(@NotNull final Player entry, @NotNull final Player other, @NotNull final PermissionGroup entryGroup) {
        final String formattedTeamName = this.getFormattedTeamName(entryGroup);
        final Team entryTeam = other.getScoreboard().getTeam(formattedTeamName) == null ?
                other.getScoreboard().registerNewTeam(formattedTeamName) :
                other.getScoreboard().getTeam(formattedTeamName);

        final String tablistPrefix = (entryGroup.getTablistPrefix() != null) ? entryGroup.getTablistPrefix() : "Error";
        System.out.println(entryGroup.getColor() + " t");
        final ChatColor teamColor = ChatColor.getByChar(entryGroup.getColor());

        if (teamColor != null) {
            Objects.requireNonNull(entryTeam).setColor(teamColor);

        } else {
            Objects.requireNonNull(entryTeam).setColor(ChatColor.WHITE); // Default color if the provided color is invalid
        }
        entryTeam.setPrefix(tablistPrefix.replace("&", "§").replace("_", "  "));
        entryTeam.addEntry(entry.getName());
    }

    @Override
    public String getFormattedTeamName(@NotNull final PermissionGroup group) {
        return String.format("%d%s", group.getPriority(), group.getName());
    }

}
