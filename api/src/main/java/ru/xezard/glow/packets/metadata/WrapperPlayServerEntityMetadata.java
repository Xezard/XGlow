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

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import ru.xezard.glow.packets.AbstractPacket;
import ru.xezard.glow.packets.metadata.versions.WrapperPlayServerEntityMetadata_v19_19;
import ru.xezard.glow.packets.metadata.versions.WrapperPlayServerEntityMetadata_v9_18;

import java.util.List;

public class WrapperPlayServerEntityMetadata
implements IWrapperPlayServerEntityMetadata {
    IWrapperPlayServerEntityMetadata wrapper;

    public WrapperPlayServerEntityMetadata() {
        MinecraftVersion version = ProtocolLibrary.getProtocolManager().getMinecraftVersion();

        switch (version.getMinor()) {
            case 18:
            case 17:
            case 16:
            case 15:
            case 14:
            case 13:
            case 12:
            case 11:
            case 10:
            case 9:
                this.wrapper = new WrapperPlayServerEntityMetadata_v9_18();
                break;

            case 19:
            default:
                this.wrapper = new WrapperPlayServerEntityMetadata_v19_19();
                break;
        }
    }

    @Override
    public AbstractPacket getPacket() {
        return this.wrapper.getPacket();
    }

    @Override
    public int getEntityId() {
        return this.wrapper.getEntityId();
    }

    @Override
    public void setEntityId(int value) {
        this.wrapper.setEntityId(value);
    }

    @Override
    public Entity getEntity(World world) {
        return this.wrapper.getEntity(world);
    }

    @Override
    public Entity getEntity(PacketEvent event) {
        return this.wrapper.getEntity(event);
    }

    @Override
    public List<WrappedWatchableObject> getMetadata() {
        return this.wrapper.getMetadata();
    }

    @Override
    public void setMetadata(List<WrappedWatchableObject> value) {
        this.wrapper.setMetadata(value);
    }
}
