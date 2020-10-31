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

import ru.xezard.glow.data.glow.IGlow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class GlowsManager
implements IGlowsManager
{
    private final List<IGlow> glows = new ArrayList<> ();

    private static volatile GlowsManager instance;

    public static GlowsManager getInstance()
    {
        if (instance == null)
        {
            synchronized (GlowsManager.class)
            {
                if (instance == null)
                {
                    instance = new GlowsManager();
                }
            }
        }

        return instance;
    }

    @Override
    public Optional<IGlow> getGlowByEntity(Entity entity)
    {
        return this.glows.stream()
                         .filter((glow) -> glow.hasHolder(entity))
                         .findFirst();
    }

    @Override
    public void addGlow(IGlow glow)
    {
        this.glows.add(glow);
    }

    @Override
    public void removeGlow(IGlow glow)
    {
        glow.destroy();

        this.glows.remove(glow);
    }

    @Override
    public void removeGlowFrom(Entity entity)
    {
        this.glows.forEach((glow) -> glow.removeHolders(entity));
    }

    @Override
    public void removeViewer(Player viewer)
    {
        this.glows.forEach((glow) -> glow.hideFrom(viewer));
    }

    @Override
    public void clear()
    {
        this.glows.forEach(this::removeGlow);
    }

    @Override
    public List<IGlow> getGlows()
    {
        return new ArrayList<> (this.glows);
    }
}