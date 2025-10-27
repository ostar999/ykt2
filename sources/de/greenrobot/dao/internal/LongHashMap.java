package de.greenrobot.dao.internal;

import de.greenrobot.dao.DaoLog;
import java.util.Arrays;

/* loaded from: classes8.dex */
public final class LongHashMap<T> {
    private int capacity;
    private int size;
    private Entry<T>[] table;
    private int threshold;

    public static final class Entry<T> {
        final long key;
        Entry<T> next;
        T value;

        public Entry(long j2, T t2, Entry<T> entry) {
            this.key = j2;
            this.value = t2;
            this.next = entry;
        }
    }

    public LongHashMap() {
        this(16);
    }

    public void clear() {
        this.size = 0;
        Arrays.fill(this.table, (Object) null);
    }

    public boolean containsKey(long j2) {
        for (Entry<T> entry = this.table[((((int) (j2 >>> 32)) ^ ((int) j2)) & Integer.MAX_VALUE) % this.capacity]; entry != null; entry = entry.next) {
            if (entry.key == j2) {
                return true;
            }
        }
        return false;
    }

    public T get(long j2) {
        for (Entry<T> entry = this.table[((((int) (j2 >>> 32)) ^ ((int) j2)) & Integer.MAX_VALUE) % this.capacity]; entry != null; entry = entry.next) {
            if (entry.key == j2) {
                return entry.value;
            }
        }
        return null;
    }

    public void logStats() {
        int i2 = 0;
        for (Entry<T> entry : this.table) {
            while (entry != null) {
                entry = entry.next;
                if (entry != null) {
                    i2++;
                }
            }
        }
        DaoLog.d("load: " + (this.size / this.capacity) + ", size: " + this.size + ", capa: " + this.capacity + ", collisions: " + i2 + ", collision ratio: " + (i2 / this.size));
    }

    public T put(long j2, T t2) {
        int i2 = ((((int) (j2 >>> 32)) ^ ((int) j2)) & Integer.MAX_VALUE) % this.capacity;
        Entry<T> entry = this.table[i2];
        for (Entry<T> entry2 = entry; entry2 != null; entry2 = entry2.next) {
            if (entry2.key == j2) {
                T t3 = entry2.value;
                entry2.value = t2;
                return t3;
            }
        }
        this.table[i2] = new Entry<>(j2, t2, entry);
        int i3 = this.size + 1;
        this.size = i3;
        if (i3 <= this.threshold) {
            return null;
        }
        setCapacity(this.capacity * 2);
        return null;
    }

    public T remove(long j2) {
        int i2 = ((((int) (j2 >>> 32)) ^ ((int) j2)) & Integer.MAX_VALUE) % this.capacity;
        Entry<T> entry = this.table[i2];
        Entry<T> entry2 = null;
        while (entry != null) {
            Entry<T> entry3 = entry.next;
            if (entry.key == j2) {
                if (entry2 == null) {
                    this.table[i2] = entry3;
                } else {
                    entry2.next = entry3;
                }
                this.size--;
                return entry.value;
            }
            entry2 = entry;
            entry = entry3;
        }
        return null;
    }

    public void reserveRoom(int i2) {
        setCapacity((i2 * 5) / 3);
    }

    public void setCapacity(int i2) {
        Entry<T>[] entryArr = new Entry[i2];
        int length = this.table.length;
        for (int i3 = 0; i3 < length; i3++) {
            Entry<T> entry = this.table[i3];
            while (entry != null) {
                long j2 = entry.key;
                int i4 = ((((int) j2) ^ ((int) (j2 >>> 32))) & Integer.MAX_VALUE) % i2;
                Entry<T> entry2 = entry.next;
                entry.next = entryArr[i4];
                entryArr[i4] = entry;
                entry = entry2;
            }
        }
        this.table = entryArr;
        this.capacity = i2;
        this.threshold = (i2 * 4) / 3;
    }

    public int size() {
        return this.size;
    }

    public LongHashMap(int i2) {
        this.capacity = i2;
        this.threshold = (i2 * 4) / 3;
        this.table = new Entry[i2];
    }
}
