package com.dev7ex.multiperms.listener.group;

import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApi;
import com.dev7ex.multiperms.api.bukkit.event.MultiPermsListener;
import com.dev7ex.multiperms.api.bukkit.event.group.PermissionGroupEditEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 09.11.2024
 */
public class PermissionGroupEditListener extends MultiPermsListener {

    /**
     * Constructs a new listener with access to the MultiPerms API.
     *
     * @param multiPermsApi The {@link MultiPermsBukkitApi} instance to interact with the MultiPerms system.
     */
    public PermissionGroupEditListener(@NotNull final MultiPermsBukkitApi multiPermsApi) {
        super(multiPermsApi);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleGroupEdit(final PermissionGroupEditEvent event) {
        super.getUserProvider().getUsers().values().forEach(user ->
                super.getScoreboardProvider().updateNameTagsDelayed(user.getEntity(), user, 5L));
    }

}
