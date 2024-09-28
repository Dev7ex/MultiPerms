package com.dev7ex.multiperms.scoreboard;

import com.dev7ex.common.bukkit.plugin.module.PluginModule;
import com.dev7ex.multiperms.MultiPermsPlugin;
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
public record ScoreboardModule(GroupProvider groupProvider) implements PluginModule {

    @Override
    public void onEnable() {
        for (final Player players : Bukkit.getOnlinePlayers()) {
            final PermissionUser user = MultiPermsPlugin.getInstance().getUserProvider().getUser(players.getUniqueId()).orElseThrow();
            this.updateNameTags(players, user);
        }
    }

    @Override
    public void onDisable() {
    }

    public void initializeScoreboard(@NotNull final Player player) {
        if (!player.getScoreboard().equals(Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard())) {
            return;
        }
        player.setScoreboard(Objects.requireNonNull(player.getServer().getScoreboardManager()).getNewScoreboard());
    }

    public void updateNameTags(@NotNull final Player player, @NotNull final PermissionUser user) {
        this.initializeScoreboard(player);

        for (final Player players : Bukkit.getOnlinePlayers()) {
            final PermissionUser targetUser = MultiPermsPlugin.getInstance().getUserProvider().getUser(players.getUniqueId()).orElseThrow();

            this.initializeScoreboard(players);
            this.addTeamEntry(player, players, user.getGroup());
            this.addTeamEntry(players, player, targetUser.getGroup());
        }
    }

    public void updateNameTagsDelayed(@NotNull final Player player, @NotNull final PermissionUser user, final long delay) {
        Bukkit.getScheduler().runTaskLater(MultiPermsPlugin.getInstance(), () -> this.updateNameTags(player, user), delay);
    }

    public void addTeamEntry(@NotNull final Player entry, @NotNull final Player other, @NotNull final PermissionGroup entryGroup) {
        final Team entryTeam = other.getScoreboard().getTeam(this.getTeamName(entryGroup)) == null ?
                other.getScoreboard().registerNewTeam(this.getTeamName(entryGroup)) :
                other.getScoreboard().getTeam(this.getTeamName(entryGroup));

        entryTeam.setPrefix(entryGroup.getTablistPrefix().replaceAll("&", "§").replaceAll("_", "  "));
        entryTeam.setColor(ChatColor.getByChar(entryGroup.getColor()));
        entryTeam.addEntry(entry.getName());
    }

    public String getTeamName(@NotNull final PermissionGroup group) {
        return String.format("%d%s", group.getPriority(), group.getName());
    }

}
