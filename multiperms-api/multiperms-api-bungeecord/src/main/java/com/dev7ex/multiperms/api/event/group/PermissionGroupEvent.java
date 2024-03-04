package com.dev7ex.multiperms.api.event.group;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import lombok.AccessLevel;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.03.2024
 */
@Getter(AccessLevel.PUBLIC)
public class PermissionGroupEvent extends Event {

    private final PermissionGroup group;

    public PermissionGroupEvent(@NotNull final PermissionGroup group) {
        this.group = group;
    }

}
