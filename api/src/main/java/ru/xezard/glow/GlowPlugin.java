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
package ru.xezard.glow;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.xezard.glow.data.glow.manager.GlowsManager;
import ru.xezard.glow.listeners.EntityDeathListener;
import ru.xezard.glow.listeners.PlayerQuitListener;

public class GlowPlugin
extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        this.registerListeners();
        this.registerPacketListener();
    }

    private void registerPacketListener()
    {
        ProtocolLibrary.getProtocolManager().addPacketListener
        (
                new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_METADATA)
                {
                    @Override
                    public void onPacketSending(PacketEvent event)
                    {
                        if (event.getPacketType() != PacketType.Play.Server.ENTITY_METADATA)
                        {
                            return;
                        }

                        PacketContainer packet = event.getPacket();

                        Entity entity = packet.getEntityModifier(event).read(0);

                        GlowsManager.getInstance().getGlowByEntity(entity).ifPresent((glow) ->
                        {
                            Player player = event.getPlayer();

                            if (!glow.sees(player))
                            {
                                return;
                            }

                            WrappedDataWatcher dataWatcher = WrappedDataWatcher.getEntityWatcher(entity);

                            WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class);

                            byte mask = dataWatcher.getByte(0);

                            mask |= 0x40;

                            dataWatcher.setObject(0, byteSerializer, mask);

                            packet.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

                            event.setPacket(packet);
                        });
                    }
                }
        );
    }

    private void registerListeners()
    {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new EntityDeathListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
    }
}