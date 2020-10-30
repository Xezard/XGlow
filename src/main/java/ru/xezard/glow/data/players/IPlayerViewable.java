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
package ru.xezard.glow.data.players;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

public interface IPlayerViewable
{
    Set<Player> getViewers();

    default boolean hasViewers()
    {
        return !this.getViewers().isEmpty();
    }

    default boolean sees(Player... possibleViewers)
    {
        return Arrays.stream(possibleViewers).anyMatch(this.getViewers()::contains);
    }

    default void display(Iterable<? extends Player> viewers)
    {
        viewers.forEach(this::display);
    }

    void display(Player... viewers);

    default void display(UUID... uuids)
    {
        for (UUID uuid : uuids)
        {
            Player player = Bukkit.getPlayer(uuid);

            if (player == null || player.isOnline())
            {
                continue;
            }

            this.display(player);
        }
    }

    default void hideFrom(Iterable<? extends Player> viewers)
    {
        viewers.forEach(this::hideFrom);
    }

    void hideFrom(Player... viewers);

    default void hideFrom(UUID... uuids)
    {
        for (UUID uuid : uuids)
        {
            Player player = Bukkit.getPlayer(uuid);

            if (player == null || player.isOnline())
            {
                continue;
            }

            this.hideFrom(player);
        }
    }

    default void hideFromEveryone()
    {
        this.getViewers().forEach((viewer) ->
        {
            if (viewer == null || viewer.isOnline())
            {
                return;
            }

            this.hideFrom(viewer);
        });
    }
}