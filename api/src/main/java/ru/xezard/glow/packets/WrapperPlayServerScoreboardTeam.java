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
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.IntEnum;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.Collection;
import java.util.List;

public class WrapperPlayServerScoreboardTeam
extends AbstractPacket
{
    public static final PacketType TYPE = PacketType.Play.Server.SCOREBOARD_TEAM;

    public WrapperPlayServerScoreboardTeam()
    {
        super(new PacketContainer(TYPE), TYPE);

        this.handle.getModifier().writeDefaults();
    }

    public WrapperPlayServerScoreboardTeam(PacketContainer packet)
    {
        super(packet, TYPE);
    }

    public String getName()
    {
        return this.handle.getStrings().read(0);
    }

    public void setName(String value)
    {
        this.handle.getStrings().write(0, value);
    }

    public int getMode()
    {
        return this.handle.getIntegers().read(0);
    }

    public void setMode(int value)
    {
        this.handle.getIntegers().write(0, value);
    }

    public WrappedChatComponent getDisplayName()
    {
        return this.handle.getChatComponents().read(0);
    }

    public void setDisplayName(WrappedChatComponent value)
    {
        this.handle.getChatComponents().write(0, value);
    }

    public WrappedChatComponent getPrefix()
    {
        return this.handle.getChatComponents().read(1);
    }

    public void setPrefix(WrappedChatComponent value)
    {
        this.handle.getChatComponents().write(1, value);
    }

    public WrappedChatComponent getSuffix()
    {
        return this.handle.getChatComponents().read(2);
    }

    public void setSuffix(WrappedChatComponent value)
    {
        this.handle.getChatComponents().write(2, value);
    }

    public String getNameTagVisibility()
    {
        return this.handle.getStrings().read(1);
    }

    public void setNameTagVisibility(String value)
    {
        this.handle.getStrings().write(1, value);
    }

    public ChatColor getColor()
    {
        return this.handle.getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat"))
                .read(0);
    }

    public void setColor(ChatColor value)
    {
        this.handle.getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat"))
                .write(0, value);
    }

    public String getCollisionRule()
    {
        return this.handle.getStrings().read(2);
    }

    public void setCollisionRule(String value)
    {
        this.handle.getStrings().write(2, value);
    }

    @SuppressWarnings("unchecked")
    public List<String> getPlayers()
    {
        return (List<String>) this.handle.getSpecificModifier(Collection.class)
                .read(0);
    }

    public void setPlayers(List<String> value)
    {
        this.handle.getSpecificModifier(Collection.class)
                .write(0, value);
    }

    /**
     * Retrieve pack option data. Pack data is calculated as follows:
     *
     * <pre>
     * <code>
     * int data = 0;
     * if (team.allowFriendlyFire()) {
     *     data |= 1;
     * }
     * if (team.canSeeFriendlyInvisibles()) {
     *     data |= 2;
     * }
     * </code>
     * </pre>
     *
     * @return The current pack option data
     */
    public int getPackOptionData()
    {
        return this.handle.getIntegers().read(1);
    }

    public void setPackOptionData(int value)
    {
        this.handle.getIntegers().write(1, value);
    }

    public static class Mode extends IntEnum
    {
        public static final int TEAM_CREATED = 0,
                                TEAM_REMOVED = 1,
                                TEAM_UPDATED = 2,
                                PLAYERS_ADDED = 3,
                                PLAYERS_REMOVED = 4;

        @Getter
        private static final Mode INSTANCE = new Mode();
    }
}