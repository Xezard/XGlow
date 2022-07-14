package ru.xezard.glow.data.glow.processor;

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import ru.xezard.glow.packets.AbstractPacket;
import ru.xezard.glow.packets.WrapperPlayServerEntityMetadata;
import ru.xezard.glow.packets.WrapperPlayServerScoreboardTeam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GlowProcessor
implements IGlowProcessor {
    static WrappedDataWatcher.Serializer BYTE_SERIALIZER =
            WrappedDataWatcher.Registry.get(Byte.class);

    String teamName;

    @Override
    public List<AbstractPacket> createGlowPackets(Set<Entity> entities, boolean glow) {
        return entities.stream()
                       .map((entity) -> this.createGlowPacket(entity, glow))
                       .collect(Collectors.toList());
    }

    @Override
    public AbstractPacket createTeamPacket(Set<Entity> holders, ChatColor color, int mode) {
        WrapperPlayServerScoreboardTeam team = new WrapperPlayServerScoreboardTeam();

        team.setTeamName(this.teamName);
        team.setDisplayName(WrappedChatComponent.fromText(this.teamName));
        team.setMode(mode);

        if (mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED) {
            return team;
        }

        team.setColor(color);
        team.setNameTagVisibility("ALWAYS");

        team.setPlayers(holders.stream()
                .map((holder) -> holder instanceof OfflinePlayer ?
                        holder.getName() : holder.getUniqueId().toString())
                .collect(Collectors.toList()));

        return team;
    }

    @Override
    public AbstractPacket createGlowPacket(Entity entity, boolean glow) {
        List<WrappedWatchableObject> metadata = this.createDataWatcher(entity, glow).getWatchableObjects();

        return new WrapperPlayServerEntityMetadata() {{
            this.setEntityId(entity.getEntityId());
            this.setMetadata(metadata);
        }};
    }

    private WrappedDataWatcher createDataWatcher(Entity entity, boolean enableGlow) {
        WrappedDataWatcher dataWatcher = WrappedDataWatcher.getEntityWatcher(entity).deepClone();

        byte mask = dataWatcher.getByte(0);

        if (enableGlow) {
            mask |= 0x40;
        }

        dataWatcher.setEntity(entity);
        dataWatcher.setObject(0, BYTE_SERIALIZER, mask);

        return dataWatcher;
    }
}