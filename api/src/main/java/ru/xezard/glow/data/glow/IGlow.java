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
package ru.xezard.glow.data.glow;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ru.xezard.glow.data.players.IPlayerViewable;

import java.util.Set;

public interface IGlow
extends IPlayerViewable {
    Set<Entity> getHolders();

    ChatColor getColor();

    String getName();

    default boolean hasHolder(Entity entity) {
        return entity != null && this.getHolders().contains(entity);
    }

    void setColor(ChatColor color);

    void addHolders(Entity... entities);

    void removeHolders(Entity... entities);

    void render(Player... players);

    default void broadcast() {
        this.display(this.getViewers());
    }

    void destroy();
}