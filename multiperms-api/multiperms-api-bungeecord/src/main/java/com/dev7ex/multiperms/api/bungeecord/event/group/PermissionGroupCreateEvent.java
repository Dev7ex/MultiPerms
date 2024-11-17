package com.dev7ex.multiperms.api.bungeecord.event.group;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.plugin.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class PermissionGroupCreateEvent extends PermissionGroupEvent implements Cancellable {

    private boolean cancelled = false;

    public PermissionGroupCreateEvent(@NotNull final PermissionGroup group) {
        super(group);
    }

}
