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

import com.google.common.collect.Iterators;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@EqualsAndHashCode
public class Animation<O>
implements IAnimation<O>
{
    private Iterator<O> iterator;

    @Getter
    protected List<O> values;

    @SafeVarargs
    public Animation(O... values)
    {
        this.values = Arrays.asList(values);

        this.iterator = Iterators.cycle(this.values);
    }

    public Animation(List<O> values)
    {
        this.iterator = Iterators.cycle(values);

        this.values = values;
    }

    @Override
    public O next()
    {
        return this.iterator.next();
    }
}