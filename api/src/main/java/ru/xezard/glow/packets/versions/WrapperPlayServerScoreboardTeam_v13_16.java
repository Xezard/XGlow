package ru.xezard.glow.packets.versions;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.ChatColor;
import ru.xezard.glow.packets.AbstractWrapperPlayServerScoreboardTeam;

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
        return NameTagVisibility.valueOf(this.handle.getStrings().read(1).toUpperCase());
    }

    @Override
    public void setNameTagVisibility(NameTagVisibility nameTagVisibility) {
        this.handle.getStrings().write(1, nameTagVisibility.name());
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
        return CollisionRule.valueOf(this.handle.getStrings().read(2).toUpperCase());
    }

    @Override
    public void setCollisionRule(CollisionRule value) {
        this.handle.getStrings().write(2, value.name());
    }
}
