package com.dev7ex.multiperms.api.bukkit;

import com.dev7ex.multiperms.api.MultiPermsApi;
import com.dev7ex.multiperms.api.bukkit.translation.BukkitTranslationProvider;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface MultiPermsBukkitApi extends MultiPermsApi {

    @Override
    MultiPermsBukkitApiConfiguration getConfiguration();

    @Override
    BukkitTranslationProvider getTranslationProvider();

}
