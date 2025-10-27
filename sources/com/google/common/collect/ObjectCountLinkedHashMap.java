package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import java.util.Arrays;
import net.lingala.zip4j.util.InternalZipConstants;

@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes4.dex */
class ObjectCountLinkedHashMap<K> extends ObjectCountHashMap<K> {
    private static final int ENDPOINT = -2;
    private transient int firstEntry;
    private transient int lastEntry;

    @VisibleForTesting
    transient long[] links;

    public ObjectCountLinkedHashMap() {
        this(3);
    }

    public static <K> ObjectCountLinkedHashMap<K> create() {
        return new ObjectCountLinkedHashMap<>();
    }

    public static <K> ObjectCountLinkedHashMap<K> createWithExpectedSize(int i2) {
        return new ObjectCountLinkedHashMap<>(i2);
    }

    private int getPredecessor(int i2) {
        return (int) (this.links[i2] >>> 32);
    }

    private int getSuccessor(int i2) {
        return (int) this.links[i2];
    }

    private void setPredecessor(int i2, int i3) {
        long[] jArr = this.links;
        jArr[i2] = (jArr[i2] & InternalZipConstants.ZIP_64_LIMIT) | (i3 << 32);
    }

    private void setSucceeds(int i2, int i3) {
        if (i2 == -2) {
            this.firstEntry = i3;
        } else {
            setSuccessor(i2, i3);
        }
        if (i3 == -2) {
            this.lastEntry = i2;
        } else {
            setPredecessor(i3, i2);
        }
    }

    private void setSuccessor(int i2, int i3) {
        long[] jArr = this.links;
        jArr[i2] = (jArr[i2] & (-4294967296L)) | (i3 & InternalZipConstants.ZIP_64_LIMIT);
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public void clear() {
        super.clear();
        this.firstEntry = -2;
        this.lastEntry = -2;
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public int firstIndex() {
        int i2 = this.firstEntry;
        if (i2 == -2) {
            return -1;
        }
        return i2;
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public void init(int i2, float f2) {
        super.init(i2, f2);
        this.firstEntry = -2;
        this.lastEntry = -2;
        long[] jArr = new long[i2];
        this.links = jArr;
        Arrays.fill(jArr, -1L);
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public void insertEntry(int i2, K k2, int i3, int i4) {
        super.insertEntry(i2, k2, i3, i4);
        setSucceeds(this.lastEntry, i2);
        setSucceeds(i2, -2);
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public void moveLastEntry(int i2) {
        int size = size() - 1;
        setSucceeds(getPredecessor(i2), getSuccessor(i2));
        if (i2 < size) {
            setSucceeds(getPredecessor(size), i2);
            setSucceeds(i2, getSuccessor(size));
        }
        super.moveLastEntry(i2);
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public int nextIndex(int i2) {
        int successor = getSuccessor(i2);
        if (successor == -2) {
            return -1;
        }
        return successor;
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public int nextIndexAfterRemove(int i2, int i3) {
        return i2 == size() ? i3 : i2;
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public void resizeEntries(int i2) {
        super.resizeEntries(i2);
        long[] jArr = this.links;
        int length = jArr.length;
        long[] jArrCopyOf = Arrays.copyOf(jArr, i2);
        this.links = jArrCopyOf;
        Arrays.fill(jArrCopyOf, length, i2, -1L);
    }

    public ObjectCountLinkedHashMap(int i2) {
        this(i2, 1.0f);
    }

    public ObjectCountLinkedHashMap(int i2, float f2) {
        super(i2, f2);
    }

    public ObjectCountLinkedHashMap(ObjectCountHashMap<K> objectCountHashMap) {
        init(objectCountHashMap.size(), 1.0f);
        int iFirstIndex = objectCountHashMap.firstIndex();
        while (iFirstIndex != -1) {
            put(objectCountHashMap.getKey(iFirstIndex), objectCountHashMap.getValue(iFirstIndex));
            iFirstIndex = objectCountHashMap.nextIndex(iFirstIndex);
        }
    }
}
