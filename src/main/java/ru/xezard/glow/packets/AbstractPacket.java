package ru.xezard.glow.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractPacket
{
	@Getter
	protected PacketContainer handle;

	protected AbstractPacket(PacketContainer handle, PacketType type)
	{
		Preconditions.checkNotNull(handle, "packet handle cannot be null");
		Preconditions.checkArgument(Objects.equal(handle.getType(), type), handle.getHandle() + " is not a packet of type " + type);

		this.handle = handle;
	}

	public void sendPacket(Iterable<Player> recipients)
	{
		for (Player receiver : recipients)
		{
			try {
				ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, this.handle);
			} catch (InvocationTargetException e) {
				throw new RuntimeException("Cannot send packet", e);
			}
		}
	}

	public void sendPacket(Player... recipients)
	{
		for (Player receiver : recipients)
		{
			try {
				ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, this.handle);
			} catch (InvocationTargetException e) {
				throw new RuntimeException("Cannot send packet", e);
			}
		}
	}

	public void broadcastPacket()
	{
		ProtocolLibrary.getProtocolManager().broadcastServerPacket(getHandle());
	}

	public void receivePacket(Player sender)
	{
		try {
			ProtocolLibrary.getProtocolManager().recieveClientPacket(sender, this.handle);
		} catch (Exception e) {
			throw new RuntimeException("Cannot receive packet: ", e);
		}
	}
}