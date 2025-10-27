package com.bumptech.glide.load.engine.bitmap_recycle;

import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.util.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public final class LruArrayPool implements ArrayPool {
    private static final int DEFAULT_SIZE = 4194304;

    @VisibleForTesting
    static final int MAX_OVER_SIZE_MULTIPLE = 8;
    private static final int SINGLE_ARRAY_MAX_SIZE_DIVISOR = 2;
    private final Map<Class<?>, ArrayAdapterInterface<?>> adapters;
    private int currentSize;
    private final GroupedLinkedMap<Key, Object> groupedMap;
    private final KeyPool keyPool;
    private final int maxSize;
    private final Map<Class<?>, NavigableMap<Integer, Integer>> sortedSizes;

    public static final class Key implements Poolable {
        private Class<?> arrayClass;
        private final KeyPool pool;
        int size;

        public Key(KeyPool keyPool) {
            this.pool = keyPool;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Key)) {
                return false;
            }
            Key key = (Key) obj;
            return this.size == key.size && this.arrayClass == key.arrayClass;
        }

        public int hashCode() {
            int i2 = this.size * 31;
            Class<?> cls = this.arrayClass;
            return i2 + (cls != null ? cls.hashCode() : 0);
        }

        public void init(int i2, Class<?> cls) {
            this.size = i2;
            this.arrayClass = cls;
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.Poolable
        public void offer() {
            this.pool.offer(this);
        }

        public String toString() {
            return "Key{size=" + this.size + "array=" + this.arrayClass + '}';
        }
    }

    public static final class KeyPool extends BaseKeyPool<Key> {
        public Key get(int i2, Class<?> cls) {
            Key key = get();
            key.init(i2, cls);
            return key;
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.BaseKeyPool
        public Key create() {
            return new Key(this);
        }
    }

    @VisibleForTesting
    public LruArrayPool() {
        this.groupedMap = new GroupedLinkedMap<>();
        this.keyPool = new KeyPool();
        this.sortedSizes = new HashMap();
        this.adapters = new HashMap();
        this.maxSize = 4194304;
    }

    private void decrementArrayOfSize(int i2, Class<?> cls) {
        NavigableMap<Integer, Integer> sizesForAdapter = getSizesForAdapter(cls);
        Integer num = sizesForAdapter.get(Integer.valueOf(i2));
        if (num != null) {
            if (num.intValue() == 1) {
                sizesForAdapter.remove(Integer.valueOf(i2));
                return;
            } else {
                sizesForAdapter.put(Integer.valueOf(i2), Integer.valueOf(num.intValue() - 1));
                return;
            }
        }
        throw new NullPointerException("Tried to decrement empty size, size: " + i2 + ", this: " + this);
    }

    private void evict() {
        evictToSize(this.maxSize);
    }

    private void evictToSize(int i2) {
        while (this.currentSize > i2) {
            Object objRemoveLast = this.groupedMap.removeLast();
            Preconditions.checkNotNull(objRemoveLast);
            ArrayAdapterInterface adapterFromObject = getAdapterFromObject(objRemoveLast);
            this.currentSize -= adapterFromObject.getArrayLength(objRemoveLast) * adapterFromObject.getElementSizeInBytes();
            decrementArrayOfSize(adapterFromObject.getArrayLength(objRemoveLast), objRemoveLast.getClass());
            if (Log.isLoggable(adapterFromObject.getTag(), 2)) {
                Log.v(adapterFromObject.getTag(), "evicted: " + adapterFromObject.getArrayLength(objRemoveLast));
            }
        }
    }

    private <T> ArrayAdapterInterface<T> getAdapterFromObject(T t2) {
        return getAdapterFromType(t2.getClass());
    }

    private <T> ArrayAdapterInterface<T> getAdapterFromType(Class<T> cls) {
        ArrayAdapterInterface<T> byteArrayAdapter = (ArrayAdapterInterface) this.adapters.get(cls);
        if (byteArrayAdapter == null) {
            if (cls.equals(int[].class)) {
                byteArrayAdapter = new IntegerArrayAdapter();
            } else {
                if (!cls.equals(byte[].class)) {
                    throw new IllegalArgumentException("No array pool found for: " + cls.getSimpleName());
                }
                byteArrayAdapter = new ByteArrayAdapter();
            }
            this.adapters.put(cls, byteArrayAdapter);
        }
        return byteArrayAdapter;
    }

    @Nullable
    private <T> T getArrayForKey(Key key) {
        return (T) this.groupedMap.get(key);
    }

    private <T> T getForKey(Key key, Class<T> cls) {
        ArrayAdapterInterface<T> adapterFromType = getAdapterFromType(cls);
        T t2 = (T) getArrayForKey(key);
        if (t2 != null) {
            this.currentSize -= adapterFromType.getArrayLength(t2) * adapterFromType.getElementSizeInBytes();
            decrementArrayOfSize(adapterFromType.getArrayLength(t2), cls);
        }
        if (t2 != null) {
            return t2;
        }
        if (Log.isLoggable(adapterFromType.getTag(), 2)) {
            Log.v(adapterFromType.getTag(), "Allocated " + key.size + " bytes");
        }
        return adapterFromType.newArray(key.size);
    }

    private NavigableMap<Integer, Integer> getSizesForAdapter(Class<?> cls) {
        NavigableMap<Integer, Integer> navigableMap = this.sortedSizes.get(cls);
        if (navigableMap != null) {
            return navigableMap;
        }
        TreeMap treeMap = new TreeMap();
        this.sortedSizes.put(cls, treeMap);
        return treeMap;
    }

    private boolean isNoMoreThanHalfFull() {
        int i2 = this.currentSize;
        return i2 == 0 || this.maxSize / i2 >= 2;
    }

    private boolean isSmallEnoughForReuse(int i2) {
        return i2 <= this.maxSize / 2;
    }

    private boolean mayFillRequest(int i2, Integer num) {
        return num != null && (isNoMoreThanHalfFull() || num.intValue() <= i2 * 8);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    public synchronized void clearMemory() {
        evictToSize(0);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    public synchronized <T> T get(int i2, Class<T> cls) {
        Integer numCeilingKey;
        numCeilingKey = getSizesForAdapter(cls).ceilingKey(Integer.valueOf(i2));
        return (T) getForKey(mayFillRequest(i2, numCeilingKey) ? this.keyPool.get(numCeilingKey.intValue(), cls) : this.keyPool.get(i2, cls), cls);
    }

    public int getCurrentSize() {
        int iIntValue = 0;
        for (Class<?> cls : this.sortedSizes.keySet()) {
            for (Integer num : this.sortedSizes.get(cls).keySet()) {
                iIntValue += num.intValue() * this.sortedSizes.get(cls).get(num).intValue() * getAdapterFromType(cls).getElementSizeInBytes();
            }
        }
        return iIntValue;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    public synchronized <T> T getExact(int i2, Class<T> cls) {
        return (T) getForKey(this.keyPool.get(i2, cls), cls);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    @Deprecated
    public <T> void put(T t2, Class<T> cls) {
        put(t2);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    public synchronized void trimMemory(int i2) {
        try {
            if (i2 >= 40) {
                clearMemory();
            } else if (i2 >= 20 || i2 == 15) {
                evictToSize(this.maxSize / 2);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    public synchronized <T> void put(T t2) {
        Class<?> cls = t2.getClass();
        ArrayAdapterInterface<T> adapterFromType = getAdapterFromType(cls);
        int arrayLength = adapterFromType.getArrayLength(t2);
        int elementSizeInBytes = adapterFromType.getElementSizeInBytes() * arrayLength;
        if (isSmallEnoughForReuse(elementSizeInBytes)) {
            Key key = this.keyPool.get(arrayLength, cls);
            this.groupedMap.put(key, t2);
            NavigableMap<Integer, Integer> sizesForAdapter = getSizesForAdapter(cls);
            Integer num = sizesForAdapter.get(Integer.valueOf(key.size));
            Integer numValueOf = Integer.valueOf(key.size);
            int iIntValue = 1;
            if (num != null) {
                iIntValue = 1 + num.intValue();
            }
            sizesForAdapter.put(numValueOf, Integer.valueOf(iIntValue));
            this.currentSize += elementSizeInBytes;
            evict();
        }
    }

    public LruArrayPool(int i2) {
        this.groupedMap = new GroupedLinkedMap<>();
        this.keyPool = new KeyPool();
        this.sortedSizes = new HashMap();
        this.adapters = new HashMap();
        this.maxSize = i2;
    }
}
