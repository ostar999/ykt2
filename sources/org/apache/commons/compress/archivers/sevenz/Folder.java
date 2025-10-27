package org.apache.commons.compress.archivers.sevenz;

import java.util.LinkedList;

/* loaded from: classes9.dex */
class Folder {
    BindPair[] bindPairs;
    Coder[] coders;
    long crc;
    boolean hasCrc;
    int numUnpackSubStreams;
    long[] packedStreams;
    long totalInputStreams;
    long totalOutputStreams;
    long[] unpackSizes;

    public int findBindPairForInStream(int i2) {
        int i3 = 0;
        while (true) {
            BindPair[] bindPairArr = this.bindPairs;
            if (i3 >= bindPairArr.length) {
                return -1;
            }
            if (bindPairArr[i3].inIndex == i2) {
                return i3;
            }
            i3++;
        }
    }

    public int findBindPairForOutStream(int i2) {
        int i3 = 0;
        while (true) {
            BindPair[] bindPairArr = this.bindPairs;
            if (i3 >= bindPairArr.length) {
                return -1;
            }
            if (bindPairArr[i3].outIndex == i2) {
                return i3;
            }
            i3++;
        }
    }

    public Iterable<Coder> getOrderedCoders() {
        LinkedList linkedList = new LinkedList();
        int i2 = (int) this.packedStreams[0];
        while (i2 != -1) {
            linkedList.addLast(this.coders[i2]);
            int iFindBindPairForOutStream = findBindPairForOutStream(i2);
            i2 = iFindBindPairForOutStream != -1 ? (int) this.bindPairs[iFindBindPairForOutStream].inIndex : -1;
        }
        return linkedList;
    }

    public long getUnpackSize() {
        long j2 = this.totalOutputStreams;
        if (j2 == 0) {
            return 0L;
        }
        for (int i2 = ((int) j2) - 1; i2 >= 0; i2--) {
            if (findBindPairForOutStream(i2) < 0) {
                return this.unpackSizes[i2];
            }
        }
        return 0L;
    }

    public long getUnpackSizeForCoder(Coder coder) {
        if (this.coders == null) {
            return 0L;
        }
        int i2 = 0;
        while (true) {
            Coder[] coderArr = this.coders;
            if (i2 >= coderArr.length) {
                return 0L;
            }
            if (coderArr[i2] == coder) {
                return this.unpackSizes[i2];
            }
            i2++;
        }
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("Folder with ");
        sb.append(this.coders.length);
        sb.append(" coders, ");
        sb.append(this.totalInputStreams);
        sb.append(" input streams, ");
        sb.append(this.totalOutputStreams);
        sb.append(" output streams, ");
        sb.append(this.bindPairs.length);
        sb.append(" bind pairs, ");
        sb.append(this.packedStreams.length);
        sb.append(" packed streams, ");
        sb.append(this.unpackSizes.length);
        sb.append(" unpack sizes, ");
        if (this.hasCrc) {
            str = "with CRC " + this.crc;
        } else {
            str = "without CRC";
        }
        sb.append(str);
        sb.append(" and ");
        sb.append(this.numUnpackSubStreams);
        sb.append(" unpack streams");
        return sb.toString();
    }
}
