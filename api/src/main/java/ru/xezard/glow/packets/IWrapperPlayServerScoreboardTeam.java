package ru.xezard.glow.packets;

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Optional;

public interface IWrapperPlayServerScoreboardTeam {
    AbstractPacket getPacket();

    String getTeamName();

    void setTeamName(String value);

    AbstractWrapperPlayServerScoreboardTeam.Mode getMode();

    void setMode(AbstractWrapperPlayServerScoreboardTeam.Mode value);

    Optional<WrappedChatComponent> getDisplayName();

    void setDisplayName(WrappedChatComponent value);

    Optional<WrappedChatComponent> getPrefix();

    void setPrefix(WrappedChatComponent value);

    Optional<WrappedChatComponent> getSuffix();

    void setSuffix(WrappedChatComponent value);

    AbstractWrapperPlayServerScoreboardTeam.NameTagVisibility getNameTagVisibility();

    void setNameTagVisibility(AbstractWrapperPlayServerScoreboardTeam.NameTagVisibility value);

    Optional<ChatColor> getColor();

    void setColor(ChatColor value);

    AbstractWrapperPlayServerScoreboardTeam.CollisionRule getCollisionRule();

    void setCollisionRule(AbstractWrapperPlayServerScoreboardTeam.CollisionRule value);

    List<String> getPlayers();

    void setPlayers(List<String> value);
}