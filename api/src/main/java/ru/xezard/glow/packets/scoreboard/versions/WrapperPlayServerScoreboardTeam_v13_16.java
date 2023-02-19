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
package ru.xezard.glow.packets.scoreboard.versions;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.ChatColor;
import ru.xezard.glow.packets.scoreboard.AbstractWrapperPlayServerScoreboardTeam;

import java.util.Optional;

public class WrapperPlayServerScoreboardTeam_v13_16
extends AbstractWrapperPlayServerScoreboardTeam {
    @Override
    public Optional<WrappedChatComponent> getDisplayName() {
        return Optional.ofNullable(this.handle.getChatComponents().read(0));
    }

    @Override
    public void setDisplayName(WrappedChatComponent value) {
        this.handle.getChatComponents().write(0, value);
    }

    @Override
    public Optional<WrappedChatComponent> getPrefix() {
        return Optional.ofNullable(this.handle.getChatComponents().read(1));
    }

    @Override
    public void setPrefix(WrappedChatComponent value) {
        this.handle.getChatComponents().write(1, value);
    }

    @Override
    public Optional<WrappedChatComponent> getSuffix() {
        return Optional.ofNullable(this.handle.getChatComponents().read(2));
    }

    @Override
    public void setSuffix(WrappedChatComponent value) {
        this.handle.getChatComponents().write(2, value);
    }

    @Override
    public NameTagVisibility getNameTagVisibility() {
        return NameTagVisibility.getByIdentifier(this.handle.getStrings().read(1).toUpperCase())
                                .orElse(NameTagVisibility.NEVER);
    }

    @Override
    public void setNameTagVisibility(NameTagVisibility nameTagVisibility) {
        this.handle.getStrings().write(1, nameTagVisibility.getIdentifier());
    }

    @Override
    public Optional<ChatColor> getColor() {
        return Optional.of(this.handle.getEnumModifier(ChatColor.class,
                        MinecraftReflection.getMinecraftClass("EnumChatFormat"))
                .read(0));
    }

    @Override
    public void setColor(ChatColor value) {
        this.handle.getEnumModifier(ChatColor.class,
                        MinecraftReflection.getMinecraftClass("EnumChatFormat"))
                .write(0, value);
    }

    @Override
    public CollisionRule getCollisionRule() {
        return CollisionRule.getByIdentifier(this.handle.getStrings().read(2))
                            .orElse(CollisionRule.NEVER);
    }

    @Override
    public void setCollisionRule(CollisionRule value) {
        this.handle.getStrings().write(2, value.getIdentifier());
    }
}
