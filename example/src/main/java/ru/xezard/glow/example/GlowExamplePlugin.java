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
package ru.xezard.glow.example;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import ru.xezard.glow.data.glow.Glow;
import ru.xezard.glow.example.commands.GlowCommand;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.bukkit.ChatColor.*;

public class GlowExamplePlugin
extends JavaPlugin {
    @Override
    public void onEnable() {
        Glow glow = Glow.builder()
                .name("test")
                .color(ChatColor.RED)
                .build();

        List<ChatColor> chatColors = Arrays.asList(
                BLACK, DARK_BLUE, DARK_GREEN,
                DARK_AQUA, DARK_RED, DARK_PURPLE,
                GOLD, GRAY, DARK_GRAY, BLUE, GREEN,
                AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE
        );

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            ChatColor pickedColor =
                    chatColors.get(ThreadLocalRandom.current().nextInt(chatColors.size()));
            glow.setColor(pickedColor);
        }, 20L, 10L);

        this.getCommand("glow").setExecutor(new GlowCommand(glow));
    }
}