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

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import ru.xezard.glow.packets.AbstractPacket;

import java.util.List;

public abstract class AbstractWrapperPlayServerEntityMetadata
        extends AbstractPacket
        implements IWrapperPlayServerEntityMetadata {
    public static final PacketType TYPE = PacketType.Play.Server.ENTITY_METADATA;

    protected AbstractWrapperPlayServerEntityMetadata() {
        super(new PacketContainer(TYPE), TYPE);

        this.handle.getModifier().writeDefaults();
    }

    @Override
    public AbstractPacket getPacket() {
        return this;
    }

    @Override
    public int getEntityId() {
        return this.handle.getIntegers().read(0);
    }

    @Override
    public void setEntityId(int value) {
        this.handle.getIntegers().write(0, value);
    }

    @Override
    public Entity getEntity(World world) {
        return this.handle.getEntityModifier(world).read(0);
    }

    @Override
    public Entity getEntity(PacketEvent event) {
        return this.getEntity(event.getPlayer().getWorld());
    }

    @Override
    public List<WrappedWatchableObject> getMetadata() {
        return this.handle.getWatchableCollectionModifier().read(0);
    }

    @Override
    public void setMetadata(List<WrappedWatchableObject> value) {
        this.handle.getWatchableCollectionModifier().write(0, value);
    }
}
