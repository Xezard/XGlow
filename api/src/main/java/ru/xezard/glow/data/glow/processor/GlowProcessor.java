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

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import ru.xezard.glow.packets.AbstractPacket;
import ru.xezard.glow.packets.AbstractWrapperPlayServerScoreboardTeam;
import ru.xezard.glow.packets.WrapperPlayServerEntityMetadata;
import ru.xezard.glow.packets.WrapperPlayServerScoreboardTeam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GlowProcessor
implements IGlowProcessor {
    static WrappedDataWatcher.Serializer BYTE_SERIALIZER =
            WrappedDataWatcher.Registry.get(Byte.class);

    @NonFinal static volatile GlowProcessor instance;

    public static GlowProcessor getInstance() {
        if (instance == null) {
            synchronized (GlowProcessor.class) {
                if (instance == null) {
                    instance = new GlowProcessor();
                }
            }
        }

        return instance;
    }

    @Override
    public List<AbstractPacket> createGlowPackets(Set<Entity> entities, boolean glow) {
        return entities.stream()
                       .map((entity) -> this.createGlowPacket(entity, glow))
                       .collect(Collectors.toList());
    }

    @Override
    public AbstractPacket createTeamPacket(Set<Entity> holders, ChatColor color, String teamName,
                                           AbstractWrapperPlayServerScoreboardTeam.Mode mode) {
        WrapperPlayServerScoreboardTeam team = new WrapperPlayServerScoreboardTeam();

        team.setTeamName(teamName);
        team.setMode(mode);

        if (mode == AbstractWrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED) {
            return team.getPacket();
        }

        team.setDisplayName(WrappedChatComponent.fromText(teamName));
        team.setColor(color);
        team.setNameTagVisibility(AbstractWrapperPlayServerScoreboardTeam.NameTagVisibility.ALWAYS);

        team.setPlayers(holders.stream()
                .map((holder) -> holder instanceof OfflinePlayer ?
                        holder.getName() : holder.getUniqueId().toString())
                .collect(Collectors.toList()));

        return team.getPacket();
    }

    @Override
    public AbstractPacket createGlowPacket(Entity entity, boolean glow) {
        List<WrappedWatchableObject> metadata = this.createDataWatcher(entity, glow).getWatchableObjects();

        return new WrapperPlayServerEntityMetadata(metadata, entity.getEntityId());
    }

    @Override
    public WrappedDataWatcher createDataWatcher(Entity entity, boolean enableGlow) {
        WrappedDataWatcher dataWatcher = WrappedDataWatcher.getEntityWatcher(entity).deepClone();

        byte mask = dataWatcher.getByte(0);

        if (enableGlow) {
            mask |= 0x40;
        }

        dataWatcher.setEntity(entity);
        dataWatcher.setObject(0, BYTE_SERIALIZER, mask);

        return dataWatcher;
    }
}