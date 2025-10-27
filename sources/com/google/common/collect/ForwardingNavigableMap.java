package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.SortedMap;

@GwtIncompatible
/* loaded from: classes4.dex */
public abstract class ForwardingNavigableMap<K, V> extends ForwardingSortedMap<K, V> implements NavigableMap<K, V> {

    @Beta
    public class StandardDescendingMap extends Maps.DescendingMap<K, V> {
        public StandardDescendingMap() {
        }

        @Override // com.google.common.collect.Maps.DescendingMap
        public Iterator<Map.Entry<K, V>> entryIterator() {
            return new Iterator<Map.Entry<K, V>>() { // from class: com.google.common.collect.ForwardingNavigableMap.StandardDescendingMap.1
                private Map.Entry<K, V> nextOrNull;
                private Map.Entry<K, V> toRemove = null;

                {
                    this.nextOrNull = StandardDescendingMap.this.forward().lastEntry();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.nextOrNull != null;
                }

                @Override // java.util.Iterator
                public void remove() {
                    CollectPreconditions.checkRemove(this.toRemove != null);
                    StandardDescendingMap.this.forward().remove(this.toRemove.getKey());
                    this.toRemove = null;
                }

                @Override // java.util.Iterator
                public Map.Entry<K, V> next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    try {
                        Map.Entry<K, V> entry = this.nextOrNull;
                        this.toRemove = entry;
                        this.nextOrNull = StandardDescendingMap.this.forward().lowerEntry(this.nextOrNull.getKey());
                        return entry;
                    } catch (Throwable th) {
                        this.toRemove = this.nextOrNull;
                        this.nextOrNull = StandardDescendingMap.this.forward().lowerEntry(this.nextOrNull.getKey());
                        throw th;
                    }
                }
            };
        }

        @Override // com.google.common.collect.Maps.DescendingMap
        public NavigableMap<K, V> forward() {
            return ForwardingNavigableMap.this;
        }
    }

    @Beta
    public class StandardNavigableKeySet extends Maps.NavigableKeySet<K, V> {
        public StandardNavigableKeySet() {
            super(ForwardingNavigableMap.this);
        }
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> ceilingEntry(K k2) {
        return delegate().ceilingEntry(k2);
    }

    @Override // java.util.NavigableMap
    public K ceilingKey(K k2) {
        return delegate().ceilingKey(k2);
    }

    @Override // com.google.common.collect.ForwardingSortedMap, com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
    public abstract NavigableMap<K, V> delegate();

    @Override // java.util.NavigableMap
    public NavigableSet<K> descendingKeySet() {
        return delegate().descendingKeySet();
    }

    @Override // java.util.NavigableMap
    public NavigableMap<K, V> descendingMap() {
        return delegate().descendingMap();
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> firstEntry() {
        return delegate().firstEntry();
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> floorEntry(K k2) {
        return delegate().floorEntry(k2);
    }

    @Override // java.util.NavigableMap
    public K floorKey(K k2) {
        return delegate().floorKey(k2);
    }

    @Override // java.util.NavigableMap
    public NavigableMap<K, V> headMap(K k2, boolean z2) {
        return delegate().headMap(k2, z2);
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> higherEntry(K k2) {
        return delegate().higherEntry(k2);
    }

    @Override // java.util.NavigableMap
    public K higherKey(K k2) {
        return delegate().higherKey(k2);
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> lastEntry() {
        return delegate().lastEntry();
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> lowerEntry(K k2) {
        return delegate().lowerEntry(k2);
    }

    @Override // java.util.NavigableMap
    public K lowerKey(K k2) {
        return delegate().lowerKey(k2);
    }

    @Override // java.util.NavigableMap
    public NavigableSet<K> navigableKeySet() {
        return delegate().navigableKeySet();
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> pollFirstEntry() {
        return delegate().pollFirstEntry();
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> pollLastEntry() {
        return delegate().pollLastEntry();
    }

    public Map.Entry<K, V> standardCeilingEntry(K k2) {
        return tailMap(k2, true).firstEntry();
    }

    public K standardCeilingKey(K k2) {
        return (K) Maps.keyOrNull(ceilingEntry(k2));
    }

    @Beta
    public NavigableSet<K> standardDescendingKeySet() {
        return descendingMap().navigableKeySet();
    }

    public Map.Entry<K, V> standardFirstEntry() {
        return (Map.Entry) Iterables.getFirst(entrySet(), null);
    }

    public K standardFirstKey() {
        Map.Entry<K, V> entryFirstEntry = firstEntry();
        if (entryFirstEntry != null) {
            return entryFirstEntry.getKey();
        }
        throw new NoSuchElementException();
    }

    public Map.Entry<K, V> standardFloorEntry(K k2) {
        return headMap(k2, true).lastEntry();
    }

    public K standardFloorKey(K k2) {
        return (K) Maps.keyOrNull(floorEntry(k2));
    }

    public SortedMap<K, V> standardHeadMap(K k2) {
        return headMap(k2, false);
    }

    public Map.Entry<K, V> standardHigherEntry(K k2) {
        return tailMap(k2, false).firstEntry();
    }

    public K standardHigherKey(K k2) {
        return (K) Maps.keyOrNull(higherEntry(k2));
    }

    public Map.Entry<K, V> standardLastEntry() {
        return (Map.Entry) Iterables.getFirst(descendingMap().entrySet(), null);
    }

    public K standardLastKey() {
        Map.Entry<K, V> entryLastEntry = lastEntry();
        if (entryLastEntry != null) {
            return entryLastEntry.getKey();
        }
        throw new NoSuchElementException();
    }

    public Map.Entry<K, V> standardLowerEntry(K k2) {
        return headMap(k2, false).lastEntry();
    }

    public K standardLowerKey(K k2) {
        return (K) Maps.keyOrNull(lowerEntry(k2));
    }

    public Map.Entry<K, V> standardPollFirstEntry() {
        return (Map.Entry) Iterators.pollNext(entrySet().iterator());
    }

    public Map.Entry<K, V> standardPollLastEntry() {
        return (Map.Entry) Iterators.pollNext(descendingMap().entrySet().iterator());
    }

    @Override // com.google.common.collect.ForwardingSortedMap
    public SortedMap<K, V> standardSubMap(K k2, K k3) {
        return subMap(k2, true, k3, false);
    }

    public SortedMap<K, V> standardTailMap(K k2) {
        return tailMap(k2, true);
    }

    @Override // java.util.NavigableMap
    public NavigableMap<K, V> subMap(K k2, boolean z2, K k3, boolean z3) {
        return delegate().subMap(k2, z2, k3, z3);
    }

    @Override // java.util.NavigableMap
    public NavigableMap<K, V> tailMap(K k2, boolean z2) {
        return delegate().tailMap(k2, z2);
    }
}
