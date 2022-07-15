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
package ru.xezard.glow.packets;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.ChatColor;
import ru.xezard.glow.packets.versions.WrapperPlayServerScoreboardTeam_v12;
import ru.xezard.glow.packets.versions.WrapperPlayServerScoreboardTeam_v13_16;
import ru.xezard.glow.packets.versions.WrapperPlayServerScoreboardTeam_v17_19;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WrapperPlayServerScoreboardTeam
implements IWrapperPlayServerScoreboardTeam {
    IWrapperPlayServerScoreboardTeam wrapper;

    public WrapperPlayServerScoreboardTeam() {
        MinecraftVersion version = ProtocolLibrary.getProtocolManager().getMinecraftVersion();

        switch (version.getMinor()) {
            case 16:
            case 15:
            case 14:
            case 13:
                this.wrapper = new WrapperPlayServerScoreboardTeam_v13_16();
                break;

            case 12:
            case 11:
            case 10:
            case 9:
            case 8:
                this.wrapper = new WrapperPlayServerScoreboardTeam_v12();
                break;

            case 19:
            case 18:
            case 17:
            default:
                this.wrapper = new WrapperPlayServerScoreboardTeam_v17_19();
                break;
        }
    }

    @Override
    public AbstractPacket getPacket() {
        return this.wrapper.getPacket();
    }

    @Override
    public String getTeamName() {
        return this.wrapper.getTeamName();
    }

    @Override
    public void setTeamName(String value) {
        this.wrapper.setTeamName(value);
    }

    @Override
    public AbstractWrapperPlayServerScoreboardTeam.Mode getMode() {
        return this.wrapper.getMode();
    }

    @Override
    public void setMode(AbstractWrapperPlayServerScoreboardTeam.Mode value) {
        this.wrapper.setMode(value);
    }

    @Override
    public Optional<WrappedChatComponent> getDisplayName() {
        return this.wrapper.getDisplayName();
    }

    @Override
    public void setDisplayName(WrappedChatComponent value) {
        this.wrapper.setDisplayName(value);
    }

    @Override
    public Optional<WrappedChatComponent> getPrefix() {
        return this.wrapper.getPrefix();
    }

    @Override
    public void setPrefix(WrappedChatComponent value) {
        this.wrapper.setPrefix(value);
    }

    @Override
    public Optional<WrappedChatComponent> getSuffix() {
        return this.wrapper.getSuffix();
    }

    @Override
    public void setSuffix(WrappedChatComponent value) {
        this.wrapper.setSuffix(value);
    }

    @Override
    public AbstractWrapperPlayServerScoreboardTeam.NameTagVisibility getNameTagVisibility() {
        return this.wrapper.getNameTagVisibility();
    }

    @Override
    public void setNameTagVisibility(AbstractWrapperPlayServerScoreboardTeam.
                                             NameTagVisibility value) {
        this.wrapper.setNameTagVisibility(value);
    }

    @Override
    public Optional<ChatColor> getColor() {
        return this.wrapper.getColor();
    }

    @Override
    public void setColor(ChatColor value) {
        this.wrapper.setColor(value);
    }

    @Override
    public AbstractWrapperPlayServerScoreboardTeam.CollisionRule getCollisionRule() {
        return this.wrapper.getCollisionRule();
    }

    @Override
    public void setCollisionRule(AbstractWrapperPlayServerScoreboardTeam.CollisionRule value) {
        this.wrapper.setCollisionRule(value);
    }

    @Override
    public List<String> getPlayers() {
        return this.wrapper.getPlayers();
    }

    @Override
    public void setPlayers(List<String> value) {
        this.wrapper.setPlayers(value);
    }
}