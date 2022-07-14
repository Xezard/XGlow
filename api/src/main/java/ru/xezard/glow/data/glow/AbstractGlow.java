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

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import ru.xezard.glow.data.glow.manager.GlowsManager;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ru.xezard.glow.data.glow.processor.GlowProcessor;
import ru.xezard.glow.data.glow.processor.IGlowProcessor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractGlow
implements IGlow {
    Set<Player> viewers = new HashSet<> ();
    Set<Entity> holders = new HashSet<> ();

    IGlowProcessor processor;

    @Getter
    @NonFinal ChatColor color;

    @Getter
    String name;

    public AbstractGlow(ChatColor color, String name) {
        this.processor = new GlowProcessor(name);
        this.color = color;
        this.name = name;

        GlowsManager.getInstance().addGlow(this);
    }

    @Override
    public void setColor(ChatColor color) {
        this.color = color;
        this.broadcast();
    }

    @Override
    public void broadcast() {
        this.display(this.viewers);
    }

    @Override
    public Set<Entity> getHolders() {
        return Collections.unmodifiableSet(this.holders);
    }

    @Override
    public Set<Player> getViewers() {
        return Collections.unmodifiableSet(this.viewers);
    }
}