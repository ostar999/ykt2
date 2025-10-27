package kotlinx.coroutines.debug.internal;

import androidx.exifinterface.media.ExifInterface;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.google.common.util.concurrent.b;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.AbstractMutableMap;
import kotlin.collections.AbstractMutableSet;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.debug.internal.ConcurrentWeakMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0010#\n\u0002\u0010'\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\b\u0000\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u0001*\b\b\u0001\u0010\u0003*\u00020\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010*:\u0003&'(B\u0011\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u001b\u0010\u000b\u001a\u00020\n2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\bH\u0002¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u00020\nH\u0016¢\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u000f\u001a\u00020\nH\u0002¢\u0006\u0004\b\u000f\u0010\u000eJ\u001a\u0010\u0011\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0004\b\u0011\u0010\u0012J!\u0010\u0014\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00028\u0001H\u0016¢\u0006\u0004\b\u0014\u0010\u0015J#\u0010\u0016\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u00002\b\u0010\u0013\u001a\u0004\u0018\u00018\u0001H\u0002¢\u0006\u0004\b\u0016\u0010\u0015J\u0019\u0010\u0017\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u0000H\u0016¢\u0006\u0004\b\u0017\u0010\u0012J\r\u0010\u0018\u001a\u00020\n¢\u0006\u0004\b\u0018\u0010\u000eR&\u0010\u001d\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001a0\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001cR\u0014\u0010#\u001a\u00020 8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u001c\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010$8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010%¨\u0006)"}, d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "weakRefQueue", "<init>", "(Z)V", "Lkotlinx/coroutines/debug/internal/HashedWeakRef;", "w", "", "cleanWeakRef", "(Lkotlinx/coroutines/debug/internal/HashedWeakRef;)V", PLVDocumentMarkToolType.CLEAR, "()V", "decrementSize", "key", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "value", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "putSynchronized", PLVRemoveMicSiteEvent.EVENT_NAME, "runWeakRefQueueCleaningLoopUntilInterrupted", "", "", "getEntries", "()Ljava/util/Set;", "entries", "getKeys", "keys", "", "getSize", "()I", DatabaseManager.SIZE, "Ljava/lang/ref/ReferenceQueue;", "Ljava/lang/ref/ReferenceQueue;", "Core", "Entry", "KeyValueSet", "kotlinx-coroutines-core", "Lkotlin/collections/AbstractMutableMap;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class ConcurrentWeakMap<K, V> extends AbstractMutableMap<K, V> {
    private static final /* synthetic */ AtomicIntegerFieldUpdater _size$FU = AtomicIntegerFieldUpdater.newUpdater(ConcurrentWeakMap.class, "_size");

    @NotNull
    private volatile /* synthetic */ int _size;

    /* renamed from: core, reason: collision with root package name */
    @NotNull
    volatile /* synthetic */ Object f27649core;

    @Nullable
    private final ReferenceQueue<K> weakRefQueue;

    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010)\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0082\u0004\u0018\u00002\u00020\u0018:\u0001#B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u0019\u0010\b\u001a\u00020\u00072\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u000b\u001a\u0004\u0018\u00018\u00012\u0006\u0010\n\u001a\u00028\u0000¢\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000e\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ3\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00020\u0013\"\u0004\b\u0002\u0010\u00102\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0011¢\u0006\u0004\b\u0014\u0010\u0015J3\u0010\u0019\u001a\u0004\u0018\u00010\u00182\u0006\u0010\n\u001a\u00028\u00002\b\u0010\u0016\u001a\u0004\u0018\u00018\u00012\u0010\b\u0002\u0010\u0017\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0005¢\u0006\u0004\b\u0019\u0010\u001aJ\u001d\u0010\u001c\u001a\u00120\u0000R\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001b¢\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u001e\u0010\u001fR\u0014\u0010\u0002\u001a\u00020\u00018\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010 R\u0014\u0010!\u001a\u00020\u00018\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b!\u0010 R\u0014\u0010\"\u001a\u00020\u00018\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\"\u0010 ¨\u0006$"}, d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;", "", "allocated", "<init>", "(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;I)V", "Lkotlinx/coroutines/debug/internal/HashedWeakRef;", "weakRef", "", "cleanWeakRef", "(Lkotlinx/coroutines/debug/internal/HashedWeakRef;)V", "key", "getImpl", "(Ljava/lang/Object;)Ljava/lang/Object;", "hash", "index", "(I)I", "E", "Lkotlin/Function2;", "factory", "", "keyValueIterator", "(Lkotlin/jvm/functions/Function2;)Ljava/util/Iterator;", "value", "weakKey0", "", "putImpl", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlinx/coroutines/debug/internal/HashedWeakRef;)Ljava/lang/Object;", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "rehash", "()Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;", "removeCleanedAt", "(I)V", "I", "shift", "threshold", "KeyValueIterator", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public final class Core {
        private static final /* synthetic */ AtomicIntegerFieldUpdater load$FU = AtomicIntegerFieldUpdater.newUpdater(Core.class, "load");
        private final int allocated;

        @NotNull
        /* synthetic */ AtomicReferenceArray keys;

        @NotNull
        private volatile /* synthetic */ int load = 0;
        private final int shift;
        private final int threshold;

        @NotNull
        /* synthetic */ AtomicReferenceArray values;

        @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010)\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\b\u0082\u0004\u0018\u0000*\u0004\b\u0002\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001f\u0012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u000b\u001a\u00020\fH\u0002J\t\u0010\r\u001a\u00020\u000eH\u0096\u0002J\u000e\u0010\u000f\u001a\u00028\u0002H\u0096\u0002¢\u0006\u0002\u0010\u0010J\b\u0010\u0011\u001a\u00020\u0012H\u0016R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00028\u0000X\u0082.¢\u0006\u0004\n\u0002\u0010\tR\u0010\u0010\n\u001a\u00028\u0001X\u0082.¢\u0006\u0004\n\u0002\u0010\t¨\u0006\u0013"}, d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core$KeyValueIterator;", "E", "", "factory", "Lkotlin/Function2;", "(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;Lkotlin/jvm/functions/Function2;)V", "index", "", "key", "Ljava/lang/Object;", "value", "findNext", "", "hasNext", "", "next", "()Ljava/lang/Object;", PLVRemoveMicSiteEvent.EVENT_NAME, "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
        public final class KeyValueIterator<E> implements Iterator<E>, KMutableIterator {

            @NotNull
            private final Function2<K, V, E> factory;
            private int index = -1;
            private K key;
            private V value;

            /* JADX WARN: Multi-variable type inference failed */
            public KeyValueIterator(@NotNull Function2<? super K, ? super V, ? extends E> function2) {
                this.factory = function2;
                findNext();
            }

            private final void findNext() {
                K k2;
                while (true) {
                    int i2 = this.index + 1;
                    this.index = i2;
                    if (i2 >= ((Core) Core.this).allocated) {
                        return;
                    }
                    HashedWeakRef hashedWeakRef = (HashedWeakRef) Core.this.keys.get(this.index);
                    if (hashedWeakRef != null && (k2 = (K) hashedWeakRef.get()) != null) {
                        this.key = k2;
                        Object obj = (V) Core.this.values.get(this.index);
                        if (obj instanceof Marked) {
                            obj = (V) ((Marked) obj).ref;
                        }
                        if (obj != null) {
                            this.value = (V) obj;
                            return;
                        }
                    }
                }
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.index < ((Core) Core.this).allocated;
            }

            @Override // java.util.Iterator
            public E next() {
                if (this.index >= ((Core) Core.this).allocated) {
                    throw new NoSuchElementException();
                }
                Function2<K, V, E> function2 = this.factory;
                K k2 = this.key;
                if (k2 == false) {
                    Intrinsics.throwUninitializedPropertyAccessException("key");
                    k2 = (K) Unit.INSTANCE;
                }
                V v2 = this.value;
                if (v2 == false) {
                    Intrinsics.throwUninitializedPropertyAccessException("value");
                    v2 = (V) Unit.INSTANCE;
                }
                E e2 = (E) function2.invoke(k2, v2);
                findNext();
                return e2;
            }

            @Override // java.util.Iterator
            @NotNull
            public Void remove() {
                ConcurrentWeakMapKt.noImpl();
                throw new KotlinNothingValueException();
            }
        }

        public Core(int i2) {
            this.allocated = i2;
            this.shift = Integer.numberOfLeadingZeros(i2) + 1;
            this.threshold = (i2 * 2) / 3;
            this.keys = new AtomicReferenceArray(i2);
            this.values = new AtomicReferenceArray(i2);
        }

        private final int index(int hash) {
            return (hash * (-1640531527)) >>> this.shift;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Object putImpl$default(Core core2, Object obj, Object obj2, HashedWeakRef hashedWeakRef, int i2, Object obj3) {
            if ((i2 & 4) != 0) {
                hashedWeakRef = null;
            }
            return core2.putImpl(obj, obj2, hashedWeakRef);
        }

        private final void removeCleanedAt(int index) {
            Object obj;
            do {
                obj = this.values.get(index);
                if (obj == null || (obj instanceof Marked)) {
                    return;
                }
            } while (!b.a(this.values, index, obj, null));
            ConcurrentWeakMap.this.decrementSize();
        }

        public final void cleanWeakRef(@NotNull HashedWeakRef<?> weakRef) {
            int iIndex = index(weakRef.hash);
            while (true) {
                HashedWeakRef<?> hashedWeakRef = (HashedWeakRef) this.keys.get(iIndex);
                if (hashedWeakRef == null) {
                    return;
                }
                if (hashedWeakRef == weakRef) {
                    removeCleanedAt(iIndex);
                    return;
                } else {
                    if (iIndex == 0) {
                        iIndex = this.allocated;
                    }
                    iIndex--;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Nullable
        public final V getImpl(@NotNull K key) {
            int iIndex = index(key.hashCode());
            while (true) {
                HashedWeakRef hashedWeakRef = (HashedWeakRef) this.keys.get(iIndex);
                if (hashedWeakRef == null) {
                    return null;
                }
                T t2 = hashedWeakRef.get();
                if (Intrinsics.areEqual(key, t2)) {
                    V v2 = (V) this.values.get(iIndex);
                    return v2 instanceof Marked ? (V) ((Marked) v2).ref : v2;
                }
                if (t2 == 0) {
                    removeCleanedAt(iIndex);
                }
                if (iIndex == 0) {
                    iIndex = this.allocated;
                }
                iIndex--;
            }
        }

        @NotNull
        public final <E> Iterator<E> keyValueIterator(@NotNull Function2<? super K, ? super V, ? extends E> factory) {
            return new KeyValueIterator(factory);
        }

        @Nullable
        public final Object putImpl(@NotNull K key, @Nullable V value, @Nullable HashedWeakRef<K> weakKey0) {
            int i2;
            Object obj;
            int iIndex = index(key.hashCode());
            boolean z2 = false;
            while (true) {
                HashedWeakRef hashedWeakRef = (HashedWeakRef) this.keys.get(iIndex);
                if (hashedWeakRef != null) {
                    T t2 = hashedWeakRef.get();
                    if (!Intrinsics.areEqual(key, t2)) {
                        if (t2 == 0) {
                            removeCleanedAt(iIndex);
                        }
                        if (iIndex == 0) {
                            iIndex = this.allocated;
                        }
                        iIndex--;
                    } else if (z2) {
                        load$FU.decrementAndGet(this);
                    }
                } else if (value != null) {
                    if (!z2) {
                        do {
                            i2 = this.load;
                            if (i2 >= this.threshold) {
                                return ConcurrentWeakMapKt.REHASH;
                            }
                        } while (!load$FU.compareAndSet(this, i2, i2 + 1));
                        z2 = true;
                    }
                    if (weakKey0 == null) {
                        weakKey0 = new HashedWeakRef<>(key, ((ConcurrentWeakMap) ConcurrentWeakMap.this).weakRefQueue);
                    }
                    if (b.a(this.keys, iIndex, null, weakKey0)) {
                        break;
                    }
                } else {
                    return null;
                }
            }
            do {
                obj = this.values.get(iIndex);
                if (obj instanceof Marked) {
                    return ConcurrentWeakMapKt.REHASH;
                }
            } while (!b.a(this.values, iIndex, obj, value));
            return obj;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @NotNull
        public final ConcurrentWeakMap<K, V>.Core rehash() {
            int i2;
            Object obj;
            while (true) {
                ConcurrentWeakMap<K, V>.Core core2 = (ConcurrentWeakMap<K, V>.Core) ConcurrentWeakMap.this.new Core(Integer.highestOneBit(RangesKt___RangesKt.coerceAtLeast(ConcurrentWeakMap.this.size(), 4)) * 4);
                int i3 = this.allocated;
                while (i2 < i3) {
                    HashedWeakRef hashedWeakRef = (HashedWeakRef) this.keys.get(i2);
                    Object obj2 = hashedWeakRef != null ? hashedWeakRef.get() : null;
                    if (hashedWeakRef != null && obj2 == null) {
                        removeCleanedAt(i2);
                    }
                    while (true) {
                        obj = this.values.get(i2);
                        if (obj instanceof Marked) {
                            obj = ((Marked) obj).ref;
                            break;
                        }
                        if (b.a(this.values, i2, obj, ConcurrentWeakMapKt.mark(obj))) {
                            break;
                        }
                    }
                    i2 = (obj2 == null || obj == null || core2.putImpl(obj2, obj, hashedWeakRef) != ConcurrentWeakMapKt.REHASH) ? i2 + 1 : 0;
                }
                return core2;
            }
        }
    }

    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010'\n\u0002\b\u000b\b\u0002\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0002\u0012\u0006\u0010\u0005\u001a\u00028\u0003¢\u0006\u0002\u0010\u0006J\u0015\u0010\u000b\u001a\u00028\u00032\u0006\u0010\f\u001a\u00028\u0003H\u0016¢\u0006\u0002\u0010\rR\u0016\u0010\u0004\u001a\u00028\u0002X\u0096\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0005\u001a\u00028\u0003X\u0096\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\n\u0010\b¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Entry;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "key", "value", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getKey", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getValue", "setValue", "newValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Entry<K, V> implements Map.Entry<K, V>, KMutableMap.Entry {
        private final K key;
        private final V value;

        public Entry(K k2, V v2) {
            this.key = k2;
            this.value = v2;
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
        public V setValue(V newValue) {
            ConcurrentWeakMapKt.noImpl();
            throw new KotlinNothingValueException();
        }
    }

    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010)\n\u0000\b\u0082\u0004\u0018\u0000*\u0004\b\u0002\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001f\u0012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0015\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010\rJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00020\u000fH\u0096\u0002R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$KeyValueSet;", "E", "Lkotlin/collections/AbstractMutableSet;", "factory", "Lkotlin/Function2;", "(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;Lkotlin/jvm/functions/Function2;)V", DatabaseManager.SIZE, "", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "iterator", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public final class KeyValueSet<E> extends AbstractMutableSet<E> {

        @NotNull
        private final Function2<K, V, E> factory;

        /* JADX WARN: Multi-variable type inference failed */
        public KeyValueSet(@NotNull Function2<? super K, ? super V, ? extends E> function2) {
            this.factory = function2;
        }

        @Override // kotlin.collections.AbstractMutableSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean add(E element) {
            ConcurrentWeakMapKt.noImpl();
            throw new KotlinNothingValueException();
        }

        @Override // kotlin.collections.AbstractMutableSet
        public int getSize() {
            return ConcurrentWeakMap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        @NotNull
        public Iterator<E> iterator() {
            return ((Core) ConcurrentWeakMap.this.f27649core).keyValueIterator(this.factory);
        }
    }

    public ConcurrentWeakMap() {
        this(false, 1, null);
    }

    public /* synthetic */ ConcurrentWeakMap(boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z2);
    }

    private final void cleanWeakRef(HashedWeakRef<?> w2) {
        ((Core) this.f27649core).cleanWeakRef(w2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void decrementSize() {
        _size$FU.decrementAndGet(this);
    }

    private final synchronized V putSynchronized(K key, V value) {
        V v2;
        Core coreRehash = (Core) this.f27649core;
        while (true) {
            v2 = (V) Core.putImpl$default(coreRehash, key, value, null, 4, null);
            if (v2 == ConcurrentWeakMapKt.REHASH) {
                coreRehash = coreRehash.rehash();
                this.f27649core = coreRehash;
            }
        }
        return v2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        Iterator<K> it = keySet().iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    @Nullable
    public V get(@Nullable Object key) {
        if (key == null) {
            return null;
        }
        return (V) ((Core) this.f27649core).getImpl(key);
    }

    @Override // kotlin.collections.AbstractMutableMap
    @NotNull
    public Set<Map.Entry<K, V>> getEntries() {
        return new KeyValueSet(new Function2<K, V, Map.Entry<K, V>>() { // from class: kotlinx.coroutines.debug.internal.ConcurrentWeakMap$entries$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((ConcurrentWeakMap$entries$1<K, V>) obj, obj2);
            }

            @Override // kotlin.jvm.functions.Function2
            @NotNull
            public final Map.Entry<K, V> invoke(@NotNull K k2, @NotNull V v2) {
                return new ConcurrentWeakMap.Entry(k2, v2);
            }
        });
    }

    @Override // kotlin.collections.AbstractMutableMap
    @NotNull
    public Set<K> getKeys() {
        return new KeyValueSet(new Function2<K, V, K>() { // from class: kotlinx.coroutines.debug.internal.ConcurrentWeakMap$keys$1
            @Override // kotlin.jvm.functions.Function2
            @NotNull
            public final K invoke(@NotNull K k2, @NotNull V v2) {
                return k2;
            }
        });
    }

    @Override // kotlin.collections.AbstractMutableMap
    /* renamed from: getSize, reason: from getter */
    public int get_size() {
        return this._size;
    }

    @Override // kotlin.collections.AbstractMutableMap, java.util.AbstractMap, java.util.Map
    @Nullable
    public V put(@NotNull K key, @NotNull V value) {
        V vPutSynchronized = (V) Core.putImpl$default((Core) this.f27649core, key, value, null, 4, null);
        if (vPutSynchronized == ConcurrentWeakMapKt.REHASH) {
            vPutSynchronized = putSynchronized(key, value);
        }
        if (vPutSynchronized == null) {
            _size$FU.incrementAndGet(this);
        }
        return vPutSynchronized;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    @Nullable
    public V remove(@Nullable Object key) {
        if (key == 0) {
            return null;
        }
        V vPutSynchronized = (V) Core.putImpl$default((Core) this.f27649core, key, null, null, 4, null);
        if (vPutSynchronized == ConcurrentWeakMapKt.REHASH) {
            vPutSynchronized = putSynchronized(key, null);
        }
        if (vPutSynchronized != null) {
            _size$FU.decrementAndGet(this);
        }
        return vPutSynchronized;
    }

    public final void runWeakRefQueueCleaningLoopUntilInterrupted() {
        if (!(this.weakRefQueue != null)) {
            throw new IllegalStateException("Must be created with weakRefQueue = true".toString());
        }
        while (true) {
            try {
                Reference<? extends K> referenceRemove = this.weakRefQueue.remove();
                if (referenceRemove == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.debug.internal.HashedWeakRef<*>");
                }
                cleanWeakRef((HashedWeakRef) referenceRemove);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public ConcurrentWeakMap(boolean z2) {
        this._size = 0;
        this.f27649core = new Core(16);
        this.weakRefQueue = z2 ? new ReferenceQueue<>() : null;
    }
}
