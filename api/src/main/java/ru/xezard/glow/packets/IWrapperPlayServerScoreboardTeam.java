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
package ru.xezard.glow.packets;

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Optional;

public interface IWrapperPlayServerScoreboardTeam {
    AbstractPacket getPacket();

    String getTeamName();

    void setTeamName(String value);

    AbstractWrapperPlayServerScoreboardTeam.Mode getMode();

    void setMode(AbstractWrapperPlayServerScoreboardTeam.Mode value);

    Optional<WrappedChatComponent> getDisplayName();

    void setDisplayName(WrappedChatComponent value);

    Optional<WrappedChatComponent> getPrefix();

    void setPrefix(WrappedChatComponent value);

    Optional<WrappedChatComponent> getSuffix();

    void setSuffix(WrappedChatComponent value);

    AbstractWrapperPlayServerScoreboardTeam.NameTagVisibility getNameTagVisibility();

    void setNameTagVisibility(AbstractWrapperPlayServerScoreboardTeam.NameTagVisibility value);

    Optional<ChatColor> getColor();

    void setColor(ChatColor value);

    AbstractWrapperPlayServerScoreboardTeam.CollisionRule getCollisionRule();

    void setCollisionRule(AbstractWrapperPlayServerScoreboardTeam.CollisionRule value);

    List<String> getPlayers();

    void setPlayers(List<String> value);
}