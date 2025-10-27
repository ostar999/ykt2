package com.google.common.cache;

import cn.hutool.core.text.StrPool;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.cache.AbstractCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.google.j2objc.annotations.Weak;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes4.dex */
class LocalCache<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {
    static final int CONTAINS_VALUE_RETRIES = 3;
    static final int DRAIN_MAX = 16;
    static final int DRAIN_THRESHOLD = 63;
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MAX_SEGMENTS = 65536;
    final int concurrencyLevel;

    @NullableDecl
    final CacheLoader<? super K, V> defaultLoader;
    final EntryFactory entryFactory;

    @MonotonicNonNullDecl
    Set<Map.Entry<K, V>> entrySet;
    final long expireAfterAccessNanos;
    final long expireAfterWriteNanos;
    final AbstractCache.StatsCounter globalStatsCounter;
    final Equivalence<Object> keyEquivalence;

    @MonotonicNonNullDecl
    Set<K> keySet;
    final Strength keyStrength;
    final long maxWeight;
    final long refreshNanos;
    final RemovalListener<K, V> removalListener;
    final Queue<RemovalNotification<K, V>> removalNotificationQueue;
    final int segmentMask;
    final int segmentShift;
    final Segment<K, V>[] segments;
    final Ticker ticker;
    final Equivalence<Object> valueEquivalence;
    final Strength valueStrength;

    @MonotonicNonNullDecl
    Collection<V> values;
    final Weigher<K, V> weigher;
    static final Logger logger = Logger.getLogger(LocalCache.class.getName());
    static final ValueReference<Object, Object> UNSET = new ValueReference<Object, Object>() { // from class: com.google.common.cache.LocalCache.1
        @Override // com.google.common.cache.LocalCache.ValueReference
        public ValueReference<Object, Object> copyFor(ReferenceQueue<Object> referenceQueue, @NullableDecl Object obj, ReferenceEntry<Object, Object> referenceEntry) {
            return this;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public Object get() {
            return null;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ReferenceEntry<Object, Object> getEntry() {
            return null;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return 0;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isActive() {
            return false;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isLoading() {
            return false;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public void notifyNewValue(Object obj) {
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public Object waitForValue() {
            return null;
        }
    };
    static final Queue<?> DISCARDING_QUEUE = new AbstractQueue<Object>() { // from class: com.google.common.cache.LocalCache.2
        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<Object> iterator() {
            return ImmutableSet.of().iterator();
        }

        @Override // java.util.Queue
        public boolean offer(Object obj) {
            return true;
        }

        @Override // java.util.Queue
        public Object peek() {
            return null;
        }

        @Override // java.util.Queue
        public Object poll() {
            return null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return 0;
        }
    };

    public abstract class AbstractCacheSet<T> extends AbstractSet<T> {

        @Weak
        final ConcurrentMap<?, ?> map;

        public AbstractCacheSet(ConcurrentMap<?, ?> concurrentMap) {
            this.map = concurrentMap;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            this.map.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            return LocalCache.toArrayList(this).toArray();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public <E> E[] toArray(E[] eArr) {
            return (E[]) LocalCache.toArrayList(this).toArray(eArr);
        }
    }

    public static abstract class AbstractReferenceEntry<K, V> implements ReferenceEntry<K, V> {
        @Override // com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public int getHash() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public K getKey() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNext() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ValueReference<K, V> getValueReference() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setValueReference(ValueReference<K, V> valueReference) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j2) {
            throw new UnsupportedOperationException();
        }
    }

    public static final class AccessQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>() { // from class: com.google.common.cache.LocalCache.AccessQueue.1
            ReferenceEntry<K, V> nextAccess = this;
            ReferenceEntry<K, V> previousAccess = this;

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public long getAccessTime() {
                return Long.MAX_VALUE;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public ReferenceEntry<K, V> getNextInAccessQueue() {
                return this.nextAccess;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public ReferenceEntry<K, V> getPreviousInAccessQueue() {
                return this.previousAccess;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setAccessTime(long j2) {
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
                this.nextAccess = referenceEntry;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
                this.previousAccess = referenceEntry;
            }
        };

        @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
        public void clear() {
            ReferenceEntry<K, V> nextInAccessQueue = this.head.getNextInAccessQueue();
            while (true) {
                ReferenceEntry<K, V> referenceEntry = this.head;
                if (nextInAccessQueue == referenceEntry) {
                    referenceEntry.setNextInAccessQueue(referenceEntry);
                    ReferenceEntry<K, V> referenceEntry2 = this.head;
                    referenceEntry2.setPreviousInAccessQueue(referenceEntry2);
                    return;
                } else {
                    ReferenceEntry<K, V> nextInAccessQueue2 = nextInAccessQueue.getNextInAccessQueue();
                    LocalCache.nullifyAccessOrder(nextInAccessQueue);
                    nextInAccessQueue = nextInAccessQueue2;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return ((ReferenceEntry) obj).getNextInAccessQueue() != NullEntry.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.head.getNextInAccessQueue() == this.head;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(peek()) { // from class: com.google.common.cache.LocalCache.AccessQueue.2
                @Override // com.google.common.collect.AbstractSequentialIterator
                public ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> referenceEntry) {
                    ReferenceEntry<K, V> nextInAccessQueue = referenceEntry.getNextInAccessQueue();
                    if (nextInAccessQueue == AccessQueue.this.head) {
                        return null;
                    }
                    return nextInAccessQueue;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object obj) {
            ReferenceEntry referenceEntry = (ReferenceEntry) obj;
            ReferenceEntry<K, V> previousInAccessQueue = referenceEntry.getPreviousInAccessQueue();
            ReferenceEntry<K, V> nextInAccessQueue = referenceEntry.getNextInAccessQueue();
            LocalCache.connectAccessOrder(previousInAccessQueue, nextInAccessQueue);
            LocalCache.nullifyAccessOrder(referenceEntry);
            return nextInAccessQueue != NullEntry.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            int i2 = 0;
            for (ReferenceEntry<K, V> nextInAccessQueue = this.head.getNextInAccessQueue(); nextInAccessQueue != this.head; nextInAccessQueue = nextInAccessQueue.getNextInAccessQueue()) {
                i2++;
            }
            return i2;
        }

        @Override // java.util.Queue
        public boolean offer(ReferenceEntry<K, V> referenceEntry) {
            LocalCache.connectAccessOrder(referenceEntry.getPreviousInAccessQueue(), referenceEntry.getNextInAccessQueue());
            LocalCache.connectAccessOrder(this.head.getPreviousInAccessQueue(), referenceEntry);
            LocalCache.connectAccessOrder(referenceEntry, this.head);
            return true;
        }

        @Override // java.util.Queue
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> nextInAccessQueue = this.head.getNextInAccessQueue();
            if (nextInAccessQueue == this.head) {
                return null;
            }
            return nextInAccessQueue;
        }

        @Override // java.util.Queue
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> nextInAccessQueue = this.head.getNextInAccessQueue();
            if (nextInAccessQueue == this.head) {
                return null;
            }
            remove(nextInAccessQueue);
            return nextInAccessQueue;
        }
    }

    public enum EntryFactory {
        STRONG { // from class: com.google.common.cache.LocalCache.EntryFactory.1
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new StrongEntry(k2, i2, referenceEntry);
            }
        },
        STRONG_ACCESS { // from class: com.google.common.cache.LocalCache.EntryFactory.2
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessEntry(k2, i2, referenceEntry);
            }
        },
        STRONG_WRITE { // from class: com.google.common.cache.LocalCache.EntryFactory.3
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new StrongWriteEntry(k2, i2, referenceEntry);
            }
        },
        STRONG_ACCESS_WRITE { // from class: com.google.common.cache.LocalCache.EntryFactory.4
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessWriteEntry(k2, i2, referenceEntry);
            }
        },
        WEAK { // from class: com.google.common.cache.LocalCache.EntryFactory.5
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new WeakEntry(segment.keyReferenceQueue, k2, i2, referenceEntry);
            }
        },
        WEAK_ACCESS { // from class: com.google.common.cache.LocalCache.EntryFactory.6
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessEntry(segment.keyReferenceQueue, k2, i2, referenceEntry);
            }
        },
        WEAK_WRITE { // from class: com.google.common.cache.LocalCache.EntryFactory.7
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new WeakWriteEntry(segment.keyReferenceQueue, k2, i2, referenceEntry);
            }
        },
        WEAK_ACCESS_WRITE { // from class: com.google.common.cache.LocalCache.EntryFactory.8
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessWriteEntry(segment.keyReferenceQueue, k2, i2, referenceEntry);
            }
        };

        static final int ACCESS_MASK = 1;
        static final int WEAK_MASK = 4;
        static final int WRITE_MASK = 2;
        static final EntryFactory[] factories = {STRONG, STRONG_ACCESS, STRONG_WRITE, STRONG_ACCESS_WRITE, WEAK, WEAK_ACCESS, WEAK_WRITE, WEAK_ACCESS_WRITE};

        public static EntryFactory getFactory(Strength strength, boolean z2, boolean z3) {
            return factories[(strength == Strength.WEAK ? 4 : 0) | (z2 ? 1 : 0) | (z3 ? 2 : 0)];
        }

        public <K, V> void copyAccessEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            referenceEntry2.setAccessTime(referenceEntry.getAccessTime());
            LocalCache.connectAccessOrder(referenceEntry.getPreviousInAccessQueue(), referenceEntry2);
            LocalCache.connectAccessOrder(referenceEntry2, referenceEntry.getNextInAccessQueue());
            LocalCache.nullifyAccessOrder(referenceEntry);
        }

        public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            return newEntry(segment, referenceEntry.getKey(), referenceEntry.getHash(), referenceEntry2);
        }

        public <K, V> void copyWriteEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            referenceEntry2.setWriteTime(referenceEntry.getWriteTime());
            LocalCache.connectWriteOrder(referenceEntry.getPreviousInWriteQueue(), referenceEntry2);
            LocalCache.connectWriteOrder(referenceEntry2, referenceEntry.getNextInWriteQueue());
            LocalCache.nullifyWriteOrder(referenceEntry);
        }

        public abstract <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry);
    }

    public final class EntryIterator extends LocalCache<K, V>.HashIterator<Map.Entry<K, V>> {
        public EntryIterator() {
            super();
        }

        @Override // com.google.common.cache.LocalCache.HashIterator, java.util.Iterator
        public Map.Entry<K, V> next() {
            return nextEntry();
        }
    }

    public final class EntrySet extends LocalCache<K, V>.AbstractCacheSet<Map.Entry<K, V>> {
        public EntrySet(ConcurrentMap<?, ?> concurrentMap) {
            super(concurrentMap);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            Map.Entry entry;
            Object key;
            Object obj2;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && (obj2 = LocalCache.this.get(key)) != null && LocalCache.this.valueEquivalence.equivalent(entry.getValue(), obj2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            Map.Entry entry;
            Object key;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && LocalCache.this.remove(key, entry.getValue());
        }
    }

    public abstract class HashIterator<T> implements Iterator<T> {

        @MonotonicNonNullDecl
        Segment<K, V> currentSegment;

        @MonotonicNonNullDecl
        AtomicReferenceArray<ReferenceEntry<K, V>> currentTable;

        @NullableDecl
        LocalCache<K, V>.WriteThroughEntry lastReturned;

        @NullableDecl
        ReferenceEntry<K, V> nextEntry;

        @NullableDecl
        LocalCache<K, V>.WriteThroughEntry nextExternal;
        int nextSegmentIndex;
        int nextTableIndex = -1;

        public HashIterator() {
            this.nextSegmentIndex = LocalCache.this.segments.length - 1;
            advance();
        }

        public final void advance() {
            this.nextExternal = null;
            if (nextInChain() || nextInTable()) {
                return;
            }
            while (true) {
                int i2 = this.nextSegmentIndex;
                if (i2 < 0) {
                    return;
                }
                Segment<K, V>[] segmentArr = LocalCache.this.segments;
                this.nextSegmentIndex = i2 - 1;
                Segment<K, V> segment = segmentArr[i2];
                this.currentSegment = segment;
                if (segment.count != 0) {
                    this.currentTable = this.currentSegment.table;
                    this.nextTableIndex = r0.length() - 1;
                    if (nextInTable()) {
                        return;
                    }
                }
            }
        }

        public boolean advanceTo(ReferenceEntry<K, V> referenceEntry) {
            try {
                long j2 = LocalCache.this.ticker.read();
                K key = referenceEntry.getKey();
                Object liveValue = LocalCache.this.getLiveValue(referenceEntry, j2);
                if (liveValue == null) {
                    this.currentSegment.postReadCleanup();
                    return false;
                }
                this.nextExternal = new WriteThroughEntry(key, liveValue);
                this.currentSegment.postReadCleanup();
                return true;
            } catch (Throwable th) {
                this.currentSegment.postReadCleanup();
                throw th;
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.nextExternal != null;
        }

        @Override // java.util.Iterator
        public abstract T next();

        public LocalCache<K, V>.WriteThroughEntry nextEntry() {
            LocalCache<K, V>.WriteThroughEntry writeThroughEntry = this.nextExternal;
            if (writeThroughEntry == null) {
                throw new NoSuchElementException();
            }
            this.lastReturned = writeThroughEntry;
            advance();
            return this.lastReturned;
        }

        public boolean nextInChain() {
            ReferenceEntry<K, V> referenceEntry = this.nextEntry;
            if (referenceEntry == null) {
                return false;
            }
            while (true) {
                this.nextEntry = referenceEntry.getNext();
                ReferenceEntry<K, V> referenceEntry2 = this.nextEntry;
                if (referenceEntry2 == null) {
                    return false;
                }
                if (advanceTo(referenceEntry2)) {
                    return true;
                }
                referenceEntry = this.nextEntry;
            }
        }

        public boolean nextInTable() {
            while (true) {
                int i2 = this.nextTableIndex;
                if (i2 < 0) {
                    return false;
                }
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.currentTable;
                this.nextTableIndex = i2 - 1;
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(i2);
                this.nextEntry = referenceEntry;
                if (referenceEntry != null && (advanceTo(referenceEntry) || nextInChain())) {
                    return true;
                }
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            Preconditions.checkState(this.lastReturned != null);
            LocalCache.this.remove(this.lastReturned.getKey());
            this.lastReturned = null;
        }
    }

    public final class KeyIterator extends LocalCache<K, V>.HashIterator<K> {
        public KeyIterator() {
            super();
        }

        @Override // com.google.common.cache.LocalCache.HashIterator, java.util.Iterator
        public K next() {
            return nextEntry().getKey();
        }
    }

    public final class KeySet extends LocalCache<K, V>.AbstractCacheSet<K> {
        public KeySet(ConcurrentMap<?, ?> concurrentMap) {
            super(concurrentMap);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return this.map.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return this.map.remove(obj) != null;
        }
    }

    public static final class LoadingSerializationProxy<K, V> extends ManualSerializationProxy<K, V> implements LoadingCache<K, V>, Serializable {
        private static final long serialVersionUID = 1;

        @MonotonicNonNullDecl
        transient LoadingCache<K, V> autoDelegate;

        public LoadingSerializationProxy(LocalCache<K, V> localCache) {
            super(localCache);
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            this.autoDelegate = (LoadingCache<K, V>) recreateCacheBuilder().build(this.loader);
        }

        private Object readResolve() {
            return this.autoDelegate;
        }

        @Override // com.google.common.cache.LoadingCache, com.google.common.base.Function
        public final V apply(K k2) {
            return this.autoDelegate.apply(k2);
        }

        @Override // com.google.common.cache.LoadingCache
        public V get(K k2) throws ExecutionException {
            return this.autoDelegate.get(k2);
        }

        @Override // com.google.common.cache.LoadingCache
        public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
            return this.autoDelegate.getAll(iterable);
        }

        @Override // com.google.common.cache.LoadingCache
        public V getUnchecked(K k2) {
            return this.autoDelegate.getUnchecked(k2);
        }

        @Override // com.google.common.cache.LoadingCache
        public void refresh(K k2) {
            this.autoDelegate.refresh(k2);
        }
    }

    public static class LoadingValueReference<K, V> implements ValueReference<K, V> {
        final SettableFuture<V> futureValue;
        volatile ValueReference<K, V> oldValue;
        final Stopwatch stopwatch;

        public LoadingValueReference() {
            this(LocalCache.unset());
        }

        private ListenableFuture<V> fullyFailedFuture(Throwable th) {
            return Futures.immediateFailedFuture(th);
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, @NullableDecl V v2, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        public long elapsedNanos() {
            return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V get() {
            return this.oldValue.get();
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        public ValueReference<K, V> getOldValue() {
            return this.oldValue;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return this.oldValue.getWeight();
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isActive() {
            return this.oldValue.isActive();
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isLoading() {
            return true;
        }

        public ListenableFuture<V> loadFuture(K k2, CacheLoader<? super K, V> cacheLoader) {
            try {
                this.stopwatch.start();
                V v2 = this.oldValue.get();
                if (v2 == null) {
                    V vLoad = cacheLoader.load(k2);
                    return set(vLoad) ? this.futureValue : Futures.immediateFuture(vLoad);
                }
                ListenableFuture<V> listenableFutureReload = cacheLoader.reload(k2, v2);
                return listenableFutureReload == null ? Futures.immediateFuture(null) : Futures.transform(listenableFutureReload, new Function<V, V>() { // from class: com.google.common.cache.LocalCache.LoadingValueReference.1
                    @Override // com.google.common.base.Function
                    public V apply(V v3) {
                        LoadingValueReference.this.set(v3);
                        return v3;
                    }
                }, MoreExecutors.directExecutor());
            } catch (Throwable th) {
                ListenableFuture<V> listenableFutureFullyFailedFuture = setException(th) ? this.futureValue : fullyFailedFuture(th);
                if (th instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                return listenableFutureFullyFailedFuture;
            }
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public void notifyNewValue(@NullableDecl V v2) {
            if (v2 != null) {
                set(v2);
            } else {
                this.oldValue = LocalCache.unset();
            }
        }

        public boolean set(@NullableDecl V v2) {
            return this.futureValue.set(v2);
        }

        public boolean setException(Throwable th) {
            return this.futureValue.setException(th);
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V waitForValue() throws ExecutionException {
            return (V) Uninterruptibles.getUninterruptibly(this.futureValue);
        }

        public LoadingValueReference(ValueReference<K, V> valueReference) {
            this.futureValue = SettableFuture.create();
            this.stopwatch = Stopwatch.createUnstarted();
            this.oldValue = valueReference;
        }
    }

    public static class LocalLoadingCache<K, V> extends LocalManualCache<K, V> implements LoadingCache<K, V> {
        private static final long serialVersionUID = 1;

        public LocalLoadingCache(CacheBuilder<? super K, ? super V> cacheBuilder, CacheLoader<? super K, V> cacheLoader) {
            super();
        }

        @Override // com.google.common.cache.LoadingCache, com.google.common.base.Function
        public final V apply(K k2) {
            return getUnchecked(k2);
        }

        @Override // com.google.common.cache.LoadingCache
        public V get(K k2) throws ExecutionException {
            return this.localCache.getOrLoad(k2);
        }

        @Override // com.google.common.cache.LoadingCache
        public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
            return this.localCache.getAll(iterable);
        }

        @Override // com.google.common.cache.LoadingCache
        public V getUnchecked(K k2) {
            try {
                return get(k2);
            } catch (ExecutionException e2) {
                throw new UncheckedExecutionException(e2.getCause());
            }
        }

        @Override // com.google.common.cache.LoadingCache
        public void refresh(K k2) {
            this.localCache.refresh(k2);
        }

        @Override // com.google.common.cache.LocalCache.LocalManualCache
        public Object writeReplace() {
            return new LoadingSerializationProxy(this.localCache);
        }
    }

    public static class LocalManualCache<K, V> implements Cache<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        final LocalCache<K, V> localCache;

        @Override // com.google.common.cache.Cache
        public ConcurrentMap<K, V> asMap() {
            return this.localCache;
        }

        @Override // com.google.common.cache.Cache
        public void cleanUp() {
            this.localCache.cleanUp();
        }

        @Override // com.google.common.cache.Cache
        public V get(K k2, final Callable<? extends V> callable) throws ExecutionException {
            Preconditions.checkNotNull(callable);
            return this.localCache.get(k2, new CacheLoader<Object, V>() { // from class: com.google.common.cache.LocalCache.LocalManualCache.1
                @Override // com.google.common.cache.CacheLoader
                public V load(Object obj) throws Exception {
                    return (V) callable.call();
                }
            });
        }

        @Override // com.google.common.cache.Cache
        public ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
            return this.localCache.getAllPresent(iterable);
        }

        @Override // com.google.common.cache.Cache
        @NullableDecl
        public V getIfPresent(Object obj) {
            return this.localCache.getIfPresent(obj);
        }

        @Override // com.google.common.cache.Cache
        public void invalidate(Object obj) {
            Preconditions.checkNotNull(obj);
            this.localCache.remove(obj);
        }

        @Override // com.google.common.cache.Cache
        public void invalidateAll(Iterable<?> iterable) {
            this.localCache.invalidateAll(iterable);
        }

        @Override // com.google.common.cache.Cache
        public void put(K k2, V v2) {
            this.localCache.put(k2, v2);
        }

        @Override // com.google.common.cache.Cache
        public void putAll(Map<? extends K, ? extends V> map) {
            this.localCache.putAll(map);
        }

        @Override // com.google.common.cache.Cache
        public long size() {
            return this.localCache.longSize();
        }

        @Override // com.google.common.cache.Cache
        public CacheStats stats() {
            AbstractCache.SimpleStatsCounter simpleStatsCounter = new AbstractCache.SimpleStatsCounter();
            simpleStatsCounter.incrementBy(this.localCache.globalStatsCounter);
            for (Segment<K, V> segment : this.localCache.segments) {
                simpleStatsCounter.incrementBy(segment.statsCounter);
            }
            return simpleStatsCounter.snapshot();
        }

        Object writeReplace() {
            return new ManualSerializationProxy(this.localCache);
        }

        public LocalManualCache(CacheBuilder<? super K, ? super V> cacheBuilder) {
            this(new LocalCache(cacheBuilder, null));
        }

        @Override // com.google.common.cache.Cache
        public void invalidateAll() {
            this.localCache.clear();
        }

        private LocalManualCache(LocalCache<K, V> localCache) {
            this.localCache = localCache;
        }
    }

    public static class ManualSerializationProxy<K, V> extends ForwardingCache<K, V> implements Serializable {
        private static final long serialVersionUID = 1;
        final int concurrencyLevel;

        @MonotonicNonNullDecl
        transient Cache<K, V> delegate;
        final long expireAfterAccessNanos;
        final long expireAfterWriteNanos;
        final Equivalence<Object> keyEquivalence;
        final Strength keyStrength;
        final CacheLoader<? super K, V> loader;
        final long maxWeight;
        final RemovalListener<? super K, ? super V> removalListener;

        @NullableDecl
        final Ticker ticker;
        final Equivalence<Object> valueEquivalence;
        final Strength valueStrength;
        final Weigher<K, V> weigher;

        public ManualSerializationProxy(LocalCache<K, V> localCache) {
            this(localCache.keyStrength, localCache.valueStrength, localCache.keyEquivalence, localCache.valueEquivalence, localCache.expireAfterWriteNanos, localCache.expireAfterAccessNanos, localCache.maxWeight, localCache.weigher, localCache.concurrencyLevel, localCache.removalListener, localCache.ticker, localCache.defaultLoader);
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            this.delegate = (Cache<K, V>) recreateCacheBuilder().build();
        }

        private Object readResolve() {
            return this.delegate;
        }

        public CacheBuilder<K, V> recreateCacheBuilder() {
            CacheBuilder<K, V> cacheBuilder = (CacheBuilder<K, V>) CacheBuilder.newBuilder().setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).valueEquivalence(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel).removalListener(this.removalListener);
            cacheBuilder.strictParsing = false;
            long j2 = this.expireAfterWriteNanos;
            if (j2 > 0) {
                cacheBuilder.expireAfterWrite(j2, TimeUnit.NANOSECONDS);
            }
            long j3 = this.expireAfterAccessNanos;
            if (j3 > 0) {
                cacheBuilder.expireAfterAccess(j3, TimeUnit.NANOSECONDS);
            }
            Weigher weigher = this.weigher;
            if (weigher != CacheBuilder.OneWeigher.INSTANCE) {
                cacheBuilder.weigher(weigher);
                long j4 = this.maxWeight;
                if (j4 != -1) {
                    cacheBuilder.maximumWeight(j4);
                }
            } else {
                long j5 = this.maxWeight;
                if (j5 != -1) {
                    cacheBuilder.maximumSize(j5);
                }
            }
            Ticker ticker = this.ticker;
            if (ticker != null) {
                cacheBuilder.ticker(ticker);
            }
            return cacheBuilder;
        }

        private ManualSerializationProxy(Strength strength, Strength strength2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, long j2, long j3, long j4, Weigher<K, V> weigher, int i2, RemovalListener<? super K, ? super V> removalListener, Ticker ticker, CacheLoader<? super K, V> cacheLoader) {
            this.keyStrength = strength;
            this.valueStrength = strength2;
            this.keyEquivalence = equivalence;
            this.valueEquivalence = equivalence2;
            this.expireAfterWriteNanos = j2;
            this.expireAfterAccessNanos = j3;
            this.maxWeight = j4;
            this.weigher = weigher;
            this.concurrencyLevel = i2;
            this.removalListener = removalListener;
            this.ticker = (ticker == Ticker.systemTicker() || ticker == CacheBuilder.NULL_TICKER) ? null : ticker;
            this.loader = cacheLoader;
        }

        @Override // com.google.common.cache.ForwardingCache, com.google.common.collect.ForwardingObject
        public Cache<K, V> delegate() {
            return this.delegate;
        }
    }

    public enum NullEntry implements ReferenceEntry<Object, Object> {
        INSTANCE;

        @Override // com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            return 0L;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public int getHash() {
            return 0;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public Object getKey() {
            return null;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<Object, Object> getNext() {
            return null;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<Object, Object> getNextInAccessQueue() {
            return this;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<Object, Object> getNextInWriteQueue() {
            return this;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<Object, Object> getPreviousInAccessQueue() {
            return this;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<Object, Object> getPreviousInWriteQueue() {
            return this;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ValueReference<Object, Object> getValueReference() {
            return null;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            return 0L;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j2) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setValueReference(ValueReference<Object, Object> valueReference) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j2) {
        }
    }

    public static class SoftValueReference<K, V> extends SoftReference<V> implements ValueReference<K, V> {
        final ReferenceEntry<K, V> entry;

        public SoftValueReference(ReferenceQueue<V> referenceQueue, V v2, ReferenceEntry<K, V> referenceEntry) {
            super(v2, referenceQueue);
            this.entry = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v2, ReferenceEntry<K, V> referenceEntry) {
            return new SoftValueReference(referenceQueue, v2, referenceEntry);
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return 1;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isActive() {
            return true;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isLoading() {
            return false;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public void notifyNewValue(V v2) {
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V waitForValue() {
            return get();
        }
    }

    public enum Strength {
        STRONG { // from class: com.google.common.cache.LocalCache.Strength.1
            @Override // com.google.common.cache.LocalCache.Strength
            public Equivalence<Object> defaultEquivalence() {
                return Equivalence.equals();
            }

            @Override // com.google.common.cache.LocalCache.Strength
            public <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v2, int i2) {
                return i2 == 1 ? new StrongValueReference(v2) : new WeightedStrongValueReference(v2, i2);
            }
        },
        SOFT { // from class: com.google.common.cache.LocalCache.Strength.2
            @Override // com.google.common.cache.LocalCache.Strength
            public Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }

            @Override // com.google.common.cache.LocalCache.Strength
            public <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v2, int i2) {
                return i2 == 1 ? new SoftValueReference(segment.valueReferenceQueue, v2, referenceEntry) : new WeightedSoftValueReference(segment.valueReferenceQueue, v2, referenceEntry, i2);
            }
        },
        WEAK { // from class: com.google.common.cache.LocalCache.Strength.3
            @Override // com.google.common.cache.LocalCache.Strength
            public Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }

            @Override // com.google.common.cache.LocalCache.Strength
            public <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v2, int i2) {
                return i2 == 1 ? new WeakValueReference(segment.valueReferenceQueue, v2, referenceEntry) : new WeightedWeakValueReference(segment.valueReferenceQueue, v2, referenceEntry, i2);
            }
        };

        public abstract Equivalence<Object> defaultEquivalence();

        public abstract <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v2, int i2);
    }

    public static final class StrongAccessEntry<K, V> extends StrongEntry<K, V> {
        volatile long accessTime;
        ReferenceEntry<K, V> nextAccess;
        ReferenceEntry<K, V> previousAccess;

        public StrongAccessEntry(K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(k2, i2, referenceEntry);
            this.accessTime = Long.MAX_VALUE;
            this.nextAccess = LocalCache.nullEntry();
            this.previousAccess = LocalCache.nullEntry();
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j2) {
            this.accessTime = j2;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }
    }

    public static final class StrongAccessWriteEntry<K, V> extends StrongEntry<K, V> {
        volatile long accessTime;
        ReferenceEntry<K, V> nextAccess;
        ReferenceEntry<K, V> nextWrite;
        ReferenceEntry<K, V> previousAccess;
        ReferenceEntry<K, V> previousWrite;
        volatile long writeTime;

        public StrongAccessWriteEntry(K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(k2, i2, referenceEntry);
            this.accessTime = Long.MAX_VALUE;
            this.nextAccess = LocalCache.nullEntry();
            this.previousAccess = LocalCache.nullEntry();
            this.writeTime = Long.MAX_VALUE;
            this.nextWrite = LocalCache.nullEntry();
            this.previousWrite = LocalCache.nullEntry();
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j2) {
            this.accessTime = j2;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j2) {
            this.writeTime = j2;
        }
    }

    public static class StrongEntry<K, V> extends AbstractReferenceEntry<K, V> {
        final int hash;
        final K key;

        @NullableDecl
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference = LocalCache.unset();

        public StrongEntry(K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            this.key = k2;
            this.hash = i2;
            this.next = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public int getHash() {
            return this.hash;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public K getKey() {
            return this.key;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setValueReference(ValueReference<K, V> valueReference) {
            this.valueReference = valueReference;
        }
    }

    public static class StrongValueReference<K, V> implements ValueReference<K, V> {
        final V referent;

        public StrongValueReference(V v2) {
            this.referent = v2;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v2, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V get() {
            return this.referent;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return 1;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isActive() {
            return true;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isLoading() {
            return false;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public void notifyNewValue(V v2) {
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V waitForValue() {
            return get();
        }
    }

    public static final class StrongWriteEntry<K, V> extends StrongEntry<K, V> {
        ReferenceEntry<K, V> nextWrite;
        ReferenceEntry<K, V> previousWrite;
        volatile long writeTime;

        public StrongWriteEntry(K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(k2, i2, referenceEntry);
            this.writeTime = Long.MAX_VALUE;
            this.nextWrite = LocalCache.nullEntry();
            this.previousWrite = LocalCache.nullEntry();
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j2) {
            this.writeTime = j2;
        }
    }

    public final class ValueIterator extends LocalCache<K, V>.HashIterator<V> {
        public ValueIterator() {
            super();
        }

        @Override // com.google.common.cache.LocalCache.HashIterator, java.util.Iterator
        public V next() {
            return nextEntry().getValue();
        }
    }

    public interface ValueReference<K, V> {
        ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, @NullableDecl V v2, ReferenceEntry<K, V> referenceEntry);

        @NullableDecl
        V get();

        @NullableDecl
        ReferenceEntry<K, V> getEntry();

        int getWeight();

        boolean isActive();

        boolean isLoading();

        void notifyNewValue(@NullableDecl V v2);

        V waitForValue() throws ExecutionException;
    }

    public final class Values extends AbstractCollection<V> {
        private final ConcurrentMap<?, ?> map;

        public Values(ConcurrentMap<?, ?> concurrentMap) {
            this.map = concurrentMap;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            this.map.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return this.map.containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public Object[] toArray() {
            return LocalCache.toArrayList(this).toArray();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public <E> E[] toArray(E[] eArr) {
            return (E[]) LocalCache.toArrayList(this).toArray(eArr);
        }
    }

    public static final class WeakAccessEntry<K, V> extends WeakEntry<K, V> {
        volatile long accessTime;
        ReferenceEntry<K, V> nextAccess;
        ReferenceEntry<K, V> previousAccess;

        public WeakAccessEntry(ReferenceQueue<K> referenceQueue, K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k2, i2, referenceEntry);
            this.accessTime = Long.MAX_VALUE;
            this.nextAccess = LocalCache.nullEntry();
            this.previousAccess = LocalCache.nullEntry();
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j2) {
            this.accessTime = j2;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }
    }

    public static final class WeakAccessWriteEntry<K, V> extends WeakEntry<K, V> {
        volatile long accessTime;
        ReferenceEntry<K, V> nextAccess;
        ReferenceEntry<K, V> nextWrite;
        ReferenceEntry<K, V> previousAccess;
        ReferenceEntry<K, V> previousWrite;
        volatile long writeTime;

        public WeakAccessWriteEntry(ReferenceQueue<K> referenceQueue, K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k2, i2, referenceEntry);
            this.accessTime = Long.MAX_VALUE;
            this.nextAccess = LocalCache.nullEntry();
            this.previousAccess = LocalCache.nullEntry();
            this.writeTime = Long.MAX_VALUE;
            this.nextWrite = LocalCache.nullEntry();
            this.previousWrite = LocalCache.nullEntry();
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j2) {
            this.accessTime = j2;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j2) {
            this.writeTime = j2;
        }
    }

    public static class WeakEntry<K, V> extends WeakReference<K> implements ReferenceEntry<K, V> {
        final int hash;

        @NullableDecl
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference;

        public WeakEntry(ReferenceQueue<K> referenceQueue, K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(k2, referenceQueue);
            this.valueReference = LocalCache.unset();
            this.hash = i2;
            this.next = referenceEntry;
        }

        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public int getHash() {
            return this.hash;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public K getKey() {
            return get();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        public void setAccessTime(long j2) {
            throw new UnsupportedOperationException();
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setValueReference(ValueReference<K, V> valueReference) {
            this.valueReference = valueReference;
        }

        public void setWriteTime(long j2) {
            throw new UnsupportedOperationException();
        }
    }

    public static class WeakValueReference<K, V> extends WeakReference<V> implements ValueReference<K, V> {
        final ReferenceEntry<K, V> entry;

        public WeakValueReference(ReferenceQueue<V> referenceQueue, V v2, ReferenceEntry<K, V> referenceEntry) {
            super(v2, referenceQueue);
            this.entry = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v2, ReferenceEntry<K, V> referenceEntry) {
            return new WeakValueReference(referenceQueue, v2, referenceEntry);
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return 1;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isActive() {
            return true;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isLoading() {
            return false;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public void notifyNewValue(V v2) {
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V waitForValue() {
            return get();
        }
    }

    public static final class WeakWriteEntry<K, V> extends WeakEntry<K, V> {
        ReferenceEntry<K, V> nextWrite;
        ReferenceEntry<K, V> previousWrite;
        volatile long writeTime;

        public WeakWriteEntry(ReferenceQueue<K> referenceQueue, K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k2, i2, referenceEntry);
            this.writeTime = Long.MAX_VALUE;
            this.nextWrite = LocalCache.nullEntry();
            this.previousWrite = LocalCache.nullEntry();
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j2) {
            this.writeTime = j2;
        }
    }

    public static final class WeightedSoftValueReference<K, V> extends SoftValueReference<K, V> {
        final int weight;

        public WeightedSoftValueReference(ReferenceQueue<V> referenceQueue, V v2, ReferenceEntry<K, V> referenceEntry, int i2) {
            super(referenceQueue, v2, referenceEntry);
            this.weight = i2;
        }

        @Override // com.google.common.cache.LocalCache.SoftValueReference, com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v2, ReferenceEntry<K, V> referenceEntry) {
            return new WeightedSoftValueReference(referenceQueue, v2, referenceEntry, this.weight);
        }

        @Override // com.google.common.cache.LocalCache.SoftValueReference, com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return this.weight;
        }
    }

    public static final class WeightedStrongValueReference<K, V> extends StrongValueReference<K, V> {
        final int weight;

        public WeightedStrongValueReference(V v2, int i2) {
            super(v2);
            this.weight = i2;
        }

        @Override // com.google.common.cache.LocalCache.StrongValueReference, com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return this.weight;
        }
    }

    public static final class WeightedWeakValueReference<K, V> extends WeakValueReference<K, V> {
        final int weight;

        public WeightedWeakValueReference(ReferenceQueue<V> referenceQueue, V v2, ReferenceEntry<K, V> referenceEntry, int i2) {
            super(referenceQueue, v2, referenceEntry);
            this.weight = i2;
        }

        @Override // com.google.common.cache.LocalCache.WeakValueReference, com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v2, ReferenceEntry<K, V> referenceEntry) {
            return new WeightedWeakValueReference(referenceQueue, v2, referenceEntry, this.weight);
        }

        @Override // com.google.common.cache.LocalCache.WeakValueReference, com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return this.weight;
        }
    }

    public static final class WriteQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>() { // from class: com.google.common.cache.LocalCache.WriteQueue.1
            ReferenceEntry<K, V> nextWrite = this;
            ReferenceEntry<K, V> previousWrite = this;

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public ReferenceEntry<K, V> getNextInWriteQueue() {
                return this.nextWrite;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public ReferenceEntry<K, V> getPreviousInWriteQueue() {
                return this.previousWrite;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public long getWriteTime() {
                return Long.MAX_VALUE;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
                this.nextWrite = referenceEntry;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
                this.previousWrite = referenceEntry;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setWriteTime(long j2) {
            }
        };

        @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
        public void clear() {
            ReferenceEntry<K, V> nextInWriteQueue = this.head.getNextInWriteQueue();
            while (true) {
                ReferenceEntry<K, V> referenceEntry = this.head;
                if (nextInWriteQueue == referenceEntry) {
                    referenceEntry.setNextInWriteQueue(referenceEntry);
                    ReferenceEntry<K, V> referenceEntry2 = this.head;
                    referenceEntry2.setPreviousInWriteQueue(referenceEntry2);
                    return;
                } else {
                    ReferenceEntry<K, V> nextInWriteQueue2 = nextInWriteQueue.getNextInWriteQueue();
                    LocalCache.nullifyWriteOrder(nextInWriteQueue);
                    nextInWriteQueue = nextInWriteQueue2;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return ((ReferenceEntry) obj).getNextInWriteQueue() != NullEntry.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.head.getNextInWriteQueue() == this.head;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(peek()) { // from class: com.google.common.cache.LocalCache.WriteQueue.2
                @Override // com.google.common.collect.AbstractSequentialIterator
                public ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> referenceEntry) {
                    ReferenceEntry<K, V> nextInWriteQueue = referenceEntry.getNextInWriteQueue();
                    if (nextInWriteQueue == WriteQueue.this.head) {
                        return null;
                    }
                    return nextInWriteQueue;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object obj) {
            ReferenceEntry referenceEntry = (ReferenceEntry) obj;
            ReferenceEntry<K, V> previousInWriteQueue = referenceEntry.getPreviousInWriteQueue();
            ReferenceEntry<K, V> nextInWriteQueue = referenceEntry.getNextInWriteQueue();
            LocalCache.connectWriteOrder(previousInWriteQueue, nextInWriteQueue);
            LocalCache.nullifyWriteOrder(referenceEntry);
            return nextInWriteQueue != NullEntry.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            int i2 = 0;
            for (ReferenceEntry<K, V> nextInWriteQueue = this.head.getNextInWriteQueue(); nextInWriteQueue != this.head; nextInWriteQueue = nextInWriteQueue.getNextInWriteQueue()) {
                i2++;
            }
            return i2;
        }

        @Override // java.util.Queue
        public boolean offer(ReferenceEntry<K, V> referenceEntry) {
            LocalCache.connectWriteOrder(referenceEntry.getPreviousInWriteQueue(), referenceEntry.getNextInWriteQueue());
            LocalCache.connectWriteOrder(this.head.getPreviousInWriteQueue(), referenceEntry);
            LocalCache.connectWriteOrder(referenceEntry, this.head);
            return true;
        }

        @Override // java.util.Queue
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> nextInWriteQueue = this.head.getNextInWriteQueue();
            if (nextInWriteQueue == this.head) {
                return null;
            }
            return nextInWriteQueue;
        }

        @Override // java.util.Queue
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> nextInWriteQueue = this.head.getNextInWriteQueue();
            if (nextInWriteQueue == this.head) {
                return null;
            }
            remove(nextInWriteQueue);
            return nextInWriteQueue;
        }
    }

    public final class WriteThroughEntry implements Map.Entry<K, V> {
        final K key;
        V value;

        public WriteThroughEntry(K k2, V v2) {
            this.key = k2;
            this.value = v2;
        }

        @Override // java.util.Map.Entry
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return this.key.equals(entry.getKey()) && this.value.equals(entry.getValue());
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return this.key.hashCode() ^ this.value.hashCode();
        }

        @Override // java.util.Map.Entry
        public V setValue(V v2) {
            V v3 = (V) LocalCache.this.put(this.key, v2);
            this.value = v2;
            return v3;
        }

        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    public LocalCache(CacheBuilder<? super K, ? super V> cacheBuilder, @NullableDecl CacheLoader<? super K, V> cacheLoader) {
        this.concurrencyLevel = Math.min(cacheBuilder.getConcurrencyLevel(), 65536);
        Strength keyStrength = cacheBuilder.getKeyStrength();
        this.keyStrength = keyStrength;
        this.valueStrength = cacheBuilder.getValueStrength();
        this.keyEquivalence = cacheBuilder.getKeyEquivalence();
        this.valueEquivalence = cacheBuilder.getValueEquivalence();
        long maximumWeight = cacheBuilder.getMaximumWeight();
        this.maxWeight = maximumWeight;
        this.weigher = (Weigher<K, V>) cacheBuilder.getWeigher();
        this.expireAfterAccessNanos = cacheBuilder.getExpireAfterAccessNanos();
        this.expireAfterWriteNanos = cacheBuilder.getExpireAfterWriteNanos();
        this.refreshNanos = cacheBuilder.getRefreshNanos();
        CacheBuilder.NullListener nullListener = (RemovalListener<K, V>) cacheBuilder.getRemovalListener();
        this.removalListener = nullListener;
        this.removalNotificationQueue = nullListener == CacheBuilder.NullListener.INSTANCE ? discardingQueue() : new ConcurrentLinkedQueue<>();
        this.ticker = cacheBuilder.getTicker(recordsTime());
        this.entryFactory = EntryFactory.getFactory(keyStrength, usesAccessEntries(), usesWriteEntries());
        this.globalStatsCounter = cacheBuilder.getStatsCounterSupplier().get();
        this.defaultLoader = cacheLoader;
        int iMin = Math.min(cacheBuilder.getInitialCapacity(), 1073741824);
        if (evictsBySize() && !customWeigher()) {
            iMin = (int) Math.min(iMin, maximumWeight);
        }
        int i2 = 0;
        int i3 = 1;
        int i4 = 0;
        int i5 = 1;
        while (i5 < this.concurrencyLevel && (!evictsBySize() || i5 * 20 <= this.maxWeight)) {
            i4++;
            i5 <<= 1;
        }
        this.segmentShift = 32 - i4;
        this.segmentMask = i5 - 1;
        this.segments = newSegmentArray(i5);
        int i6 = iMin / i5;
        while (i3 < (i6 * i5 < iMin ? i6 + 1 : i6)) {
            i3 <<= 1;
        }
        if (evictsBySize()) {
            long j2 = this.maxWeight;
            long j3 = i5;
            long j4 = (j2 / j3) + 1;
            long j5 = j2 % j3;
            while (true) {
                Segment<K, V>[] segmentArr = this.segments;
                if (i2 >= segmentArr.length) {
                    return;
                }
                if (i2 == j5) {
                    j4--;
                }
                segmentArr[i2] = createSegment(i3, j4, cacheBuilder.getStatsCounterSupplier().get());
                i2++;
            }
        } else {
            while (true) {
                Segment<K, V>[] segmentArr2 = this.segments;
                if (i2 >= segmentArr2.length) {
                    return;
                }
                segmentArr2[i2] = createSegment(i3, -1L, cacheBuilder.getStatsCounterSupplier().get());
                i2++;
            }
        }
    }

    public static <K, V> void connectAccessOrder(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
        referenceEntry.setNextInAccessQueue(referenceEntry2);
        referenceEntry2.setPreviousInAccessQueue(referenceEntry);
    }

    public static <K, V> void connectWriteOrder(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
        referenceEntry.setNextInWriteQueue(referenceEntry2);
        referenceEntry2.setPreviousInWriteQueue(referenceEntry);
    }

    public static <E> Queue<E> discardingQueue() {
        return (Queue<E>) DISCARDING_QUEUE;
    }

    public static <K, V> ReferenceEntry<K, V> nullEntry() {
        return NullEntry.INSTANCE;
    }

    public static <K, V> void nullifyAccessOrder(ReferenceEntry<K, V> referenceEntry) {
        ReferenceEntry<K, V> referenceEntryNullEntry = nullEntry();
        referenceEntry.setNextInAccessQueue(referenceEntryNullEntry);
        referenceEntry.setPreviousInAccessQueue(referenceEntryNullEntry);
    }

    public static <K, V> void nullifyWriteOrder(ReferenceEntry<K, V> referenceEntry) {
        ReferenceEntry<K, V> referenceEntryNullEntry = nullEntry();
        referenceEntry.setNextInWriteQueue(referenceEntryNullEntry);
        referenceEntry.setPreviousInWriteQueue(referenceEntryNullEntry);
    }

    public static int rehash(int i2) {
        int i3 = i2 + ((i2 << 15) ^ (-12931));
        int i4 = i3 ^ (i3 >>> 10);
        int i5 = i4 + (i4 << 3);
        int i6 = i5 ^ (i5 >>> 6);
        int i7 = i6 + (i6 << 2) + (i6 << 14);
        return i7 ^ (i7 >>> 16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> ArrayList<E> toArrayList(Collection<E> collection) {
        ArrayList<E> arrayList = new ArrayList<>(collection.size());
        Iterators.addAll(arrayList, collection.iterator());
        return arrayList;
    }

    public static <K, V> ValueReference<K, V> unset() {
        return (ValueReference<K, V>) UNSET;
    }

    public void cleanUp() {
        for (Segment<K, V> segment : this.segments) {
            segment.cleanUp();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        for (Segment<K, V> segment : this.segments) {
            segment.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@NullableDecl Object obj) {
        if (obj == null) {
            return false;
        }
        int iHash = hash(obj);
        return segmentFor(iHash).containsKey(obj, iHash);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1, types: [int] */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r15v1, types: [int] */
    /* JADX WARN: Type inference failed for: r15v3 */
    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(@NullableDecl Object obj) {
        boolean z2 = false;
        if (obj == null) {
            return false;
        }
        long j2 = this.ticker.read();
        Segment<K, V>[] segmentArr = this.segments;
        long j3 = -1;
        int i2 = 0;
        while (i2 < 3) {
            int length = segmentArr.length;
            long j4 = 0;
            for (?? r12 = z2; r12 < length; r12++) {
                Segment<K, V> segment = segmentArr[r12];
                int i3 = segment.count;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = segment.table;
                for (?? r15 = z2; r15 < atomicReferenceArray.length(); r15++) {
                    ReferenceEntry<K, V> next = atomicReferenceArray.get(r15);
                    while (next != null) {
                        Segment<K, V>[] segmentArr2 = segmentArr;
                        V liveValue = segment.getLiveValue(next, j2);
                        long j5 = j2;
                        if (liveValue != null && this.valueEquivalence.equivalent(obj, liveValue)) {
                            return true;
                        }
                        next = next.getNext();
                        segmentArr = segmentArr2;
                        j2 = j5;
                    }
                }
                j4 += segment.modCount;
                j2 = j2;
                z2 = false;
            }
            long j6 = j2;
            Segment<K, V>[] segmentArr3 = segmentArr;
            if (j4 == j3) {
                return false;
            }
            i2++;
            j3 = j4;
            segmentArr = segmentArr3;
            j2 = j6;
            z2 = false;
        }
        return z2;
    }

    @VisibleForTesting
    public ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
        return segmentFor(referenceEntry.getHash()).copyEntry(referenceEntry, referenceEntry2);
    }

    public Segment<K, V> createSegment(int i2, long j2, AbstractCache.StatsCounter statsCounter) {
        return new Segment<>(this, i2, j2, statsCounter);
    }

    public boolean customWeigher() {
        return this.weigher != CacheBuilder.OneWeigher.INSTANCE;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @GwtIncompatible
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.entrySet;
        if (set != null) {
            return set;
        }
        EntrySet entrySet = new EntrySet(this);
        this.entrySet = entrySet;
        return entrySet;
    }

    public boolean evictsBySize() {
        return this.maxWeight >= 0;
    }

    public boolean expires() {
        return expiresAfterWrite() || expiresAfterAccess();
    }

    public boolean expiresAfterAccess() {
        return this.expireAfterAccessNanos > 0;
    }

    public boolean expiresAfterWrite() {
        return this.expireAfterWriteNanos > 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @NullableDecl
    public V get(@NullableDecl Object obj) {
        if (obj == null) {
            return null;
        }
        int iHash = hash(obj);
        return segmentFor(iHash).get(obj, iHash);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
        LinkedHashMap linkedHashMapNewLinkedHashMap = Maps.newLinkedHashMap();
        LinkedHashSet linkedHashSetNewLinkedHashSet = Sets.newLinkedHashSet();
        int i2 = 0;
        int i3 = 0;
        for (K k2 : iterable) {
            Object obj = get(k2);
            if (!linkedHashMapNewLinkedHashMap.containsKey(k2)) {
                linkedHashMapNewLinkedHashMap.put(k2, obj);
                if (obj == null) {
                    i3++;
                    linkedHashSetNewLinkedHashSet.add(k2);
                } else {
                    i2++;
                }
            }
        }
        try {
            if (!linkedHashSetNewLinkedHashSet.isEmpty()) {
                try {
                    Map mapLoadAll = loadAll(linkedHashSetNewLinkedHashSet, this.defaultLoader);
                    for (Object obj2 : linkedHashSetNewLinkedHashSet) {
                        Object obj3 = mapLoadAll.get(obj2);
                        if (obj3 == null) {
                            throw new CacheLoader.InvalidCacheLoadException("loadAll failed to return a value for " + obj2);
                        }
                        linkedHashMapNewLinkedHashMap.put(obj2, obj3);
                    }
                } catch (CacheLoader.UnsupportedLoadingOperationException unused) {
                    for (Object obj4 : linkedHashSetNewLinkedHashSet) {
                        i3--;
                        linkedHashMapNewLinkedHashMap.put(obj4, get(obj4, this.defaultLoader));
                    }
                }
            }
            return ImmutableMap.copyOf((Map) linkedHashMapNewLinkedHashMap);
        } finally {
            this.globalStatsCounter.recordHits(i2);
            this.globalStatsCounter.recordMisses(i3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
        LinkedHashMap linkedHashMapNewLinkedHashMap = Maps.newLinkedHashMap();
        int i2 = 0;
        int i3 = 0;
        for (Object obj : iterable) {
            V v2 = get(obj);
            if (v2 == null) {
                i3++;
            } else {
                linkedHashMapNewLinkedHashMap.put(obj, v2);
                i2++;
            }
        }
        this.globalStatsCounter.recordHits(i2);
        this.globalStatsCounter.recordMisses(i3);
        return ImmutableMap.copyOf((Map) linkedHashMapNewLinkedHashMap);
    }

    public ReferenceEntry<K, V> getEntry(@NullableDecl Object obj) {
        if (obj == null) {
            return null;
        }
        int iHash = hash(obj);
        return segmentFor(iHash).getEntry(obj, iHash);
    }

    @NullableDecl
    public V getIfPresent(Object obj) {
        int iHash = hash(Preconditions.checkNotNull(obj));
        V v2 = segmentFor(iHash).get(obj, iHash);
        if (v2 == null) {
            this.globalStatsCounter.recordMisses(1);
        } else {
            this.globalStatsCounter.recordHits(1);
        }
        return v2;
    }

    @NullableDecl
    public V getLiveValue(ReferenceEntry<K, V> referenceEntry, long j2) {
        V v2;
        if (referenceEntry.getKey() == null || (v2 = referenceEntry.getValueReference().get()) == null || isExpired(referenceEntry, j2)) {
            return null;
        }
        return v2;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @NullableDecl
    public V getOrDefault(@NullableDecl Object obj, @NullableDecl V v2) {
        V v3 = get(obj);
        return v3 != null ? v3 : v2;
    }

    public V getOrLoad(K k2) throws ExecutionException {
        return get(k2, this.defaultLoader);
    }

    public int hash(@NullableDecl Object obj) {
        return rehash(this.keyEquivalence.hash(obj));
    }

    public void invalidateAll(Iterable<?> iterable) {
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        Segment<K, V>[] segmentArr = this.segments;
        long j2 = 0;
        for (int i2 = 0; i2 < segmentArr.length; i2++) {
            if (segmentArr[i2].count != 0) {
                return false;
            }
            j2 += segmentArr[i2].modCount;
        }
        if (j2 == 0) {
            return true;
        }
        for (int i3 = 0; i3 < segmentArr.length; i3++) {
            if (segmentArr[i3].count != 0) {
                return false;
            }
            j2 -= segmentArr[i3].modCount;
        }
        return j2 == 0;
    }

    public boolean isExpired(ReferenceEntry<K, V> referenceEntry, long j2) {
        Preconditions.checkNotNull(referenceEntry);
        if (!expiresAfterAccess() || j2 - referenceEntry.getAccessTime() < this.expireAfterAccessNanos) {
            return expiresAfterWrite() && j2 - referenceEntry.getWriteTime() >= this.expireAfterWriteNanos;
        }
        return true;
    }

    @VisibleForTesting
    public boolean isLive(ReferenceEntry<K, V> referenceEntry, long j2) {
        return segmentFor(referenceEntry.getHash()).getLiveValue(referenceEntry, j2) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.keySet;
        if (set != null) {
            return set;
        }
        KeySet keySet = new KeySet(this);
        this.keySet = keySet;
        return keySet;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00b9  */
    @org.checkerframework.checker.nullness.compatqual.NullableDecl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Map<K, V> loadAll(java.util.Set<? extends K> r7, com.google.common.cache.CacheLoader<? super K, V> r8) throws java.lang.Throwable {
        /*
            r6 = this;
            com.google.common.base.Preconditions.checkNotNull(r8)
            com.google.common.base.Preconditions.checkNotNull(r7)
            com.google.common.base.Stopwatch r0 = com.google.common.base.Stopwatch.createStarted()
            r1 = 1
            r2 = 0
            java.util.Map r7 = r8.loadAll(r7)     // Catch: java.lang.Throwable -> L8e java.lang.Error -> L91 java.lang.Exception -> L98 java.lang.RuntimeException -> L9f java.lang.InterruptedException -> La6 com.google.common.cache.CacheLoader.UnsupportedLoadingOperationException -> Lb4
            if (r7 == 0) goto L6c
            r0.stop()
            java.util.Set r3 = r7.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L1d:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L3c
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            java.lang.Object r5 = r4.getKey()
            java.lang.Object r4 = r4.getValue()
            if (r5 == 0) goto L3a
            if (r4 != 0) goto L36
            goto L3a
        L36:
            r6.put(r5, r4)
            goto L1d
        L3a:
            r2 = r1
            goto L1d
        L3c:
            if (r2 != 0) goto L4a
            com.google.common.cache.AbstractCache$StatsCounter r8 = r6.globalStatsCounter
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.NANOSECONDS
            long r0 = r0.elapsed(r1)
            r8.recordLoadSuccess(r0)
            return r7
        L4a:
            com.google.common.cache.AbstractCache$StatsCounter r7 = r6.globalStatsCounter
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.NANOSECONDS
            long r0 = r0.elapsed(r1)
            r7.recordLoadException(r0)
            com.google.common.cache.CacheLoader$InvalidCacheLoadException r7 = new com.google.common.cache.CacheLoader$InvalidCacheLoadException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r8)
            java.lang.String r8 = " returned null keys or values from loadAll"
            r0.append(r8)
            java.lang.String r8 = r0.toString()
            r7.<init>(r8)
            throw r7
        L6c:
            com.google.common.cache.AbstractCache$StatsCounter r7 = r6.globalStatsCounter
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.NANOSECONDS
            long r0 = r0.elapsed(r1)
            r7.recordLoadException(r0)
            com.google.common.cache.CacheLoader$InvalidCacheLoadException r7 = new com.google.common.cache.CacheLoader$InvalidCacheLoadException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r8)
            java.lang.String r8 = " returned null map from loadAll"
            r0.append(r8)
            java.lang.String r8 = r0.toString()
            r7.<init>(r8)
            throw r7
        L8e:
            r7 = move-exception
            r1 = r2
            goto Lb7
        L91:
            r7 = move-exception
            com.google.common.util.concurrent.ExecutionError r8 = new com.google.common.util.concurrent.ExecutionError     // Catch: java.lang.Throwable -> L8e
            r8.<init>(r7)     // Catch: java.lang.Throwable -> L8e
            throw r8     // Catch: java.lang.Throwable -> L8e
        L98:
            r7 = move-exception
            java.util.concurrent.ExecutionException r8 = new java.util.concurrent.ExecutionException     // Catch: java.lang.Throwable -> L8e
            r8.<init>(r7)     // Catch: java.lang.Throwable -> L8e
            throw r8     // Catch: java.lang.Throwable -> L8e
        L9f:
            r7 = move-exception
            com.google.common.util.concurrent.UncheckedExecutionException r8 = new com.google.common.util.concurrent.UncheckedExecutionException     // Catch: java.lang.Throwable -> L8e
            r8.<init>(r7)     // Catch: java.lang.Throwable -> L8e
            throw r8     // Catch: java.lang.Throwable -> L8e
        La6:
            r7 = move-exception
            java.lang.Thread r8 = java.lang.Thread.currentThread()     // Catch: java.lang.Throwable -> L8e
            r8.interrupt()     // Catch: java.lang.Throwable -> L8e
            java.util.concurrent.ExecutionException r8 = new java.util.concurrent.ExecutionException     // Catch: java.lang.Throwable -> L8e
            r8.<init>(r7)     // Catch: java.lang.Throwable -> L8e
            throw r8     // Catch: java.lang.Throwable -> L8e
        Lb4:
            r7 = move-exception
            throw r7     // Catch: java.lang.Throwable -> Lb6
        Lb6:
            r7 = move-exception
        Lb7:
            if (r1 != 0) goto Lc4
            com.google.common.cache.AbstractCache$StatsCounter r8 = r6.globalStatsCounter
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.NANOSECONDS
            long r0 = r0.elapsed(r1)
            r8.recordLoadException(r0)
        Lc4:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.loadAll(java.util.Set, com.google.common.cache.CacheLoader):java.util.Map");
    }

    public long longSize() {
        long jMax = 0;
        for (int i2 = 0; i2 < this.segments.length; i2++) {
            jMax += Math.max(0, r0[i2].count);
        }
        return jMax;
    }

    @VisibleForTesting
    public ReferenceEntry<K, V> newEntry(K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
        Segment<K, V> segmentSegmentFor = segmentFor(i2);
        segmentSegmentFor.lock();
        try {
            return segmentSegmentFor.newEntry(k2, i2, referenceEntry);
        } finally {
            segmentSegmentFor.unlock();
        }
    }

    public final Segment<K, V>[] newSegmentArray(int i2) {
        return new Segment[i2];
    }

    /* JADX WARN: Multi-variable type inference failed */
    @VisibleForTesting
    public ValueReference<K, V> newValueReference(ReferenceEntry<K, V> referenceEntry, V v2, int i2) {
        return this.valueStrength.referenceValue(segmentFor(referenceEntry.getHash()), referenceEntry, Preconditions.checkNotNull(v2), i2);
    }

    public void processPendingNotifications() {
        while (true) {
            RemovalNotification<K, V> removalNotificationPoll = this.removalNotificationQueue.poll();
            if (removalNotificationPoll == null) {
                return;
            }
            try {
                this.removalListener.onRemoval(removalNotificationPoll);
            } catch (Throwable th) {
                logger.log(Level.WARNING, "Exception thrown by removal listener", th);
            }
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V put(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int iHash = hash(k2);
        return segmentFor(iHash).put(k2, iHash, v2, false);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V putIfAbsent(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int iHash = hash(k2);
        return segmentFor(iHash).put(k2, iHash, v2, true);
    }

    public void reclaimKey(ReferenceEntry<K, V> referenceEntry) {
        int hash = referenceEntry.getHash();
        segmentFor(hash).reclaimKey(referenceEntry, hash);
    }

    public void reclaimValue(ValueReference<K, V> valueReference) {
        ReferenceEntry<K, V> entry = valueReference.getEntry();
        int hash = entry.getHash();
        segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
    }

    public boolean recordsAccess() {
        return expiresAfterAccess();
    }

    public boolean recordsTime() {
        return recordsWrite() || recordsAccess();
    }

    public boolean recordsWrite() {
        return expiresAfterWrite() || refreshes();
    }

    public void refresh(K k2) {
        int iHash = hash(Preconditions.checkNotNull(k2));
        segmentFor(iHash).refresh(k2, iHash, this.defaultLoader, false);
    }

    public boolean refreshes() {
        return this.refreshNanos > 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(@NullableDecl Object obj) {
        if (obj == null) {
            return null;
        }
        int iHash = hash(obj);
        return segmentFor(iHash).remove(obj, iHash);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public boolean replace(K k2, @NullableDecl V v2, V v3) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v3);
        if (v2 == null) {
            return false;
        }
        int iHash = hash(k2);
        return segmentFor(iHash).replace(k2, iHash, v2, v3);
    }

    public Segment<K, V> segmentFor(int i2) {
        return this.segments[(i2 >>> this.segmentShift) & this.segmentMask];
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return Ints.saturatedCast(longSize());
    }

    public boolean usesAccessEntries() {
        return usesAccessQueue() || recordsAccess();
    }

    public boolean usesAccessQueue() {
        return expiresAfterAccess() || evictsBySize();
    }

    public boolean usesKeyReferences() {
        return this.keyStrength != Strength.STRONG;
    }

    public boolean usesValueReferences() {
        return this.valueStrength != Strength.STRONG;
    }

    public boolean usesWriteEntries() {
        return usesWriteQueue() || recordsWrite();
    }

    public boolean usesWriteQueue() {
        return expiresAfterWrite();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<V> values() {
        Collection<V> collection = this.values;
        if (collection != null) {
            return collection;
        }
        Values values = new Values(this);
        this.values = values;
        return values;
    }

    public V get(K k2, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
        int iHash = hash(Preconditions.checkNotNull(k2));
        return segmentFor(iHash).get(k2, iHash, cacheLoader);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public boolean remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
        if (obj == null || obj2 == null) {
            return false;
        }
        int iHash = hash(obj);
        return segmentFor(iHash).remove(obj, iHash, obj2);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V replace(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int iHash = hash(k2);
        return segmentFor(iHash).replace(k2, iHash, v2);
    }

    public static class Segment<K, V> extends ReentrantLock {

        @GuardedBy("this")
        final Queue<ReferenceEntry<K, V>> accessQueue;
        volatile int count;

        @NullableDecl
        final ReferenceQueue<K> keyReferenceQueue;

        @Weak
        final LocalCache<K, V> map;
        final long maxSegmentWeight;
        int modCount;
        final AtomicInteger readCount = new AtomicInteger();
        final Queue<ReferenceEntry<K, V>> recencyQueue;
        final AbstractCache.StatsCounter statsCounter;

        @MonotonicNonNullDecl
        volatile AtomicReferenceArray<ReferenceEntry<K, V>> table;
        int threshold;

        @GuardedBy("this")
        long totalWeight;

        @NullableDecl
        final ReferenceQueue<V> valueReferenceQueue;

        @GuardedBy("this")
        final Queue<ReferenceEntry<K, V>> writeQueue;

        public Segment(LocalCache<K, V> localCache, int i2, long j2, AbstractCache.StatsCounter statsCounter) {
            this.map = localCache;
            this.maxSegmentWeight = j2;
            this.statsCounter = (AbstractCache.StatsCounter) Preconditions.checkNotNull(statsCounter);
            initTable(newEntryArray(i2));
            this.keyReferenceQueue = localCache.usesKeyReferences() ? new ReferenceQueue<>() : null;
            this.valueReferenceQueue = localCache.usesValueReferences() ? new ReferenceQueue<>() : null;
            this.recencyQueue = localCache.usesAccessQueue() ? new ConcurrentLinkedQueue<>() : LocalCache.discardingQueue();
            this.writeQueue = localCache.usesWriteQueue() ? new WriteQueue<>() : LocalCache.discardingQueue();
            this.accessQueue = localCache.usesAccessQueue() ? new AccessQueue<>() : LocalCache.discardingQueue();
        }

        public void cleanUp() {
            runLockedCleanup(this.map.ticker.read());
            runUnlockedCleanup();
        }

        public void clear() {
            if (this.count != 0) {
                lock();
                try {
                    preWriteCleanup(this.map.ticker.read());
                    AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                    for (int i2 = 0; i2 < atomicReferenceArray.length(); i2++) {
                        for (ReferenceEntry<K, V> next = atomicReferenceArray.get(i2); next != null; next = next.getNext()) {
                            if (next.getValueReference().isActive()) {
                                K key = next.getKey();
                                V v2 = next.getValueReference().get();
                                enqueueNotification(key, next.getHash(), v2, next.getValueReference().getWeight(), (key == null || v2 == null) ? RemovalCause.COLLECTED : RemovalCause.EXPLICIT);
                            }
                        }
                    }
                    for (int i3 = 0; i3 < atomicReferenceArray.length(); i3++) {
                        atomicReferenceArray.set(i3, null);
                    }
                    clearReferenceQueues();
                    this.writeQueue.clear();
                    this.accessQueue.clear();
                    this.readCount.set(0);
                    this.modCount++;
                    this.count = 0;
                } finally {
                    unlock();
                    postWriteCleanup();
                }
            }
        }

        public void clearKeyReferenceQueue() {
            while (this.keyReferenceQueue.poll() != null) {
            }
        }

        public void clearReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                clearKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                clearValueReferenceQueue();
            }
        }

        public void clearValueReferenceQueue() {
            while (this.valueReferenceQueue.poll() != null) {
            }
        }

        public boolean containsKey(Object obj, int i2) {
            try {
                if (this.count == 0) {
                    return false;
                }
                ReferenceEntry<K, V> liveEntry = getLiveEntry(obj, i2, this.map.ticker.read());
                if (liveEntry == null) {
                    return false;
                }
                return liveEntry.getValueReference().get() != null;
            } finally {
                postReadCleanup();
            }
        }

        @VisibleForTesting
        public boolean containsValue(Object obj) {
            try {
                if (this.count != 0) {
                    long j2 = this.map.ticker.read();
                    AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                    int length = atomicReferenceArray.length();
                    for (int i2 = 0; i2 < length; i2++) {
                        for (ReferenceEntry<K, V> next = atomicReferenceArray.get(i2); next != null; next = next.getNext()) {
                            V liveValue = getLiveValue(next, j2);
                            if (liveValue != null && this.map.valueEquivalence.equivalent(obj, liveValue)) {
                                postReadCleanup();
                                return true;
                            }
                        }
                    }
                }
                return false;
            } finally {
                postReadCleanup();
            }
        }

        @GuardedBy("this")
        public ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            if (referenceEntry.getKey() == null) {
                return null;
            }
            ValueReference<K, V> valueReference = referenceEntry.getValueReference();
            V v2 = valueReference.get();
            if (v2 == null && valueReference.isActive()) {
                return null;
            }
            ReferenceEntry<K, V> referenceEntryCopyEntry = this.map.entryFactory.copyEntry(this, referenceEntry, referenceEntry2);
            referenceEntryCopyEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, v2, referenceEntryCopyEntry));
            return referenceEntryCopyEntry;
        }

        @GuardedBy("this")
        public void drainKeyReferenceQueue() {
            int i2 = 0;
            do {
                Reference<? extends K> referencePoll = this.keyReferenceQueue.poll();
                if (referencePoll == null) {
                    return;
                }
                this.map.reclaimKey((ReferenceEntry) referencePoll);
                i2++;
            } while (i2 != 16);
        }

        @GuardedBy("this")
        public void drainRecencyQueue() {
            while (true) {
                ReferenceEntry<K, V> referenceEntryPoll = this.recencyQueue.poll();
                if (referenceEntryPoll == null) {
                    return;
                }
                if (this.accessQueue.contains(referenceEntryPoll)) {
                    this.accessQueue.add(referenceEntryPoll);
                }
            }
        }

        @GuardedBy("this")
        public void drainReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                drainKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                drainValueReferenceQueue();
            }
        }

        @GuardedBy("this")
        public void drainValueReferenceQueue() {
            int i2 = 0;
            do {
                Reference<? extends V> referencePoll = this.valueReferenceQueue.poll();
                if (referencePoll == null) {
                    return;
                }
                this.map.reclaimValue((ValueReference) referencePoll);
                i2++;
            } while (i2 != 16);
        }

        @GuardedBy("this")
        public void enqueueNotification(@NullableDecl K k2, int i2, @NullableDecl V v2, int i3, RemovalCause removalCause) {
            this.totalWeight -= i3;
            if (removalCause.wasEvicted()) {
                this.statsCounter.recordEviction();
            }
            if (this.map.removalNotificationQueue != LocalCache.DISCARDING_QUEUE) {
                this.map.removalNotificationQueue.offer(RemovalNotification.create(k2, v2, removalCause));
            }
        }

        @GuardedBy("this")
        public void evictEntries(ReferenceEntry<K, V> referenceEntry) {
            if (this.map.evictsBySize()) {
                drainRecencyQueue();
                if (referenceEntry.getValueReference().getWeight() > this.maxSegmentWeight && !removeEntry(referenceEntry, referenceEntry.getHash(), RemovalCause.SIZE)) {
                    throw new AssertionError();
                }
                while (this.totalWeight > this.maxSegmentWeight) {
                    ReferenceEntry<K, V> nextEvictable = getNextEvictable();
                    if (!removeEntry(nextEvictable, nextEvictable.getHash(), RemovalCause.SIZE)) {
                        throw new AssertionError();
                    }
                }
            }
        }

        @GuardedBy("this")
        public void expand() {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            int length = atomicReferenceArray.length();
            if (length >= 1073741824) {
                return;
            }
            int i2 = this.count;
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArrayNewEntryArray = newEntryArray(length << 1);
            this.threshold = (atomicReferenceArrayNewEntryArray.length() * 3) / 4;
            int length2 = atomicReferenceArrayNewEntryArray.length() - 1;
            for (int i3 = 0; i3 < length; i3++) {
                ReferenceEntry<K, V> next = atomicReferenceArray.get(i3);
                if (next != null) {
                    ReferenceEntry<K, V> next2 = next.getNext();
                    int hash = next.getHash() & length2;
                    if (next2 == null) {
                        atomicReferenceArrayNewEntryArray.set(hash, next);
                    } else {
                        ReferenceEntry<K, V> referenceEntry = next;
                        while (next2 != null) {
                            int hash2 = next2.getHash() & length2;
                            if (hash2 != hash) {
                                referenceEntry = next2;
                                hash = hash2;
                            }
                            next2 = next2.getNext();
                        }
                        atomicReferenceArrayNewEntryArray.set(hash, referenceEntry);
                        while (next != referenceEntry) {
                            int hash3 = next.getHash() & length2;
                            ReferenceEntry<K, V> referenceEntryCopyEntry = copyEntry(next, atomicReferenceArrayNewEntryArray.get(hash3));
                            if (referenceEntryCopyEntry != null) {
                                atomicReferenceArrayNewEntryArray.set(hash3, referenceEntryCopyEntry);
                            } else {
                                removeCollectedEntry(next);
                                i2--;
                            }
                            next = next.getNext();
                        }
                    }
                }
            }
            this.table = atomicReferenceArrayNewEntryArray;
            this.count = i2;
        }

        @GuardedBy("this")
        public void expireEntries(long j2) {
            ReferenceEntry<K, V> referenceEntryPeek;
            ReferenceEntry<K, V> referenceEntryPeek2;
            drainRecencyQueue();
            do {
                referenceEntryPeek = this.writeQueue.peek();
                if (referenceEntryPeek == null || !this.map.isExpired(referenceEntryPeek, j2)) {
                    do {
                        referenceEntryPeek2 = this.accessQueue.peek();
                        if (referenceEntryPeek2 == null || !this.map.isExpired(referenceEntryPeek2, j2)) {
                            return;
                        }
                    } while (removeEntry(referenceEntryPeek2, referenceEntryPeek2.getHash(), RemovalCause.EXPIRED));
                    throw new AssertionError();
                }
            } while (removeEntry(referenceEntryPeek, referenceEntryPeek.getHash(), RemovalCause.EXPIRED));
            throw new AssertionError();
        }

        public V get(K k2, int i2, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
            ReferenceEntry<K, V> entry;
            Preconditions.checkNotNull(k2);
            Preconditions.checkNotNull(cacheLoader);
            try {
                try {
                    if (this.count != 0 && (entry = getEntry(k2, i2)) != null) {
                        long j2 = this.map.ticker.read();
                        V liveValue = getLiveValue(entry, j2);
                        if (liveValue != null) {
                            recordRead(entry, j2);
                            this.statsCounter.recordHits(1);
                            return scheduleRefresh(entry, k2, i2, liveValue, j2, cacheLoader);
                        }
                        ValueReference<K, V> valueReference = entry.getValueReference();
                        if (valueReference.isLoading()) {
                            return waitForLoadingValue(entry, k2, valueReference);
                        }
                    }
                    return lockedGetOrLoad(k2, i2, cacheLoader);
                } catch (ExecutionException e2) {
                    Throwable cause = e2.getCause();
                    if (cause instanceof Error) {
                        throw new ExecutionError((Error) cause);
                    }
                    if (cause instanceof RuntimeException) {
                        throw new UncheckedExecutionException(cause);
                    }
                    throw e2;
                }
            } finally {
                postReadCleanup();
            }
        }

        public V getAndRecordStats(K k2, int i2, LoadingValueReference<K, V> loadingValueReference, ListenableFuture<V> listenableFuture) throws Throwable {
            V v2;
            try {
                v2 = (V) Uninterruptibles.getUninterruptibly(listenableFuture);
                try {
                    if (v2 != null) {
                        this.statsCounter.recordLoadSuccess(loadingValueReference.elapsedNanos());
                        storeLoadedValue(k2, i2, loadingValueReference, v2);
                        return v2;
                    }
                    throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + k2 + StrPool.DOT);
                } catch (Throwable th) {
                    th = th;
                    if (v2 == null) {
                        this.statsCounter.recordLoadException(loadingValueReference.elapsedNanos());
                        removeLoadingValue(k2, i2, loadingValueReference);
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                v2 = null;
            }
        }

        @NullableDecl
        public ReferenceEntry<K, V> getEntry(Object obj, int i2) {
            for (ReferenceEntry<K, V> first = getFirst(i2); first != null; first = first.getNext()) {
                if (first.getHash() == i2) {
                    K key = first.getKey();
                    if (key == null) {
                        tryDrainReferenceQueues();
                    } else if (this.map.keyEquivalence.equivalent(obj, key)) {
                        return first;
                    }
                }
            }
            return null;
        }

        public ReferenceEntry<K, V> getFirst(int i2) {
            return this.table.get(i2 & (r0.length() - 1));
        }

        @NullableDecl
        public ReferenceEntry<K, V> getLiveEntry(Object obj, int i2, long j2) {
            ReferenceEntry<K, V> entry = getEntry(obj, i2);
            if (entry == null) {
                return null;
            }
            if (!this.map.isExpired(entry, j2)) {
                return entry;
            }
            tryExpireEntries(j2);
            return null;
        }

        public V getLiveValue(ReferenceEntry<K, V> referenceEntry, long j2) {
            if (referenceEntry.getKey() == null) {
                tryDrainReferenceQueues();
                return null;
            }
            V v2 = referenceEntry.getValueReference().get();
            if (v2 == null) {
                tryDrainReferenceQueues();
                return null;
            }
            if (!this.map.isExpired(referenceEntry, j2)) {
                return v2;
            }
            tryExpireEntries(j2);
            return null;
        }

        @GuardedBy("this")
        public ReferenceEntry<K, V> getNextEvictable() {
            for (ReferenceEntry<K, V> referenceEntry : this.accessQueue) {
                if (referenceEntry.getValueReference().getWeight() > 0) {
                    return referenceEntry;
                }
            }
            throw new AssertionError();
        }

        public void initTable(AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray) {
            this.threshold = (atomicReferenceArray.length() * 3) / 4;
            if (!this.map.customWeigher()) {
                int i2 = this.threshold;
                if (i2 == this.maxSegmentWeight) {
                    this.threshold = i2 + 1;
                }
            }
            this.table = atomicReferenceArray;
        }

        @NullableDecl
        public LoadingValueReference<K, V> insertLoadingValueReference(K k2, int i2, boolean z2) {
            lock();
            try {
                long j2 = this.map.ticker.read();
                preWriteCleanup(j2);
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i2;
                ReferenceEntry<K, V> referenceEntry = (ReferenceEntry) atomicReferenceArray.get(length);
                for (ReferenceEntry next = referenceEntry; next != null; next = next.getNext()) {
                    Object key = next.getKey();
                    if (next.getHash() == i2 && key != null && this.map.keyEquivalence.equivalent(k2, key)) {
                        ValueReference<K, V> valueReference = next.getValueReference();
                        if (!valueReference.isLoading() && (!z2 || j2 - next.getWriteTime() >= this.map.refreshNanos)) {
                            this.modCount++;
                            LoadingValueReference<K, V> loadingValueReference = new LoadingValueReference<>(valueReference);
                            next.setValueReference(loadingValueReference);
                            return loadingValueReference;
                        }
                        unlock();
                        postWriteCleanup();
                        return null;
                    }
                }
                this.modCount++;
                LoadingValueReference<K, V> loadingValueReference2 = new LoadingValueReference<>();
                ReferenceEntry<K, V> referenceEntryNewEntry = newEntry(k2, i2, referenceEntry);
                referenceEntryNewEntry.setValueReference(loadingValueReference2);
                atomicReferenceArray.set(length, referenceEntryNewEntry);
                return loadingValueReference2;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        public ListenableFuture<V> loadAsync(final K k2, final int i2, final LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> cacheLoader) {
            final ListenableFuture<V> listenableFutureLoadFuture = loadingValueReference.loadFuture(k2, cacheLoader);
            listenableFutureLoadFuture.addListener(new Runnable() { // from class: com.google.common.cache.LocalCache.Segment.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Segment.this.getAndRecordStats(k2, i2, loadingValueReference, listenableFutureLoadFuture);
                    } catch (Throwable th) {
                        LocalCache.logger.log(Level.WARNING, "Exception thrown during refresh", th);
                        loadingValueReference.setException(th);
                    }
                }
            }, MoreExecutors.directExecutor());
            return listenableFutureLoadFuture;
        }

        public V loadSync(K k2, int i2, LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
            return getAndRecordStats(k2, i2, loadingValueReference, loadingValueReference.loadFuture(k2, cacheLoader));
        }

        /* JADX WARN: Code restructure failed: missing block: B:27:0x00a0, code lost:
        
            if (r1 == false) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x00a2, code lost:
        
            r15 = new com.google.common.cache.LocalCache.LoadingValueReference<>();
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x00a7, code lost:
        
            if (r14 != null) goto L31;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x00a9, code lost:
        
            r14 = newEntry(r18, r19, r13);
            r14.setValueReference(r15);
            r11.set(r12, r14);
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x00b4, code lost:
        
            r14.setValueReference(r15);
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x00bd, code lost:
        
            if (r1 == false) goto L46;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x00bf, code lost:
        
            monitor-enter(r14);
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x00c2, code lost:
        
            r0 = loadSync(r18, r19, r15, r20);
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x00c6, code lost:
        
            monitor-exit(r14);
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x00cc, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x00d0, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x00d1, code lost:
        
            r17.statsCounter.recordMisses(1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x00d6, code lost:
        
            throw r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00db, code lost:
        
            return waitForLoadingValue(r14, r18, r2);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public V lockedGetOrLoad(K r18, int r19, com.google.common.cache.CacheLoader<? super K, V> r20) throws java.util.concurrent.ExecutionException {
            /*
                Method dump skipped, instructions count: 228
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.lockedGetOrLoad(java.lang.Object, int, com.google.common.cache.CacheLoader):java.lang.Object");
        }

        /* JADX WARN: Multi-variable type inference failed */
        @GuardedBy("this")
        public ReferenceEntry<K, V> newEntry(K k2, int i2, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            return this.map.entryFactory.newEntry(this, Preconditions.checkNotNull(k2), i2, referenceEntry);
        }

        public AtomicReferenceArray<ReferenceEntry<K, V>> newEntryArray(int i2) {
            return new AtomicReferenceArray<>(i2);
        }

        public void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 63) == 0) {
                cleanUp();
            }
        }

        public void postWriteCleanup() {
            runUnlockedCleanup();
        }

        @GuardedBy("this")
        public void preWriteCleanup(long j2) {
            runLockedCleanup(j2);
        }

        /* JADX WARN: Code restructure failed: missing block: B:22:0x008f, code lost:
        
            return null;
         */
        @org.checkerframework.checker.nullness.compatqual.NullableDecl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public V put(K r15, int r16, V r17, boolean r18) {
            /*
                Method dump skipped, instructions count: 235
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.put(java.lang.Object, int, java.lang.Object, boolean):java.lang.Object");
        }

        public boolean reclaimKey(ReferenceEntry<K, V> referenceEntry, int i2) {
            lock();
            try {
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i2;
                ReferenceEntry<K, V> referenceEntry2 = atomicReferenceArray.get(length);
                for (ReferenceEntry<K, V> next = referenceEntry2; next != null; next = next.getNext()) {
                    if (next == referenceEntry) {
                        this.modCount++;
                        ReferenceEntry<K, V> referenceEntryRemoveValueFromChain = removeValueFromChain(referenceEntry2, next, next.getKey(), i2, next.getValueReference().get(), next.getValueReference(), RemovalCause.COLLECTED);
                        int i3 = this.count - 1;
                        atomicReferenceArray.set(length, referenceEntryRemoveValueFromChain);
                        this.count = i3;
                        return true;
                    }
                }
                unlock();
                postWriteCleanup();
                return false;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        public boolean reclaimValue(K k2, int i2, ValueReference<K, V> valueReference) {
            lock();
            try {
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i2;
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(length);
                for (ReferenceEntry<K, V> next = referenceEntry; next != null; next = next.getNext()) {
                    K key = next.getKey();
                    if (next.getHash() == i2 && key != null && this.map.keyEquivalence.equivalent(k2, key)) {
                        if (next.getValueReference() != valueReference) {
                            unlock();
                            if (!isHeldByCurrentThread()) {
                                postWriteCleanup();
                            }
                            return false;
                        }
                        this.modCount++;
                        ReferenceEntry<K, V> referenceEntryRemoveValueFromChain = removeValueFromChain(referenceEntry, next, key, i2, valueReference.get(), valueReference, RemovalCause.COLLECTED);
                        int i3 = this.count - 1;
                        atomicReferenceArray.set(length, referenceEntryRemoveValueFromChain);
                        this.count = i3;
                        return true;
                    }
                }
                unlock();
                if (!isHeldByCurrentThread()) {
                    postWriteCleanup();
                }
                return false;
            } finally {
                unlock();
                if (!isHeldByCurrentThread()) {
                    postWriteCleanup();
                }
            }
        }

        @GuardedBy("this")
        public void recordLockedRead(ReferenceEntry<K, V> referenceEntry, long j2) {
            if (this.map.recordsAccess()) {
                referenceEntry.setAccessTime(j2);
            }
            this.accessQueue.add(referenceEntry);
        }

        public void recordRead(ReferenceEntry<K, V> referenceEntry, long j2) {
            if (this.map.recordsAccess()) {
                referenceEntry.setAccessTime(j2);
            }
            this.recencyQueue.add(referenceEntry);
        }

        @GuardedBy("this")
        public void recordWrite(ReferenceEntry<K, V> referenceEntry, int i2, long j2) {
            drainRecencyQueue();
            this.totalWeight += i2;
            if (this.map.recordsAccess()) {
                referenceEntry.setAccessTime(j2);
            }
            if (this.map.recordsWrite()) {
                referenceEntry.setWriteTime(j2);
            }
            this.accessQueue.add(referenceEntry);
            this.writeQueue.add(referenceEntry);
        }

        @NullableDecl
        public V refresh(K k2, int i2, CacheLoader<? super K, V> cacheLoader, boolean z2) {
            LoadingValueReference<K, V> loadingValueReferenceInsertLoadingValueReference = insertLoadingValueReference(k2, i2, z2);
            if (loadingValueReferenceInsertLoadingValueReference == null) {
                return null;
            }
            ListenableFuture<V> listenableFutureLoadAsync = loadAsync(k2, i2, loadingValueReferenceInsertLoadingValueReference, cacheLoader);
            if (listenableFutureLoadAsync.isDone()) {
                try {
                    return (V) Uninterruptibles.getUninterruptibly(listenableFutureLoadAsync);
                } catch (Throwable unused) {
                }
            }
            return null;
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0038, code lost:
        
            r9 = r5.getValueReference();
            r12 = r9.get();
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x0040, code lost:
        
            if (r12 == null) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x0042, code lost:
        
            r2 = com.google.common.cache.RemovalCause.EXPLICIT;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0044, code lost:
        
            r10 = r2;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x004a, code lost:
        
            if (r9.isActive() == false) goto L21;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x004c, code lost:
        
            r2 = com.google.common.cache.RemovalCause.COLLECTED;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x004f, code lost:
        
            r11.modCount++;
            r13 = removeValueFromChain(r4, r5, r6, r13, r12, r9, r10);
            r2 = r11.count - 1;
            r0.set(r1, r13);
            r11.count = r2;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x006b, code lost:
        
            return r12;
         */
        @org.checkerframework.checker.nullness.compatqual.NullableDecl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public V remove(java.lang.Object r12, int r13) {
            /*
                r11 = this;
                r11.lock()
                com.google.common.cache.LocalCache<K, V> r0 = r11.map     // Catch: java.lang.Throwable -> L78
                com.google.common.base.Ticker r0 = r0.ticker     // Catch: java.lang.Throwable -> L78
                long r0 = r0.read()     // Catch: java.lang.Throwable -> L78
                r11.preWriteCleanup(r0)     // Catch: java.lang.Throwable -> L78
                java.util.concurrent.atomic.AtomicReferenceArray<com.google.common.cache.ReferenceEntry<K, V>> r0 = r11.table     // Catch: java.lang.Throwable -> L78
                int r1 = r0.length()     // Catch: java.lang.Throwable -> L78
                int r1 = r1 + (-1)
                r1 = r1 & r13
                java.lang.Object r2 = r0.get(r1)     // Catch: java.lang.Throwable -> L78
                r4 = r2
                com.google.common.cache.ReferenceEntry r4 = (com.google.common.cache.ReferenceEntry) r4     // Catch: java.lang.Throwable -> L78
                r5 = r4
            L1f:
                r2 = 0
                if (r5 == 0) goto L6c
                java.lang.Object r6 = r5.getKey()     // Catch: java.lang.Throwable -> L78
                int r3 = r5.getHash()     // Catch: java.lang.Throwable -> L78
                if (r3 != r13) goto L73
                if (r6 == 0) goto L73
                com.google.common.cache.LocalCache<K, V> r3 = r11.map     // Catch: java.lang.Throwable -> L78
                com.google.common.base.Equivalence<java.lang.Object> r3 = r3.keyEquivalence     // Catch: java.lang.Throwable -> L78
                boolean r3 = r3.equivalent(r12, r6)     // Catch: java.lang.Throwable -> L78
                if (r3 == 0) goto L73
                com.google.common.cache.LocalCache$ValueReference r9 = r5.getValueReference()     // Catch: java.lang.Throwable -> L78
                java.lang.Object r12 = r9.get()     // Catch: java.lang.Throwable -> L78
                if (r12 == 0) goto L46
                com.google.common.cache.RemovalCause r2 = com.google.common.cache.RemovalCause.EXPLICIT     // Catch: java.lang.Throwable -> L78
            L44:
                r10 = r2
                goto L4f
            L46:
                boolean r3 = r9.isActive()     // Catch: java.lang.Throwable -> L78
                if (r3 == 0) goto L6c
                com.google.common.cache.RemovalCause r2 = com.google.common.cache.RemovalCause.COLLECTED     // Catch: java.lang.Throwable -> L78
                goto L44
            L4f:
                int r2 = r11.modCount     // Catch: java.lang.Throwable -> L78
                int r2 = r2 + 1
                r11.modCount = r2     // Catch: java.lang.Throwable -> L78
                r3 = r11
                r7 = r13
                r8 = r12
                com.google.common.cache.ReferenceEntry r13 = r3.removeValueFromChain(r4, r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L78
                int r2 = r11.count     // Catch: java.lang.Throwable -> L78
                int r2 = r2 + (-1)
                r0.set(r1, r13)     // Catch: java.lang.Throwable -> L78
                r11.count = r2     // Catch: java.lang.Throwable -> L78
                r11.unlock()
                r11.postWriteCleanup()
                return r12
            L6c:
                r11.unlock()
                r11.postWriteCleanup()
                return r2
            L73:
                com.google.common.cache.ReferenceEntry r5 = r5.getNext()     // Catch: java.lang.Throwable -> L78
                goto L1f
            L78:
                r12 = move-exception
                r11.unlock()
                r11.postWriteCleanup()
                throw r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.remove(java.lang.Object, int):java.lang.Object");
        }

        @GuardedBy("this")
        public void removeCollectedEntry(ReferenceEntry<K, V> referenceEntry) {
            enqueueNotification(referenceEntry.getKey(), referenceEntry.getHash(), referenceEntry.getValueReference().get(), referenceEntry.getValueReference().getWeight(), RemovalCause.COLLECTED);
            this.writeQueue.remove(referenceEntry);
            this.accessQueue.remove(referenceEntry);
        }

        @VisibleForTesting
        @GuardedBy("this")
        public boolean removeEntry(ReferenceEntry<K, V> referenceEntry, int i2, RemovalCause removalCause) {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            int length = (atomicReferenceArray.length() - 1) & i2;
            ReferenceEntry<K, V> referenceEntry2 = atomicReferenceArray.get(length);
            for (ReferenceEntry<K, V> next = referenceEntry2; next != null; next = next.getNext()) {
                if (next == referenceEntry) {
                    this.modCount++;
                    ReferenceEntry<K, V> referenceEntryRemoveValueFromChain = removeValueFromChain(referenceEntry2, next, next.getKey(), i2, next.getValueReference().get(), next.getValueReference(), removalCause);
                    int i3 = this.count - 1;
                    atomicReferenceArray.set(length, referenceEntryRemoveValueFromChain);
                    this.count = i3;
                    return true;
                }
            }
            return false;
        }

        @NullableDecl
        @GuardedBy("this")
        public ReferenceEntry<K, V> removeEntryFromChain(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            int i2 = this.count;
            ReferenceEntry<K, V> next = referenceEntry2.getNext();
            while (referenceEntry != referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = copyEntry(referenceEntry, next);
                if (referenceEntryCopyEntry != null) {
                    next = referenceEntryCopyEntry;
                } else {
                    removeCollectedEntry(referenceEntry);
                    i2--;
                }
                referenceEntry = referenceEntry.getNext();
            }
            this.count = i2;
            return next;
        }

        public boolean removeLoadingValue(K k2, int i2, LoadingValueReference<K, V> loadingValueReference) {
            lock();
            try {
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i2;
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(length);
                ReferenceEntry<K, V> next = referenceEntry;
                while (true) {
                    if (next == null) {
                        break;
                    }
                    K key = next.getKey();
                    if (next.getHash() != i2 || key == null || !this.map.keyEquivalence.equivalent(k2, key)) {
                        next = next.getNext();
                    } else if (next.getValueReference() == loadingValueReference) {
                        if (loadingValueReference.isActive()) {
                            next.setValueReference(loadingValueReference.getOldValue());
                        } else {
                            atomicReferenceArray.set(length, removeEntryFromChain(referenceEntry, next));
                        }
                        return true;
                    }
                }
                return false;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        @NullableDecl
        @GuardedBy("this")
        public ReferenceEntry<K, V> removeValueFromChain(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, @NullableDecl K k2, int i2, V v2, ValueReference<K, V> valueReference, RemovalCause removalCause) {
            enqueueNotification(k2, i2, v2, valueReference.getWeight(), removalCause);
            this.writeQueue.remove(referenceEntry2);
            this.accessQueue.remove(referenceEntry2);
            if (!valueReference.isLoading()) {
                return removeEntryFromChain(referenceEntry, referenceEntry2);
            }
            valueReference.notifyNewValue(null);
            return referenceEntry;
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x006f, code lost:
        
            return false;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean replace(K r18, int r19, V r20, V r21) {
            /*
                r17 = this;
                r9 = r17
                r0 = r19
                r17.lock()
                com.google.common.cache.LocalCache<K, V> r1 = r9.map     // Catch: java.lang.Throwable -> Lb5
                com.google.common.base.Ticker r1 = r1.ticker     // Catch: java.lang.Throwable -> Lb5
                long r7 = r1.read()     // Catch: java.lang.Throwable -> Lb5
                r9.preWriteCleanup(r7)     // Catch: java.lang.Throwable -> Lb5
                java.util.concurrent.atomic.AtomicReferenceArray<com.google.common.cache.ReferenceEntry<K, V>> r10 = r9.table     // Catch: java.lang.Throwable -> Lb5
                int r1 = r10.length()     // Catch: java.lang.Throwable -> Lb5
                r11 = 1
                int r1 = r1 - r11
                r12 = r0 & r1
                java.lang.Object r1 = r10.get(r12)     // Catch: java.lang.Throwable -> Lb5
                r2 = r1
                com.google.common.cache.ReferenceEntry r2 = (com.google.common.cache.ReferenceEntry) r2     // Catch: java.lang.Throwable -> Lb5
                r13 = r2
            L24:
                r14 = 0
                if (r13 == 0) goto L69
                java.lang.Object r4 = r13.getKey()     // Catch: java.lang.Throwable -> Lb5
                int r1 = r13.getHash()     // Catch: java.lang.Throwable -> Lb5
                if (r1 != r0) goto Lab
                if (r4 == 0) goto Lab
                com.google.common.cache.LocalCache<K, V> r1 = r9.map     // Catch: java.lang.Throwable -> Lb5
                com.google.common.base.Equivalence<java.lang.Object> r1 = r1.keyEquivalence     // Catch: java.lang.Throwable -> Lb5
                r15 = r18
                boolean r1 = r1.equivalent(r15, r4)     // Catch: java.lang.Throwable -> Lb5
                if (r1 == 0) goto Lad
                com.google.common.cache.LocalCache$ValueReference r16 = r13.getValueReference()     // Catch: java.lang.Throwable -> Lb5
                java.lang.Object r6 = r16.get()     // Catch: java.lang.Throwable -> Lb5
                if (r6 != 0) goto L70
                boolean r1 = r16.isActive()     // Catch: java.lang.Throwable -> Lb5
                if (r1 == 0) goto L69
                int r1 = r9.modCount     // Catch: java.lang.Throwable -> Lb5
                int r1 = r1 + r11
                r9.modCount = r1     // Catch: java.lang.Throwable -> Lb5
                com.google.common.cache.RemovalCause r8 = com.google.common.cache.RemovalCause.COLLECTED     // Catch: java.lang.Throwable -> Lb5
                r1 = r17
                r3 = r13
                r5 = r19
                r7 = r16
                com.google.common.cache.ReferenceEntry r0 = r1.removeValueFromChain(r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> Lb5
                int r1 = r9.count     // Catch: java.lang.Throwable -> Lb5
                int r1 = r1 - r11
                r10.set(r12, r0)     // Catch: java.lang.Throwable -> Lb5
                r9.count = r1     // Catch: java.lang.Throwable -> Lb5
            L69:
                r17.unlock()
                r17.postWriteCleanup()
                return r14
            L70:
                com.google.common.cache.LocalCache<K, V> r1 = r9.map     // Catch: java.lang.Throwable -> Lb5
                com.google.common.base.Equivalence<java.lang.Object> r1 = r1.valueEquivalence     // Catch: java.lang.Throwable -> Lb5
                r3 = r20
                boolean r1 = r1.equivalent(r3, r6)     // Catch: java.lang.Throwable -> Lb5
                if (r1 == 0) goto La7
                int r1 = r9.modCount     // Catch: java.lang.Throwable -> Lb5
                int r1 = r1 + r11
                r9.modCount = r1     // Catch: java.lang.Throwable -> Lb5
                int r5 = r16.getWeight()     // Catch: java.lang.Throwable -> Lb5
                com.google.common.cache.RemovalCause r10 = com.google.common.cache.RemovalCause.REPLACED     // Catch: java.lang.Throwable -> Lb5
                r1 = r17
                r2 = r18
                r3 = r19
                r4 = r6
                r6 = r10
                r1.enqueueNotification(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> Lb5
                r1 = r17
                r2 = r13
                r3 = r18
                r4 = r21
                r5 = r7
                r1.setValue(r2, r3, r4, r5)     // Catch: java.lang.Throwable -> Lb5
                r9.evictEntries(r13)     // Catch: java.lang.Throwable -> Lb5
                r17.unlock()
                r17.postWriteCleanup()
                return r11
            La7:
                r9.recordLockedRead(r13, r7)     // Catch: java.lang.Throwable -> Lb5
                goto L69
            Lab:
                r15 = r18
            Lad:
                r3 = r20
                com.google.common.cache.ReferenceEntry r13 = r13.getNext()     // Catch: java.lang.Throwable -> Lb5
                goto L24
            Lb5:
                r0 = move-exception
                r17.unlock()
                r17.postWriteCleanup()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.replace(java.lang.Object, int, java.lang.Object, java.lang.Object):boolean");
        }

        public void runLockedCleanup(long j2) {
            if (tryLock()) {
                try {
                    drainReferenceQueues();
                    expireEntries(j2);
                    this.readCount.set(0);
                } finally {
                    unlock();
                }
            }
        }

        public void runUnlockedCleanup() {
            if (isHeldByCurrentThread()) {
                return;
            }
            this.map.processPendingNotifications();
        }

        public V scheduleRefresh(ReferenceEntry<K, V> referenceEntry, K k2, int i2, V v2, long j2, CacheLoader<? super K, V> cacheLoader) {
            V vRefresh;
            return (!this.map.refreshes() || j2 - referenceEntry.getWriteTime() <= this.map.refreshNanos || referenceEntry.getValueReference().isLoading() || (vRefresh = refresh(k2, i2, cacheLoader, true)) == null) ? v2 : vRefresh;
        }

        @GuardedBy("this")
        public void setValue(ReferenceEntry<K, V> referenceEntry, K k2, V v2, long j2) {
            ValueReference<K, V> valueReference = referenceEntry.getValueReference();
            int iWeigh = this.map.weigher.weigh(k2, v2);
            Preconditions.checkState(iWeigh >= 0, "Weights must be non-negative");
            referenceEntry.setValueReference(this.map.valueStrength.referenceValue(this, referenceEntry, v2, iWeigh));
            recordWrite(referenceEntry, iWeigh, j2);
            valueReference.notifyNewValue(v2);
        }

        public boolean storeLoadedValue(K k2, int i2, LoadingValueReference<K, V> loadingValueReference, V v2) {
            lock();
            try {
                long j2 = this.map.ticker.read();
                preWriteCleanup(j2);
                int i3 = this.count + 1;
                if (i3 > this.threshold) {
                    expand();
                    i3 = this.count + 1;
                }
                int i4 = i3;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = i2 & (atomicReferenceArray.length() - 1);
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(length);
                ReferenceEntry<K, V> next = referenceEntry;
                while (true) {
                    if (next == null) {
                        this.modCount++;
                        ReferenceEntry<K, V> referenceEntryNewEntry = newEntry(k2, i2, referenceEntry);
                        setValue(referenceEntryNewEntry, k2, v2, j2);
                        atomicReferenceArray.set(length, referenceEntryNewEntry);
                        this.count = i4;
                        evictEntries(referenceEntryNewEntry);
                        break;
                    }
                    K key = next.getKey();
                    if (next.getHash() == i2 && key != null && this.map.keyEquivalence.equivalent(k2, key)) {
                        ValueReference<K, V> valueReference = next.getValueReference();
                        V v3 = valueReference.get();
                        if (loadingValueReference != valueReference && (v3 != null || valueReference == LocalCache.UNSET)) {
                            enqueueNotification(k2, i2, v2, 0, RemovalCause.REPLACED);
                            unlock();
                            postWriteCleanup();
                            return false;
                        }
                        this.modCount++;
                        if (loadingValueReference.isActive()) {
                            enqueueNotification(k2, i2, v3, loadingValueReference.getWeight(), v3 == null ? RemovalCause.COLLECTED : RemovalCause.REPLACED);
                            i4--;
                        }
                        setValue(next, k2, v2, j2);
                        this.count = i4;
                        evictEntries(next);
                    } else {
                        next = next.getNext();
                    }
                }
                return true;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        public void tryDrainReferenceQueues() {
            if (tryLock()) {
                try {
                    drainReferenceQueues();
                } finally {
                    unlock();
                }
            }
        }

        public void tryExpireEntries(long j2) {
            if (tryLock()) {
                try {
                    expireEntries(j2);
                } finally {
                    unlock();
                }
            }
        }

        public V waitForLoadingValue(ReferenceEntry<K, V> referenceEntry, K k2, ValueReference<K, V> valueReference) throws ExecutionException {
            if (!valueReference.isLoading()) {
                throw new AssertionError();
            }
            Preconditions.checkState(!Thread.holdsLock(referenceEntry), "Recursive load of: %s", k2);
            try {
                V vWaitForValue = valueReference.waitForValue();
                if (vWaitForValue != null) {
                    recordRead(referenceEntry, this.map.ticker.read());
                    return vWaitForValue;
                }
                throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + k2 + StrPool.DOT);
            } finally {
                this.statsCounter.recordMisses(1);
            }
        }

        @NullableDecl
        public V get(Object obj, int i2) {
            try {
                if (this.count != 0) {
                    long j2 = this.map.ticker.read();
                    ReferenceEntry<K, V> liveEntry = getLiveEntry(obj, i2, j2);
                    if (liveEntry == null) {
                        return null;
                    }
                    V v2 = liveEntry.getValueReference().get();
                    if (v2 != null) {
                        recordRead(liveEntry, j2);
                        return scheduleRefresh(liveEntry, liveEntry.getKey(), i2, v2, j2, this.map.defaultLoader);
                    }
                    tryDrainReferenceQueues();
                }
                return null;
            } finally {
                postReadCleanup();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0038, code lost:
        
            r10 = r6.getValueReference();
            r9 = r10.get();
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x0048, code lost:
        
            if (r12.map.valueEquivalence.equivalent(r15, r9) == false) goto L14;
         */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x004a, code lost:
        
            r13 = com.google.common.cache.RemovalCause.EXPLICIT;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x004d, code lost:
        
            if (r9 != null) goto L24;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0053, code lost:
        
            if (r10.isActive() == false) goto L24;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0055, code lost:
        
            r13 = com.google.common.cache.RemovalCause.COLLECTED;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0057, code lost:
        
            r12.modCount++;
            r14 = removeValueFromChain(r5, r6, r7, r14, r9, r10, r13);
            r15 = r12.count - 1;
            r0.set(r1, r14);
            r12.count = r15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x006d, code lost:
        
            if (r13 != com.google.common.cache.RemovalCause.EXPLICIT) goto L21;
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x0070, code lost:
        
            r2 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0077, code lost:
        
            return r2;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean remove(java.lang.Object r13, int r14, java.lang.Object r15) {
            /*
                r12 = this;
                r12.lock()
                com.google.common.cache.LocalCache<K, V> r0 = r12.map     // Catch: java.lang.Throwable -> L84
                com.google.common.base.Ticker r0 = r0.ticker     // Catch: java.lang.Throwable -> L84
                long r0 = r0.read()     // Catch: java.lang.Throwable -> L84
                r12.preWriteCleanup(r0)     // Catch: java.lang.Throwable -> L84
                java.util.concurrent.atomic.AtomicReferenceArray<com.google.common.cache.ReferenceEntry<K, V>> r0 = r12.table     // Catch: java.lang.Throwable -> L84
                int r1 = r0.length()     // Catch: java.lang.Throwable -> L84
                r2 = 1
                int r1 = r1 - r2
                r1 = r1 & r14
                java.lang.Object r3 = r0.get(r1)     // Catch: java.lang.Throwable -> L84
                r5 = r3
                com.google.common.cache.ReferenceEntry r5 = (com.google.common.cache.ReferenceEntry) r5     // Catch: java.lang.Throwable -> L84
                r6 = r5
            L1f:
                r3 = 0
                if (r6 == 0) goto L78
                java.lang.Object r7 = r6.getKey()     // Catch: java.lang.Throwable -> L84
                int r4 = r6.getHash()     // Catch: java.lang.Throwable -> L84
                if (r4 != r14) goto L7f
                if (r7 == 0) goto L7f
                com.google.common.cache.LocalCache<K, V> r4 = r12.map     // Catch: java.lang.Throwable -> L84
                com.google.common.base.Equivalence<java.lang.Object> r4 = r4.keyEquivalence     // Catch: java.lang.Throwable -> L84
                boolean r4 = r4.equivalent(r13, r7)     // Catch: java.lang.Throwable -> L84
                if (r4 == 0) goto L7f
                com.google.common.cache.LocalCache$ValueReference r10 = r6.getValueReference()     // Catch: java.lang.Throwable -> L84
                java.lang.Object r9 = r10.get()     // Catch: java.lang.Throwable -> L84
                com.google.common.cache.LocalCache<K, V> r13 = r12.map     // Catch: java.lang.Throwable -> L84
                com.google.common.base.Equivalence<java.lang.Object> r13 = r13.valueEquivalence     // Catch: java.lang.Throwable -> L84
                boolean r13 = r13.equivalent(r15, r9)     // Catch: java.lang.Throwable -> L84
                if (r13 == 0) goto L4d
                com.google.common.cache.RemovalCause r13 = com.google.common.cache.RemovalCause.EXPLICIT     // Catch: java.lang.Throwable -> L84
                goto L57
            L4d:
                if (r9 != 0) goto L78
                boolean r13 = r10.isActive()     // Catch: java.lang.Throwable -> L84
                if (r13 == 0) goto L78
                com.google.common.cache.RemovalCause r13 = com.google.common.cache.RemovalCause.COLLECTED     // Catch: java.lang.Throwable -> L84
            L57:
                int r15 = r12.modCount     // Catch: java.lang.Throwable -> L84
                int r15 = r15 + r2
                r12.modCount = r15     // Catch: java.lang.Throwable -> L84
                r4 = r12
                r8 = r14
                r11 = r13
                com.google.common.cache.ReferenceEntry r14 = r4.removeValueFromChain(r5, r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L84
                int r15 = r12.count     // Catch: java.lang.Throwable -> L84
                int r15 = r15 - r2
                r0.set(r1, r14)     // Catch: java.lang.Throwable -> L84
                r12.count = r15     // Catch: java.lang.Throwable -> L84
                com.google.common.cache.RemovalCause r14 = com.google.common.cache.RemovalCause.EXPLICIT     // Catch: java.lang.Throwable -> L84
                if (r13 != r14) goto L70
                goto L71
            L70:
                r2 = r3
            L71:
                r12.unlock()
                r12.postWriteCleanup()
                return r2
            L78:
                r12.unlock()
                r12.postWriteCleanup()
                return r3
            L7f:
                com.google.common.cache.ReferenceEntry r6 = r6.getNext()     // Catch: java.lang.Throwable -> L84
                goto L1f
            L84:
                r13 = move-exception
                r12.unlock()
                r12.postWriteCleanup()
                throw r13
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.remove(java.lang.Object, int, java.lang.Object):boolean");
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0072, code lost:
        
            return null;
         */
        @org.checkerframework.checker.nullness.compatqual.NullableDecl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public V replace(K r18, int r19, V r20) {
            /*
                r17 = this;
                r9 = r17
                r0 = r19
                r17.lock()
                com.google.common.cache.LocalCache<K, V> r1 = r9.map     // Catch: java.lang.Throwable -> La7
                com.google.common.base.Ticker r1 = r1.ticker     // Catch: java.lang.Throwable -> La7
                long r7 = r1.read()     // Catch: java.lang.Throwable -> La7
                r9.preWriteCleanup(r7)     // Catch: java.lang.Throwable -> La7
                java.util.concurrent.atomic.AtomicReferenceArray<com.google.common.cache.ReferenceEntry<K, V>> r10 = r9.table     // Catch: java.lang.Throwable -> La7
                int r1 = r10.length()     // Catch: java.lang.Throwable -> La7
                int r1 = r1 + (-1)
                r11 = r0 & r1
                java.lang.Object r1 = r10.get(r11)     // Catch: java.lang.Throwable -> La7
                r2 = r1
                com.google.common.cache.ReferenceEntry r2 = (com.google.common.cache.ReferenceEntry) r2     // Catch: java.lang.Throwable -> La7
                r12 = r2
            L24:
                r13 = 0
                if (r12 == 0) goto L6c
                java.lang.Object r4 = r12.getKey()     // Catch: java.lang.Throwable -> La7
                int r1 = r12.getHash()     // Catch: java.lang.Throwable -> La7
                if (r1 != r0) goto L9f
                if (r4 == 0) goto L9f
                com.google.common.cache.LocalCache<K, V> r1 = r9.map     // Catch: java.lang.Throwable -> La7
                com.google.common.base.Equivalence<java.lang.Object> r1 = r1.keyEquivalence     // Catch: java.lang.Throwable -> La7
                r14 = r18
                boolean r1 = r1.equivalent(r14, r4)     // Catch: java.lang.Throwable -> La7
                if (r1 == 0) goto La1
                com.google.common.cache.LocalCache$ValueReference r15 = r12.getValueReference()     // Catch: java.lang.Throwable -> La7
                java.lang.Object r16 = r15.get()     // Catch: java.lang.Throwable -> La7
                if (r16 != 0) goto L73
                boolean r1 = r15.isActive()     // Catch: java.lang.Throwable -> La7
                if (r1 == 0) goto L6c
                int r1 = r9.modCount     // Catch: java.lang.Throwable -> La7
                int r1 = r1 + 1
                r9.modCount = r1     // Catch: java.lang.Throwable -> La7
                com.google.common.cache.RemovalCause r8 = com.google.common.cache.RemovalCause.COLLECTED     // Catch: java.lang.Throwable -> La7
                r1 = r17
                r3 = r12
                r5 = r19
                r6 = r16
                r7 = r15
                com.google.common.cache.ReferenceEntry r0 = r1.removeValueFromChain(r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> La7
                int r1 = r9.count     // Catch: java.lang.Throwable -> La7
                int r1 = r1 + (-1)
                r10.set(r11, r0)     // Catch: java.lang.Throwable -> La7
                r9.count = r1     // Catch: java.lang.Throwable -> La7
            L6c:
                r17.unlock()
                r17.postWriteCleanup()
                return r13
            L73:
                int r1 = r9.modCount     // Catch: java.lang.Throwable -> La7
                int r1 = r1 + 1
                r9.modCount = r1     // Catch: java.lang.Throwable -> La7
                int r5 = r15.getWeight()     // Catch: java.lang.Throwable -> La7
                com.google.common.cache.RemovalCause r6 = com.google.common.cache.RemovalCause.REPLACED     // Catch: java.lang.Throwable -> La7
                r1 = r17
                r2 = r18
                r3 = r19
                r4 = r16
                r1.enqueueNotification(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> La7
                r1 = r17
                r2 = r12
                r3 = r18
                r4 = r20
                r5 = r7
                r1.setValue(r2, r3, r4, r5)     // Catch: java.lang.Throwable -> La7
                r9.evictEntries(r12)     // Catch: java.lang.Throwable -> La7
                r17.unlock()
                r17.postWriteCleanup()
                return r16
            L9f:
                r14 = r18
            La1:
                com.google.common.cache.ReferenceEntry r12 = r12.getNext()     // Catch: java.lang.Throwable -> La7
                goto L24
            La7:
                r0 = move-exception
                r17.unlock()
                r17.postWriteCleanup()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.replace(java.lang.Object, int, java.lang.Object):java.lang.Object");
        }
    }
}
