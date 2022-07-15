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

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import ru.xezard.glow.data.glow.IGlow;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class GlowsManager
implements IGlowsManager {
    Set<IGlow> glows = ConcurrentHashMap.newKeySet();

    @NonFinal static volatile GlowsManager instance;

    public static GlowsManager getInstance() {
        if (instance == null) {
            synchronized (GlowsManager.class) {
                if (instance == null) {
                    instance = new GlowsManager();
                }
            }
        }

        return instance;
    }

    @Override
    public void addGlow(IGlow glow) {
        this.glows.add(glow);
    }

    @Override
    public void removeGlow(IGlow glow) {
        glow.destroy();

        this.glows.remove(glow);
    }

    @Override
    public Set<IGlow> getGlows() {
        return Collections.unmodifiableSet(this.glows);
    }
}