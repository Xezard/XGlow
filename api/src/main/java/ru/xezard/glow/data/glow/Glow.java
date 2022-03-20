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

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import ru.xezard.glow.packets.AbstractPacket;
import ru.xezard.glow.packets.WrapperPlayServerScoreboardTeam;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.xezard.glow.data.animation.Animation;
import ru.xezard.glow.data.animation.IAnimation;
import ru.xezard.glow.packets.WrapperPlayServerEntityMetadata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = false)
public class Glow
extends AbstractGlow
{
    public Glow(IAnimation<ChatColor> animatedColor, Plugin plugin, long updatePeriod, String name, boolean async)
    {
        super(animatedColor, plugin, updatePeriod, name, async);
    }

    public Glow(ChatColor color, String name)
    {
        super(color, name);
    }

    private List<AbstractPacket> createGlowPackets(Set<Entity> entities, boolean glow)
    {
        return entities.stream()
                       .map((entity) -> this.createGlowPacket(entity, glow))
                       .collect(Collectors.toList());
    }

    private AbstractPacket createGlowPacket(Entity entity, boolean glow)
    {
        WrapperPlayServerEntityMetadata entityMetadata = new WrapperPlayServerEntityMetadata();

        entityMetadata.setEntityID(entity.getEntityId());

        WrappedDataWatcher dataWatcher = WrappedDataWatcher.getEntityWatcher(entity).deepClone();

        WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class);

        byte mask = dataWatcher.getByte(0);

        if (glow)
        {
            mask |= 0x40;
        }

        dataWatcher.setEntity(entity);
        dataWatcher.setObject(0, byteSerializer, mask);

        entityMetadata.setMetadata(dataWatcher.getWatchableObjects());

        return entityMetadata;
    }

    private AbstractPacket createTeamPacket(int mode)
    {
        WrapperPlayServerScoreboardTeam team = new WrapperPlayServerScoreboardTeam();

        team.setName(this.name);
        team.setDisplayName(WrappedChatComponent.fromText(this.name));
        team.setMode(mode);

        if (mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED)
        {
            return team;
        }

        team.setColor(this.animatedColor.next());
        team.setNameTagVisibility("ALWAYS");

        team.setPlayers
        (
                this.holders.stream()
                            .map((holder) -> holder instanceof OfflinePlayer ? holder.getName() : holder.getUniqueId().toString())
                            .collect(Collectors.toList())
        );

        return team;
    }

    @Override
    public boolean hasHolder(Entity entity)
    {
        return this.holders.contains(entity);
    }

    @Override
    public void addHolders(Entity... entities)
    {
        this.processHolder(true, entities);
    }

    @Override
    public void removeHolders(Entity... entities)
    {
        this.processHolder(false, entities);
    }

    private void processHolder(boolean add, Entity... entities)
    {
        List<AbstractPacket> packets = new ArrayList<> ();

        for (Entity entity : entities)
        {
            if (add ? !this.holders.add(entity) : !this.holders.remove(entity))
            {
                continue;
            }

            packets.add(this.createGlowPacket(entity, add));
        }

        if (packets.isEmpty())
        {
            return;
        }

        packets.add(this.createTeamPacket(add ? WrapperPlayServerScoreboardTeam.Mode.PLAYERS_ADDED : WrapperPlayServerScoreboardTeam.Mode.PLAYERS_REMOVED));

        packets.forEach((packet) -> packet.sendPacket(this.viewers));
    }

    @Override
    public void display(Player... viewers)
    {
        List<AbstractPacket> packets = this.createGlowPackets(this.holders, true);

        for (Player viewer : viewers)
        {
            if (this.viewers.contains(viewer))
            {
                continue;
            }

            this.render(viewer);
        }

        packets.add(this.createTeamPacket(WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED));

        packets.forEach((packet) -> packet.sendPacket(viewers));
    }

    @Override
    public void hideFrom(Player... viewers)
    {
        this.processView(false, viewers);
    }

    @Override
    public void render(Player... viewers)
    {
        this.processView(true, viewers);
    }

    private void processView(boolean display, Player... viewers)
    {
        List<AbstractPacket> packets = this.createGlowPackets(this.holders, display);

        for (Player viewer : viewers)
        {
            if (display == this.viewers.contains(viewer))
            {
                continue;
            }

            if (display)
            {
                this.viewers.add(viewer);
                continue;
            }

            this.viewers.remove(viewer);
        }

        packets.add(this.createTeamPacket(display ? WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED : WrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED));

        packets.forEach((packet) -> packet.sendPacket(viewers));

        if (this.animatedColor.isAnimated())
        {
            if (display)
            {
                if (this.viewers.size() <= 0 || this.animated)
                {
                    return;
                }

                this.startAnimation();
                return;
            }

            if (this.viewers.size() > 0 || !this.animated)
            {
                return;
            }

            this.stopAnimation();
        }
    }

    @Override
    public void destroy()
    {
        new HashSet<> (this.holders).forEach(this::removeHolders);
        new HashSet<> (this.viewers).forEach(this::hideFrom);

        this.holders.clear();
        this.viewers.clear();
    }

    public static GlowBuilder builder()
    {
        return new GlowBuilder();
    }

    @NoArgsConstructor
    public static class GlowBuilder
    {
        private IAnimation<ChatColor> animatedColor;

        private Plugin plugin;

        private String name;

        private long updatePeriod;

        private boolean asyncAnimation;

        public GlowBuilder animatedColor(IAnimation<ChatColor> animatedColor)
        {
            this.animatedColor = animatedColor;
            return this;
        }

        public GlowBuilder animatedColor(List<ChatColor> animatedColor)
        {
            this.animatedColor = new Animation<> (animatedColor);
            return this;
        }

        public GlowBuilder animatedColor(ChatColor... displayNameColor)
        {
            this.animatedColor = new Animation<> (displayNameColor);
            return this;
        }

        public GlowBuilder plugin(Plugin plugin)
        {
            this.plugin = plugin;
            return this;
        }

        public GlowBuilder updatePeriod(long updatePeriod)
        {
            this.updatePeriod = updatePeriod;
            return this;
        }

        public GlowBuilder name(String name)
        {
            this.name = name;
            return this;
        }

        public GlowBuilder asyncAnimation(boolean asyncAnimation)
        {
            this.asyncAnimation = asyncAnimation;
            return this;
        }

        public Glow build()
        {
            if (this.animatedColor.isAnimated() && this.plugin == null)
            {
                return new Glow(this.animatedColor.next(), this.name);
            }

            return new Glow
            (
                    this.animatedColor,
                    this.plugin,
                    this.updatePeriod,
                    this.name,
                    this.asyncAnimation
            );
        }
    }
}