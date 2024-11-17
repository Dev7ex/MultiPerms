package com.dev7ex.multiperms;

import com.dev7ex.common.bungeecord.plugin.BungeePlugin;
import com.dev7ex.common.bungeecord.plugin.ConfigurablePlugin;
import com.dev7ex.common.bungeecord.plugin.PluginIdentification;
import com.dev7ex.common.bungeecord.plugin.statistic.PluginStatisticProperties;
import com.dev7ex.multiperms.api.MultiPermsApiProvider;
import com.dev7ex.multiperms.api.bungeecord.MultiPermsBungeeApi;
import com.dev7ex.multiperms.command.PermissionCommand;
import com.dev7ex.multiperms.group.GroupConfiguration;
import com.dev7ex.multiperms.group.GroupProvider;
import com.dev7ex.multiperms.hook.DefaultPermissionHookProvider;
import com.dev7ex.multiperms.listener.group.PermissionCheckListener;
import com.dev7ex.multiperms.listener.player.PlayerConnectionListener;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import com.dev7ex.multiperms.user.UserProvider;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.File;

/**
 * @author Dev7ex
 * @since 03.03.2024
 *
 * Permission Management for Minecraft
 *
 */
@Getter(AccessLevel.PUBLIC)
@PluginStatisticProperties(enabled = true, identification = 23032)
@PluginIdentification(spigotResourceId = 111992)
public class MultiPermsPlugin extends BungeePlugin implements ConfigurablePlugin, MultiPermsBungeeApi {

    private MultiPermsConfiguration configuration;
    private GroupConfiguration groupConfiguration;

    private PermissionCommand permissionCommand;

    private UserProvider userProvider;
    private GroupProvider groupProvider;
    private DefaultPermissionHookProvider permissionHookProvider;
    private DefaultTranslationProvider translationProvider;

    @Override
    public void onLoad() {
        super.createDataFolder();
        super.createSubFolder("language");
        super.createSubFolder("user");

        this.configuration = new MultiPermsConfiguration(this);
        this.configuration.load();

        this.groupConfiguration = new GroupConfiguration(this);
        this.groupConfiguration.createFile();
        this.groupConfiguration.load();
    }

    @Override
    public void onEnable() {
        MultiPermsApiProvider.registerApi(this);

        super.onEnable();
    }

    @Override
    public void onDisable() {
        MultiPermsApiProvider.unregisterApi();

        super.onDisable();
    }

    @Override
    public void registerCommands() {
        super.registerCommand(this.permissionCommand = new PermissionCommand(this));
    }

    @Override
    public void registerListeners() {
        super.registerListener(new PermissionCheckListener(this));
        super.registerListener(new PlayerConnectionListener(this));
    }

    @Override
    public void registerModules() {
        super.registerModule(this.groupProvider = new GroupProvider(this.groupConfiguration));
        super.registerModule(this.userProvider = new UserProvider(this.configuration, this.groupProvider));
        super.registerModule(this.permissionHookProvider = new DefaultPermissionHookProvider());
        super.registerModule(this.translationProvider = new DefaultTranslationProvider(this));
    }

    @Override
    public File getUserFolder() {
        return super.getSubFolder("user");
    }

    public static MultiPermsPlugin getInstance() {
        return BungeePlugin.getPlugin(MultiPermsPlugin.class);
    }

}
