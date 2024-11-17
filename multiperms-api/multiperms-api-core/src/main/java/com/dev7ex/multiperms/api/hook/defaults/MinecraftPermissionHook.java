package com.dev7ex.multiperms.api.hook.defaults;

import com.dev7ex.multiperms.api.hook.PermissionHook;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

/**
 * @author Dev7ex
 * @since 09.11.2024
 */
@Getter(AccessLevel.PUBLIC)
public class MinecraftPermissionHook implements PermissionHook {

    private final List<String> permissions = List.of(
            "minecraft.command.advancement", "minecraft.command.attribute", "minecraft.command.ban",
            "minecraft.command.ban-ip", "minecraft.command.banlist", "minecraft.command.bossbar",
            "minecraft.command.clear", "minecraft.command.clone", "minecraft.command.damage",
            "minecraft.command.data", "minecraft.command.datapack", "minecraft.command.debug",
            "minecraft.command.defaultgamemode", "minecraft.command.deop", "minecraft.command.difficulty",
            "minecraft.command.effect", "minecraft.command.enchant", "minecraft.command.execute",
            "minecraft.command.fill", "minecraft.command.fillbiome", "minecraft.command.forceload",
            "minecraft.command.function", "minecraft.command.gamemode", "minecraft.command.gamerule",
            "minecraft.command.give", "minecraft.command.help", "minecraft.command.item", "minecraft.command.jfr",
            "minecraft.command.kick", "minecraft.command.kill", "minecraft.command.list", "minecraft.command.locate",
            "minecraft.command.loot", "minecraft.command.me", "minecraft.command.msg", "minecraft.command.op",
            "minecraft.command.pardon", "minecraft.command.pardon-ip", "minecraft.command.particle",
            "minecraft.command.perf", "minecraft.command.place", "minecraft.command.playsound",
            "minecraft.command.publish", "minecraft.command.random", "minecraft.command.recipe",
            "minecraft.command.reload", "minecraft.command.return", "minecraft.command.ride",
            "minecraft.command.save-all", "minecraft.command.save-off", "minecraft.command.save-on",
            "minecraft.command.say", "minecraft.command.schedule", "minecraft.command.scoreboard",
            "minecraft.command.seed", "minecraft.command.setblock", "minecraft.command.setidletimeout",
            "minecraft.command.setworldspawn", "minecraft.command.spawnpoint", "minecraft.command.spectate",
            "minecraft.command.spreadplayers", "minecraft.command.stop", "minecraft.command.stopsound",
            "minecraft.command.summon", "minecraft.command.tag", "minecraft.command.team", "minecraft.command.teammsg",
            "minecraft.command.teleport", "minecraft.command.tellraw", "minecraft.command.tick",
            "minecraft.command.time", "minecraft.command.title", "minecraft.command.transfer",
            "minecraft.command.trigger", "minecraft.command.weather", "minecraft.command.whitelist",
            "minecraft.command.worldborder", "minecraft.command.xp"
    );

    @Override
    public void register() {}

    @Override
    public void unregister() {}

}
