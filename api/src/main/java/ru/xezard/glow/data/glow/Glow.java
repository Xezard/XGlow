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
package ru.xezard.glow.data.glow;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ru.xezard.glow.packets.AbstractPacket;
import ru.xezard.glow.packets.AbstractWrapperPlayServerScoreboardTeam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
public class Glow
extends AbstractGlow {
    public Glow(ChatColor color, String name) {
        super(color, name);
    }

    @Override
    public boolean hasHolder(Entity entity) {
        return this.holders.contains(entity);
    }

    @Override
    public void addHolders(Entity... entities) {
        this.processHolder(true, entities);
    }

    @Override
    public void removeHolders(Entity... entities) {
        this.processHolder(false, entities);
    }

    private void processHolder(boolean add, Entity... entities) {
        List<AbstractPacket> packets = new ArrayList<> ();

        for (Entity entity : entities) {
            if (add ? !this.holders.add(entity) : !this.holders.remove(entity)) {
                continue;
            }

            packets.add(PROCESSOR.createGlowPacket(entity, add));
        }

        if (packets.isEmpty()) {
            return;
        }

        packets.add(PROCESSOR.createTeamPacket(this.holders, this.color, this.name,
                add ? AbstractWrapperPlayServerScoreboardTeam.Mode.PLAYERS_ADDED :
                        AbstractWrapperPlayServerScoreboardTeam.Mode.PLAYERS_REMOVED));

        packets.forEach((packet) -> packet.sendPacket(this.viewers));
    }

    @Override
    public void display(Player... viewers) {
        List<AbstractPacket> packets = PROCESSOR.createGlowPackets(this.holders, true);

        for (Player viewer : viewers) {
            if (this.viewers.contains(viewer)) {
                continue;
            }

            this.render(viewer);
        }

        packets.add(PROCESSOR.createTeamPacket(this.holders, this.color, this.name,
                AbstractWrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED));

        packets.forEach((packet) -> packet.sendPacket(viewers));
    }

    @Override
    public void hideFrom(Player... viewers) {
        this.processView(false, viewers);
    }

    @Override
    public void render(Player... viewers) {
        this.processView(true, viewers);
    }

    private void processView(boolean display, Player... viewers) {
        List<AbstractPacket> packets = PROCESSOR.createGlowPackets(this.holders, display);

        for (Player viewer : viewers) {
            if (display == this.viewers.contains(viewer)) {
                continue;
            }

            if (display) {
                this.viewers.add(viewer);
                continue;
            }

            this.viewers.remove(viewer);
        }

        packets.add(PROCESSOR.createTeamPacket(this.holders, this.color, this.name,
                display ? AbstractWrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED :
                        AbstractWrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED));

        packets.forEach((packet) -> packet.sendPacket(viewers));
    }

    @Override
    public void destroy() {
        new HashSet<> (this.holders).forEach(this::removeHolders);
        new HashSet<> (this.viewers).forEach(this::hideFrom);

        this.holders.clear();
        this.viewers.clear();
    }

    public static GlowBuilder builder() {
        return new GlowBuilder();
    }

    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class GlowBuilder {
        ChatColor color;
        String name;

        public GlowBuilder color(ChatColor color) {
            this.color = color;
            return this;
        }

        public GlowBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Glow build() {
            return new Glow(this.color, this.name);
        }
    }
}