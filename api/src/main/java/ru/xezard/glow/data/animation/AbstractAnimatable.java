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
package ru.xezard.glow.data.animation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

@Getter
@NoArgsConstructor
public abstract class AbstractAnimatable
implements IAnimatable
{
    private Plugin plugin;

    private BukkitTask animationTask;

    // update period in ticks
    protected long updatePeriod;

    protected boolean async,
                      animated;

    public AbstractAnimatable(Plugin plugin, long updatePeriod)
    {
        this(plugin, updatePeriod, false);
    }

    public AbstractAnimatable(Plugin plugin, long updatePeriod, boolean async)
    {
        this.plugin = plugin;

        this.updatePeriod = updatePeriod;

        this.async = async;
    }

    public void startAnimation()
    {
        if (this.updatePeriod < 1)
        {
            return;
        }

        this.animated = true;

        if (this.animationTask != null)
        {
            this.animationTask.cancel();
            this.animationTask = null;
        }

        if (this.async)
        {
            this.animationTask = Bukkit.getScheduler().runTaskTimerAsynchronously
            (
                    this.plugin,
                    this::tick,
                    0L,
                    this.updatePeriod
            );
        } else {
            this.animationTask = Bukkit.getScheduler().runTaskTimer
            (
                    this.plugin,
                    this::tick,
                    0L,
                    this.updatePeriod
            );
        }
    }

    public void stopAnimation()
    {
        if (this.animationTask == null)
        {
            return;
        }

        this.animated = false;

        this.animationTask.cancel();
    }
}