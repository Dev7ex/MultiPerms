package com.dev7ex.multiperms.api.event.group;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import net.md_5.bungee.api.plugin.Cancellable;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class PermissionGroupDeleteEvent extends PermissionGroupEvent implements Cancellable {

    private boolean cancelled = false;

    public PermissionGroupDeleteEvent(@NotNull final PermissionGroup group) {
        super(group);
    }

}
