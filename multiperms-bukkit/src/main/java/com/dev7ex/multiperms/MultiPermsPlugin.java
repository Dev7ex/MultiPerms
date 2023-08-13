package com.dev7ex.multiperms;

import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.common.bukkit.plugin.PluginProperties;
import com.dev7ex.multiperms.api.MultiPermsApiProvider;
import com.dev7ex.multiperms.api.bukkit.MultiPermsBukkitApi;
import com.dev7ex.multiperms.command.PermissionCommand;
import com.dev7ex.multiperms.group.GroupConfiguration;
import com.dev7ex.multiperms.group.GroupService;
import com.dev7ex.multiperms.hook.DefaultPermissionHookProvider;
import com.dev7ex.multiperms.listener.PlayerChatListener;
import com.dev7ex.multiperms.listener.PlayerConnectionListener;
import com.dev7ex.multiperms.listener.ScoreboardListener;
import com.dev7ex.multiperms.scoreboard.ScoreboardService;
import com.dev7ex.multiperms.user.UserService;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
@Getter(AccessLevel.PUBLIC)
@PluginProperties(metrics = true, metricsId = 19393)
public class MultiPermsPlugin extends BukkitPlugin implements MultiPermsBukkitApi {

    private MultiPermsConfiguration configuration;
    private GroupConfiguration groupConfiguration;

    private GroupService groupProvider;
    private UserService userProvider;
    private ScoreboardService scoreboardProvider;
    private DefaultPermissionHookProvider permissionHookProvider;

    @Override
    public void onLoad() {
        super.createDataFolder();
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
    }

    @Override
    public void onDisable() {
        MultiPermsApiProvider.unregisterApi();
    }

    @Override
    public void registerCommands() {
        super.registerCommand(new PermissionCommand(this));
    }

    @Override
    public void registerListeners() {
        super.registerListenerIf(new PlayerChatListener(this), enableIf -> this.configuration.isChatEnabled());
        super.registerListener(new PlayerConnectionListener(this));
        super.registerListenerIf(new ScoreboardListener(this), enableIf -> this.configuration.isTablistEnabled());
    }

    @Override
    public void registerServices() {
        super.registerService(this.groupProvider = new GroupService(this.groupConfiguration));
        super.registerService(this.userProvider = new UserService(this.groupProvider));
        super.registerService(this.scoreboardProvider = new ScoreboardService(this.groupProvider));
        super.registerService(this.permissionHookProvider = new DefaultPermissionHookProvider());
    }

    @Override
    public File getUserFolder() {
        return super.getSubFolder("user");
    }

    public static MultiPermsPlugin getInstance() {
        return JavaPlugin.getPlugin(MultiPermsPlugin.class);
    }

}
