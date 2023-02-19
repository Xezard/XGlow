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
package ru.xezard.glow.packets.scoreboard.versions;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.ChatColor;
import ru.xezard.glow.packets.scoreboard.AbstractWrapperPlayServerScoreboardTeam;

import java.util.Optional;

public class WrapperPlayServerScoreboardTeam_v17_19
extends AbstractWrapperPlayServerScoreboardTeam {
    @Override
    public Optional<WrappedChatComponent> getDisplayName() {
        return this.handle.getOptionalStructures().read(0)
                .map((structure) -> structure.getChatComponents().read(0));
    }

    @Override
    public void setDisplayName(WrappedChatComponent value) {
        this.handle.getOptionalStructures().read(0)
                .map((structure) -> structure.getChatComponents().write(0, value));
    }

    @Override
    public Optional<WrappedChatComponent> getPrefix() {
        return this.handle.getOptionalStructures().read(0)
                .map((structure) -> structure.getChatComponents().read(1));
    }

    @Override
    public void setPrefix(WrappedChatComponent value) {
        this.handle.getOptionalStructures().read(0)
                .map((structure) -> structure.getChatComponents().write(1, value));
    }

    @Override
    public Optional<WrappedChatComponent> getSuffix() {
        return this.handle.getOptionalStructures().read(0)
                .map((structure) -> structure.getChatComponents().read(2));
    }

    @Override
    public void setSuffix(WrappedChatComponent value) {
        this.handle.getOptionalStructures().read(0)
                .map((structure) -> structure.getChatComponents().write(2, value));
    }

    @Override
    public NameTagVisibility getNameTagVisibility() {
        return this.handle.getOptionalStructures().read(0)
                .flatMap((structure) -> NameTagVisibility.getByIdentifier(
                        structure.getStrings().read(0)))
                .orElse(NameTagVisibility.NEVER);
    }

    @Override
    public void setNameTagVisibility(NameTagVisibility value) {
        this.handle.getOptionalStructures().read(0).map((structure) ->
                structure.getStrings().write(0, value.getIdentifier()));
    }

    @Override
    public Optional<ChatColor> getColor() {
        return this.handle.getOptionalStructures().read(0).map((structure) ->
                structure.getEnumModifier(ChatColor.class,
                        MinecraftReflection.getMinecraftClass("EnumChatFormat"))
                        .read(0));
    }

    @Override
    public void setColor(ChatColor value) {
        this.handle.getOptionalStructures().read(0).map((structure) ->
                structure.getEnumModifier(ChatColor.class,
                        MinecraftReflection.getMinecraftClass("EnumChatFormat"))
                        .write(0, value));
    }

    @Override
    public CollisionRule getCollisionRule() {
        return this.handle.getOptionalStructures().read(0)
                .flatMap((structure) -> CollisionRule.getByIdentifier(
                        structure.getStrings().read(1)))
                .orElse(CollisionRule.NEVER);
    }

    @Override
    public void setCollisionRule(CollisionRule value) {
        this.handle.getOptionalStructures().read(0).map((structure) ->
                structure.getStrings().write(1, value.getIdentifier()));
    }
}