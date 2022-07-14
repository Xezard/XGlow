package ru.xezard.glow.packets;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.ChatColor;
import ru.xezard.glow.packets.versions.WrapperPlayServerScoreboardTeam_v13_16;
import ru.xezard.glow.packets.versions.WrapperPlayServerScoreboardTeam_v18_19;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
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

            case 19:
            case 18:
            default:
                this.wrapper = new WrapperPlayServerScoreboardTeam_v18_19();
                break;
        }

        //    public static final MinecraftVersion CAVES_CLIFFS_2 = new MinecraftVersion("1.18");
        //    public static final MinecraftVersion CAVES_CLIFFS_1 = new MinecraftVersion("1.17");
        //    public static final MinecraftVersion NETHER_UPDATE_2 = new MinecraftVersion("1.16.2");
        //    public static final MinecraftVersion NETHER_UPDATE = new MinecraftVersion("1.16");
        //    public static final MinecraftVersion BEE_UPDATE = new MinecraftVersion("1.15");
        //    public static final MinecraftVersion VILLAGE_UPDATE = new MinecraftVersion("1.14");
        //    public static final MinecraftVersion AQUATIC_UPDATE = new MinecraftVersion("1.13");
        //    public static final MinecraftVersion COLOR_UPDATE = new MinecraftVersion("1.12");
        //    public static final MinecraftVersion EXPLORATION_UPDATE = new MinecraftVersion("1.11");
        //    public static final MinecraftVersion FROSTBURN_UPDATE = new MinecraftVersion("1.10");
        //    public static final MinecraftVersion COMBAT_UPDATE = new MinecraftVersion("1.9");
        //    public static final MinecraftVersion BOUNTIFUL_UPDATE = new MinecraftVersion("1.8");
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