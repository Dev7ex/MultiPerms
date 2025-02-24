package com.dev7ex.multiperms;

import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.common.bukkit.plugin.ConfigurablePlugin;
import com.dev7ex.common.bukkit.plugin.PluginIdentification;
import com.dev7ex.common.bukkit.plugin.statistic.PluginStatisticProperties;
import com.dev7ex.multiperms.api.MultiPermsApiProvider;
import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApi;
import com.dev7ex.multiperms.command.PermissionCommand;
import com.dev7ex.multiperms.group.GroupConfiguration;
import com.dev7ex.multiperms.group.GroupProvider;
import com.dev7ex.multiperms.hook.DefaultPermissionHookProvider;
import com.dev7ex.multiperms.listener.group.PermissionGroupDeleteListener;
import com.dev7ex.multiperms.listener.group.PermissionGroupEditListener;
import com.dev7ex.multiperms.listener.player.PlayerChatListener;
import com.dev7ex.multiperms.listener.player.PlayerConnectionListener;
import com.dev7ex.multiperms.listener.player.PlayerPermissionCheckListener;
import com.dev7ex.multiperms.listener.user.UserConnectionListener;
import com.dev7ex.multiperms.scoreboard.ScoreboardProvider;
import com.dev7ex.multiperms.translation.DefaultTranslationProvider;
import com.dev7ex.multiperms.user.UserProvider;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
@PluginIdentification(spigotResourceId = 111992)
@PluginStatisticProperties(enabled = true, identification = 23031)
public class MultiPermsPlugin extends BukkitPlugin implements MultiPermsBukkitApi, ConfigurablePlugin {

    private MultiPermsConfiguration configuration;
    private GroupConfiguration groupConfiguration;

    private PermissionCommand permissionCommand;

    private GroupProvider groupProvider;
    private UserProvider userProvider;
    private ScoreboardProvider scoreboardProvider;
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

        super.getServer().getServicesManager().register(MultiPermsBukkitApi.class, this, this, ServicePriority.Normal);
    }

    @Override
    public void onDisable() {
        MultiPermsApiProvider.unregisterApi();

        super.getServer().getServicesManager().unregisterAll(this);
    }

    @Override
    public void registerCommands() {
        super.registerCommand(this.permissionCommand = new PermissionCommand(this));
    }

    @Override
    public void registerListeners() {
        super.registerListenerIf(new PermissionGroupDeleteListener(this),
                enableIf -> this.configuration.isTablistEnabled());
        super.registerListenerIf(new PermissionGroupEditListener(this),
                enableIf -> this.configuration.isTablistEnabled());

        super.registerListenerIf(new PlayerChatListener(this),
                enableIf -> this.configuration.isChatEnabled());
        super.registerListener(new PlayerConnectionListener(this));
        super.registerListenerIf(new PlayerPermissionCheckListener(this),
                enableIf -> this.configuration.isPermissionBasedActionsEnabled());

        super.registerListenerIf(new UserConnectionListener(this),
                enableIf -> this.configuration.isTablistEnabled());
    }

    @Override
    public void registerModules() {
        super.registerModule(this.groupProvider = new GroupProvider(this.groupConfiguration));
        super.registerModule(this.scoreboardProvider = new ScoreboardProvider(this.groupProvider));
        super.registerModule(this.userProvider = new UserProvider(this.configuration, this.groupProvider, this.scoreboardProvider));
        super.registerModule(this.permissionHookProvider = new DefaultPermissionHookProvider());
        super.registerModule(this.translationProvider = new DefaultTranslationProvider(this));
    }

    @Override
    public File getUserFolder() {
        return super.getSubFolder("user");
    }

    public static MultiPermsPlugin getInstance() {
        return JavaPlugin.getPlugin(MultiPermsPlugin.class);
    }

}
