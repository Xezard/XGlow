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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.xezard.glow.data.animation.AbstractAnimatable;
import ru.xezard.glow.data.animation.Animation;
import ru.xezard.glow.data.animation.IAnimation;
import ru.xezard.glow.data.glow.manager.GlowsManager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
public abstract class AbstractGlow
extends AbstractAnimatable
implements IGlow
{
    protected Set<Player> viewers = new HashSet<> ();
    protected Set<Entity> holders = new HashSet<> ();

    @Getter
    protected IAnimation<ChatColor> animatedColor;

    @Getter
    protected String name;

    public AbstractGlow(IAnimation<ChatColor> animatedDisplayNameColor, Plugin plugin, long updatePeriod, String name, boolean async)
    {
        super(plugin, updatePeriod, async);

        this.animatedColor = animatedDisplayNameColor;
        this.name = name;

        this.name = name;

        GlowsManager.getInstance().addGlow(this);
    }

    @Override
    public ChatColor getColor()
    {
        return this.animatedColor.next();
    }

    @Override
    public void setColor(IAnimation<ChatColor> animatedColor)
    {
        this.animatedColor = animatedColor;
    }

    @Override
    public void setColor(ChatColor color)
    {
        this.animatedColor = new Animation<> (color);
    }

    @Override
    public void broadcast()
    {
        this.display(this.viewers);
    }

    @Override
    public Set<Entity> getHolders()
    {
        return Collections.unmodifiableSet(this.holders);
    }

    @Override
    public Set<Player> getViewers()
    {
        return Collections.unmodifiableSet(this.viewers);
    }

    @Override
    public void tick()
    {
        this.broadcast();
    }
}