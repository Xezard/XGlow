/*
 *  This file is part of XGlow,
 *  licensed under the Apache License, Version 2.0.
 *
 *  Copyright (c) Xezard (Zotov Ivan)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package ru.xezard.glow.example.commands;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import ru.xezard.glow.data.glow.Glow;
import ru.xezard.glow.example.GlowExamplePlugin;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.bukkit.ChatColor.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GlowCommand
implements CommandExecutor {
    static List<String> HELP_MESSAGE = Arrays.asList(
            "--------------------- [XGlow] ----------------------",
            "'[]', '<>' - required and optional arguments",
            "",
            "> 'glow <help>' - show help page",
            "> 'glow init' - init glow object and update glow color task",
            "> 'glow spawn' - spawns a test entity",
            "> 'glow enable' - adds glow to a test entity",
            "> 'glow disable' - disable glow on a test entity",
            "> 'glow destroy' - remove all holders and viewers from glow objet",
            "> 'glow addviewer [player name]' - add viewer for glow on a test entity",
            "> 'glow removeviewer [player name]' - add viewer for glow on a test entity"
    );

    static List<ChatColor> CHAT_COLORS = Arrays.asList(
            BLACK, DARK_BLUE, DARK_GREEN,
            DARK_AQUA, DARK_RED, DARK_PURPLE,
            GOLD, GRAY, DARK_GRAY, BLUE, GREEN,
            AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE
    );

    static String PREFIX = "[XGlowExample] ";
    static String
            TEST_ENTITY_SPAWNED_MESSAGE = PREFIX + "Test entity spawned!",
            TEST_ENTITY_ALREADY_SPAWNED_MESSAGE = PREFIX + "Test entity already spawned!",
            TEST_ENTITY_WAS_NOT_SPAWNED_MESSAGE = PREFIX + "Test entity was not spawned!",
            TEST_ENTITY_ALREADY_GLOWING_MESSAGE = PREFIX + "Test entity already glowing!",
            TEST_ENTITY_ALREADY_NOT_GLOWING_MESSAGE = PREFIX + "Test entity is not glowing for now.",
            TEST_ENTITY_GLOWING_NOW_MESSAGE = PREFIX + "The test entity is now glowing for all viewers of the glow object.",
            TEST_ENTITY_NOT_GLOWING_NOW_MESSAGE = PREFIX + "The test entity is now not glowing for all viewers of the glow object.",
            PLAYER_WITH_THAT_NAME_NOT_FOUND_MESSAGE = PREFIX + "Player with name '{target_name}' not found.",
            PLAYER_SEES_TEST_ENTITY_GLOW_MESSAGE = PREFIX + "Player with name '{target_name}' can now see test entity glow.",
            PLAYER_NO_LONGER_SEES_TEST_ENTITY_GLOW_MESSAGE = PREFIX + "Player with name '{target_name}' no longer sees test entity glow.",
            GLOW_OBJECT_DESTROYED = PREFIX + "Glow object destroyed!",
            GLOW_OBJECT_INITIALIZED = PREFIX + "Glow object successfully initialized!",
            GLOW_OBJECT_ALREADY_INITIALIZED = PREFIX + "Glow object already initialized!",
            GLOW_OBJECT_IS_DESTROYED = PREFIX + "Glow object is not initialized!";

    @NonFinal Entity entity;
    @NonFinal Glow glow;
    @NonFinal BukkitTask task;
    GlowExamplePlugin plugin;

    public GlowCommand(GlowExamplePlugin plugin) {
        this.plugin = plugin;

        this.init();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't use this command from console.");
            return false;
        }

        Player player = (Player) sender;

        switch (arguments.length) {
            case 0:
                HELP_MESSAGE.forEach(sender::sendMessage);
                return true;

            case 1:
                switch (arguments[0].toLowerCase()) {
                    case "init":
                        if (this.glow != null && this.task != null) {
                            player.sendMessage(GLOW_OBJECT_ALREADY_INITIALIZED);
                            return false;
                        }

                        this.init();

                        player.sendMessage(GLOW_OBJECT_INITIALIZED);
                        return true;

                    case "spawn":
                        if (this.entity != null) {
                            player.sendMessage(TEST_ENTITY_ALREADY_SPAWNED_MESSAGE);
                            return true;
                        }

                        this.entity = player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);

                        player.sendMessage(TEST_ENTITY_SPAWNED_MESSAGE);
                        return true;

                    case "enable":
                        if (this.glow == null) {
                            player.sendMessage(GLOW_OBJECT_IS_DESTROYED);
                            return true;
                        }

                        if (this.entity == null) {
                            player.sendMessage(TEST_ENTITY_WAS_NOT_SPAWNED_MESSAGE);
                            return true;
                        }

                        if (this.glow.hasHolder(this.entity)) {
                            player.sendMessage(TEST_ENTITY_ALREADY_GLOWING_MESSAGE);
                            return true;
                        }

                        this.glow.addHolders(this.entity);

                        player.sendMessage(TEST_ENTITY_GLOWING_NOW_MESSAGE);
                        return true;

                    case "disable":
                        if (this.glow == null) {
                            player.sendMessage(GLOW_OBJECT_IS_DESTROYED);
                            return true;
                        }

                        if (this.entity == null) {
                            player.sendMessage(TEST_ENTITY_WAS_NOT_SPAWNED_MESSAGE);
                            return true;
                        }

                        if (!this.glow.hasHolder(this.entity)) {
                            player.sendMessage(TEST_ENTITY_ALREADY_NOT_GLOWING_MESSAGE);
                            return true;
                        }

                        this.glow.removeHolders(this.entity);

                        player.sendMessage(TEST_ENTITY_NOT_GLOWING_NOW_MESSAGE);
                        return true;

                    case "destroy":
                        this.task.cancel();
                        this.task = null;

                        this.glow.destroy();
                        this.glow = null;

                        player.sendMessage(GLOW_OBJECT_DESTROYED);
                        return true;
                }

                return false;

            case 2:
                if (this.glow == null) {
                    player.sendMessage(GLOW_OBJECT_IS_DESTROYED);
                    return true;
                }

                if (this.entity == null) {
                    player.sendMessage(TEST_ENTITY_WAS_NOT_SPAWNED_MESSAGE);
                    return true;
                }

                String targetName = arguments[1];

                Player target = Bukkit.getPlayerExact(targetName);

                if (target == null || !target.isOnline()) {
                    player.sendMessage(PLAYER_WITH_THAT_NAME_NOT_FOUND_MESSAGE
                            .replace("{target_name}", targetName));
                    return true;
                }

                switch (arguments[0].toLowerCase()) {
                    case "addviewer":
                        this.glow.display(target);

                        player.sendMessage(PLAYER_SEES_TEST_ENTITY_GLOW_MESSAGE
                                .replace("{target_name}", targetName));
                        return true;
                    case "removeviewer":
                        this.glow.hideFrom(target);

                        player.sendMessage(PLAYER_NO_LONGER_SEES_TEST_ENTITY_GLOW_MESSAGE
                                .replace("{target_name}", targetName));
                        return true;
                }
                return false;
        }
        return false;
    }

    private void init() {
        this.glow = Glow.builder()
                .name("test")
                .color(ChatColor.RED)
                .build();

        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, () -> {
            ChatColor pickedColor =
                    CHAT_COLORS.get(ThreadLocalRandom.current().nextInt(CHAT_COLORS.size()));
            this.glow.setColor(pickedColor);
        }, 20L, 10L);
    }
}