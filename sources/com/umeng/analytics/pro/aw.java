package com.umeng.analytics.pro;

import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/* loaded from: classes6.dex */
public final class aw {

    /* renamed from: a, reason: collision with root package name */
    private static final Comparator f22544a = new a();

    public static class a implements Comparator {
        private a() {
        }

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            if (obj == null && obj2 == null) {
                return 0;
            }
            if (obj == null) {
                return -1;
            }
            if (obj2 == null) {
                return 1;
            }
            return obj instanceof List ? aw.a((List) obj, (List) obj2) : obj instanceof Set ? aw.a((Set) obj, (Set) obj2) : obj instanceof Map ? aw.a((Map) obj, (Map) obj2) : obj instanceof byte[] ? aw.a((byte[]) obj, (byte[]) obj2) : aw.a((Comparable) obj, (Comparable) obj2);
        }
    }

    private aw() {
    }

    public static int a(byte b3, byte b4) {
        if (b3 < b4) {
            return -1;
        }
        return b4 < b3 ? 1 : 0;
    }

    public static int a(double d3, double d4) {
        if (d3 < d4) {
            return -1;
        }
        return d4 < d3 ? 1 : 0;
    }

    public static int a(int i2, int i3) {
        if (i2 < i3) {
            return -1;
        }
        return i3 < i2 ? 1 : 0;
    }

    public static int a(long j2, long j3) {
        if (j2 < j3) {
            return -1;
        }
        return j3 < j2 ? 1 : 0;
    }

    public static int a(Object obj, Object obj2) {
        if (obj instanceof Comparable) {
            return a((Comparable) obj, (Comparable) obj2);
        }
        if (obj instanceof List) {
            return a((List) obj, (List) obj2);
        }
        if (obj instanceof Set) {
            return a((Set) obj, (Set) obj2);
        }
        if (obj instanceof Map) {
            return a((Map) obj, (Map) obj2);
        }
        if (obj instanceof byte[]) {
            return a((byte[]) obj, (byte[]) obj2);
        }
        throw new IllegalArgumentException("Cannot compare objects of type " + obj.getClass());
    }

    public static int a(short s2, short s3) {
        if (s2 < s3) {
            return -1;
        }
        return s3 < s2 ? 1 : 0;
    }

    public static boolean b(ByteBuffer byteBuffer) {
        return byteBuffer.hasArray() && byteBuffer.position() == 0 && byteBuffer.arrayOffset() == 0 && byteBuffer.remaining() == byteBuffer.capacity();
    }

    public static ByteBuffer c(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return null;
        }
        return b(byteBuffer) ? byteBuffer : ByteBuffer.wrap(a(byteBuffer));
    }

    public static ByteBuffer d(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return null;
        }
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[byteBuffer.remaining()]);
        if (byteBuffer.hasArray()) {
            System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBufferWrap.array(), 0, byteBuffer.remaining());
        } else {
            byteBuffer.slice().get(byteBufferWrap.array());
        }
        return byteBufferWrap;
    }

    public static int a(boolean z2, boolean z3) {
        return Boolean.valueOf(z2).compareTo(Boolean.valueOf(z3));
    }

    public static int a(String str, String str2) {
        return str.compareTo(str2);
    }

    public static int a(byte[] bArr, byte[] bArr2) {
        int iA = a(bArr.length, bArr2.length);
        if (iA != 0) {
            return iA;
        }
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int iA2 = a(bArr[i2], bArr2[i2]);
            if (iA2 != 0) {
                return iA2;
            }
        }
        return 0;
    }

    public static int a(Comparable comparable, Comparable comparable2) {
        return comparable.compareTo(comparable2);
    }

    public static int a(List list, List list2) {
        int iA = a(list.size(), list2.size());
        if (iA != 0) {
            return iA;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            int iCompare = f22544a.compare(list.get(i2), list2.get(i2));
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return 0;
    }

    public static int a(Set set, Set set2) {
        int iA = a(set.size(), set2.size());
        if (iA != 0) {
            return iA;
        }
        Comparator comparator = f22544a;
        TreeSet treeSet = new TreeSet(comparator);
        treeSet.addAll(set);
        TreeSet treeSet2 = new TreeSet(comparator);
        treeSet2.addAll(set2);
        Iterator it = treeSet.iterator();
        Iterator it2 = treeSet2.iterator();
        while (it.hasNext() && it2.hasNext()) {
            int iCompare = f22544a.compare(it.next(), it2.next());
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return 0;
    }

    public static int a(Map map, Map map2) {
        int iA = a(map.size(), map2.size());
        if (iA != 0) {
            return iA;
        }
        Comparator comparator = f22544a;
        TreeMap treeMap = new TreeMap(comparator);
        treeMap.putAll(map);
        Iterator it = treeMap.entrySet().iterator();
        TreeMap treeMap2 = new TreeMap(comparator);
        treeMap2.putAll(map2);
        Iterator it2 = treeMap2.entrySet().iterator();
        while (it.hasNext() && it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Map.Entry entry2 = (Map.Entry) it2.next();
            Comparator comparator2 = f22544a;
            int iCompare = comparator2.compare(entry.getKey(), entry2.getKey());
            if (iCompare != 0) {
                return iCompare;
            }
            int iCompare2 = comparator2.compare(entry.getValue(), entry2.getValue());
            if (iCompare2 != 0) {
                return iCompare2;
            }
        }
        return 0;
    }

    public static void a(ByteBuffer byteBuffer, StringBuilder sb) {
        byte[] bArrArray = byteBuffer.array();
        int iArrayOffset = byteBuffer.arrayOffset();
        int iPosition = byteBuffer.position() + iArrayOffset;
        int iLimit = iArrayOffset + byteBuffer.limit();
        int i2 = iLimit - iPosition > 128 ? iPosition + 128 : iLimit;
        for (int i3 = iPosition; i3 < i2; i3++) {
            if (i3 > iPosition) {
                sb.append(" ");
            }
            sb.append(a(bArrArray[i3]));
        }
        if (iLimit != i2) {
            sb.append("...");
        }
    }

    public static String a(byte b3) {
        return Integer.toHexString((b3 | 256) & 511).toUpperCase().substring(1);
    }

    public static byte[] a(ByteBuffer byteBuffer) {
        if (b(byteBuffer)) {
            return byteBuffer.array();
        }
        byte[] bArr = new byte[byteBuffer.remaining()];
        a(byteBuffer, bArr, 0);
        return bArr;
    }

    public static int a(ByteBuffer byteBuffer, byte[] bArr, int i2) {
        int iRemaining = byteBuffer.remaining();
        System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), bArr, i2, iRemaining);
        return iRemaining;
    }

    public static byte[] a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }
}
