/*
 * PacketWrapper - ProtocolLib wrappers for Minecraft packets
 * Copyright (C) dmulloy2 <http://dmulloy2.net>
 * Copyright (C) Kristian S. Strangeland
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ru.xezard.glow.packets.metadata;

import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import ru.xezard.glow.packets.AbstractPacket;

import java.util.List;

public interface IWrapperPlayServerEntityMetadata {
    AbstractPacket getPacket();

    int getEntityId();

    void setEntityId(int value);

    Entity getEntity(World world);

    Entity getEntity(PacketEvent event);

    List<WrappedWatchableObject> getMetadata();

    void setMetadata(List<WrappedWatchableObject> value);
}
