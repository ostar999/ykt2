package cn.hutool.core.util;

import cn.hutool.core.lang.DefaultSegment;
import cn.hutool.core.lang.Segment;
import v.h1;

/* loaded from: classes.dex */
public class PageUtil {
    private static int firstPageNo;

    public static int getEnd(int i2, int i3) {
        return getEndByStart(getStart(i2, i3), i3);
    }

    private static int getEndByStart(int i2, int i3) {
        if (i3 < 1) {
            i3 = 0;
        }
        return i2 + i3;
    }

    public static int getFirstPageNo() {
        return firstPageNo;
    }

    public static int getStart(int i2, int i3) {
        int i4 = firstPageNo;
        if (i2 < i4) {
            i2 = i4;
        }
        if (i3 < 1) {
            i3 = 0;
        }
        return (i2 - i4) * i3;
    }

    public static int[] rainbow(int i2, int i3, int i4) {
        int i5 = 0;
        int i6 = (i4 & 1) == 0 ? 1 : 0;
        int i7 = i4 >> 1;
        int i8 = i6 != 0 ? i7 + 1 : i7;
        int i9 = i3 < i4 ? i3 : i4;
        int[] iArr = new int[i9];
        if (i3 < i4) {
            while (i5 < i9) {
                int i10 = i5 + 1;
                iArr[i5] = i10;
                i5 = i10;
            }
        } else if (i2 <= i7) {
            while (i5 < i9) {
                int i11 = i5 + 1;
                iArr[i5] = i11;
                i5 = i11;
            }
        } else if (i2 > i3 - i8) {
            while (i5 < i9) {
                iArr[i5] = ((i5 + i3) - i4) + 1;
                i5++;
            }
        } else {
            while (i5 < i9) {
                iArr[i5] = ((i5 + i2) - i7) + i6;
                i5++;
            }
        }
        return iArr;
    }

    public static synchronized void setFirstPageNo(int i2) {
        firstPageNo = i2;
    }

    public static void setOneAsFirstPageNo() {
        setFirstPageNo(1);
    }

    public static Segment<Integer> toSegment(int i2, int i3) {
        int[] iArrTransToStartEnd = transToStartEnd(i2, i3);
        return new DefaultSegment(Integer.valueOf(iArrTransToStartEnd[0]), Integer.valueOf(iArrTransToStartEnd[1]));
    }

    public static int totalPage(int i2, int i3) {
        return totalPage(i2, i3);
    }

    public static int[] transToStartEnd(int i2, int i3) {
        int start = getStart(i2, i3);
        return new int[]{start, getEndByStart(start, i3)};
    }

    public static int totalPage(long j2, int i2) {
        if (i2 == 0) {
            return 0;
        }
        long j3 = i2;
        long j4 = j2 % j3;
        long j5 = j2 / j3;
        if (j4 != 0) {
            j5++;
        }
        return h1.a(j5);
    }

    public static int[] rainbow(int i2, int i3) {
        return rainbow(i2, i3, 10);
    }
}
