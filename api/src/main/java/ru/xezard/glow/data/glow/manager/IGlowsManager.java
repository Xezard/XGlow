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
package ru.xezard.glow.data.glow.manager;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ru.xezard.glow.data.glow.IGlow;

import java.util.Optional;
import java.util.Set;

public interface IGlowsManager {
    Set<IGlow> getGlows();

    default Optional<IGlow> getGlowByEntity(Entity entity) {
        return this.getGlows()
                   .stream()
                   .filter((glow) -> glow.hasHolder(entity))
                   .findFirst();
    }

    void addGlow(IGlow glow);

    void removeGlow(IGlow glow);

    default void removeGlowFrom(Entity entity) {
        this.getGlows().forEach((glow) -> glow.removeHolders(entity));
    }

    default void removeViewer(Player viewer) {
        this.getGlows().forEach((glow) -> glow.hideFrom(viewer));
    }

    default void clear() {
        this.getGlows().forEach(this::removeGlow);
    }
}