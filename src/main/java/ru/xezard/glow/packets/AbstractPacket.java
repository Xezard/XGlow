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
package ru.xezard.glow.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractPacket
{
    @Getter
    protected PacketContainer handle;

    protected AbstractPacket(PacketContainer handle, PacketType type)
    {
        Preconditions.checkNotNull(handle, "packet handle cannot be null");
        Preconditions.checkArgument(Objects.equal(handle.getType(), type), handle.getHandle() + " is not a packet of type " + type);

        this.handle = handle;
    }

    public void sendPacket(Iterable<Player> recipients)
    {
        for (Player receiver : recipients)
        {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, this.handle);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot send packet", e);
            }
        }
    }

    public void sendPacket(Player... recipients)
    {
        for (Player receiver : recipients)
        {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, this.handle);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot send packet", e);
            }
        }
    }

    public void broadcastPacket()
    {
        ProtocolLibrary.getProtocolManager().broadcastServerPacket(getHandle());
    }

    public void receivePacket(Player sender)
    {
        try {
            ProtocolLibrary.getProtocolManager().recieveClientPacket(sender, this.handle);
        } catch (Exception e) {
            throw new RuntimeException("Cannot receive packet: ", e);
        }
    }
}