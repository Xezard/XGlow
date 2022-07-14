package ru.xezard.glow.data.glow.processor;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import ru.xezard.glow.packets.AbstractPacket;
import ru.xezard.glow.packets.AbstractWrapperPlayServerScoreboardTeam;

import java.util.List;
import java.util.Set;

public interface IGlowProcessor {
    AbstractPacket createTeamPacket(Set<Entity> holders, ChatColor color, String teamName,
                                    AbstractWrapperPlayServerScoreboardTeam.Mode mode);

    List<AbstractPacket> createGlowPackets(Set<Entity> entities, boolean enableGlow);

    AbstractPacket createGlowPacket(Entity entity, boolean enableGlow);
}