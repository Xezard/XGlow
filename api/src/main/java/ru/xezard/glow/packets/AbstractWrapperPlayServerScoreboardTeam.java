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

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Preconditions;

import java.util.Collection;
import java.util.List;

public abstract class AbstractWrapperPlayServerScoreboardTeam
extends AbstractPacket
implements IWrapperPlayServerScoreboardTeam {
    public static final PacketType TYPE = PacketType.Play.Server.SCOREBOARD_TEAM;

    public AbstractWrapperPlayServerScoreboardTeam() {
        super(new PacketContainer(TYPE), TYPE);

        this.handle.getModifier().writeDefaults();
    }

    public AbstractWrapperPlayServerScoreboardTeam(PacketContainer packet) {
        super(packet, TYPE);
    }

    @Override
    public AbstractPacket getPacket() {
        return this;
    }

    @Override
    public String getTeamName() {
        return this.handle.getStrings().read(0);
    }

    @Override
    public void setTeamName(String value) {
        this.handle.getStrings().write(0, value);
    }

    @Override
    public Mode getMode() {
        return Mode.values()[this.handle.getIntegers().read(0)];
    }

    @Override
    public void setMode(Mode value) {
        this.handle.getIntegers().write(0, value.ordinal());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getPlayers() {
        return (List<String>) this.handle.getSpecificModifier(Collection.class)
                .read(0);
    }

    @Override
    public void setPlayers(List<String> value) {
        Preconditions.checkArgument(value.size() <= 40,
                "ScoreboardTeam cannot contain more than 40 entries.");

        this.handle.getSpecificModifier(Collection.class)
                .write(0, value);
    }

    public enum Mode {
        TEAM_CREATED,
        TEAM_REMOVED,
        TEAM_UPDATED,
        PLAYERS_ADDED,
        PLAYERS_REMOVED
    }

    public enum NameTagVisibility {
        ALWAYS,
        HIDE_FOR_OTHER_TEAMS,
        HIDE_FOR_OWN_TEAM,
        NEVER
    }

    public enum CollisionRule {
        ALWAYS,
        PUSH_OTHER_TEAMS,
        PUSH_OWN_TEAM,
        NEVER
    }
}