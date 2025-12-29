package com.dev7ex.multiperms.api.event.group;

import com.dev7ex.multiperms.api.event.PermissionEvent;
import com.dev7ex.multiperms.api.event.PermissionEventCancellable;
import com.dev7ex.multiperms.api.group.PermissionGroup;

public abstract class PermissionGroupEvent implements PermissionEvent, PermissionEventCancellable {

    private final PermissionGroup group;

    public PermissionGroupEvent(final PermissionGroup group) {
        this.group = group;
    }

}
