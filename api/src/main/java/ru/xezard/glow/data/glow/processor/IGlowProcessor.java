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
package ru.xezard.glow.data.glow.processor;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import ru.xezard.glow.packets.AbstractPacket;
import ru.xezard.glow.packets.scoreboard.AbstractWrapperPlayServerScoreboardTeam;

import java.util.List;
import java.util.Map;

public interface IGlowProcessor {
    AbstractPacket createTeamPacket(Map<String, Boolean> holders, ChatColor color, String teamName,
                                    AbstractWrapperPlayServerScoreboardTeam.Mode mode);

    List<AbstractPacket> createGlowPackets(Map<String, Boolean> holders, boolean glow);

    AbstractPacket createGlowPacket(Entity entity, boolean enableGlow);

    WrappedDataWatcher createDataWatcher(Entity entity, boolean enableGlow);
}