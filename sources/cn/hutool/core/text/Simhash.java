package cn.hutool.core.text;

import cn.hutool.core.lang.hash.MurmurHash;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

/* loaded from: classes.dex */
public class Simhash {
    private final int bitNum;
    private final int fracBitNum;
    private final int fracCount;
    private final int hammingThresh;
    private final StampedLock lock;
    private final List<Map<String, List<Long>>> storage;

    public Simhash() {
        this(4, 3);
    }

    private int hamming(Long l2, Long l3) {
        int i2 = 0;
        for (int i3 = 0; i3 < 64; i3++) {
            if (((l2.longValue() >> i3) & 1) != (1 & (l3.longValue() >> i3))) {
                i2++;
            }
        }
        return i2;
    }

    private List<String> splitSimhash(Long l2) {
        int i2 = this.fracBitNum;
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        int i3 = 0;
        while (i3 < 64) {
            sb.append((l2.longValue() >> i3) & 1);
            i3++;
            if (i3 % i2 == 0) {
                arrayList.add(sb.toString());
                sb.setLength(0);
            }
        }
        return arrayList;
    }

    public boolean equals(Collection<? extends CharSequence> collection) {
        long jHash = hash(collection);
        List<String> listSplitSimhash = splitSimhash(Long.valueOf(jHash));
        int i2 = this.hammingThresh;
        long lock = this.lock.readLock();
        for (int i3 = 0; i3 < this.fracCount; i3++) {
            try {
                String str = listSplitSimhash.get(i3);
                Map<String, List<Long>> map = this.storage.get(i3);
                if (map.containsKey(str)) {
                    Iterator<Long> it = map.get(str).iterator();
                    while (it.hasNext()) {
                        if (hamming(Long.valueOf(jHash), it.next()) < i2) {
                            this.lock.unlockRead(lock);
                            return true;
                        }
                    }
                }
            } finally {
                this.lock.unlockRead(lock);
            }
        }
        return false;
    }

    public long hash(Collection<? extends CharSequence> collection) {
        int[] iArr = new int[64];
        Iterator<? extends CharSequence> it = collection.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            long jHash64 = MurmurHash.hash64(it.next());
            for (int i2 = 0; i2 < 64; i2++) {
                if (((jHash64 >> i2) & 1) == 1) {
                    iArr[i2] = iArr[i2] + 1;
                } else {
                    iArr[i2] = iArr[i2] - 1;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < 64; i3++) {
            sb.append(iArr[i3] > 0 ? 1 : 0);
        }
        return new BigInteger(sb.toString(), 2).longValue();
    }

    public void store(Long l2) {
        int i2 = this.fracCount;
        List<Map<String, List<Long>>> list = this.storage;
        List<String> listSplitSimhash = splitSimhash(l2);
        long jWriteLock = this.lock.writeLock();
        for (int i3 = 0; i3 < i2; i3++) {
            try {
                String str = listSplitSimhash.get(i3);
                Map<String, List<Long>> map = list.get(i3);
                if (map.containsKey(str)) {
                    map.get(str).add(l2);
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(l2);
                    map.put(str, arrayList);
                }
            } finally {
                this.lock.unlockWrite(jWriteLock);
            }
        }
    }

    public Simhash(int i2, int i3) {
        this.bitNum = 64;
        this.lock = new StampedLock();
        this.fracCount = i2;
        this.fracBitNum = 64 / i2;
        this.hammingThresh = i3;
        this.storage = new ArrayList(i2);
        for (int i4 = 0; i4 < i2; i4++) {
            this.storage.add(new HashMap());
        }
    }
}
