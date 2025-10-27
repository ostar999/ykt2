package cn.hutool.core.date.format;

/* loaded from: classes.dex */
public final /* synthetic */ class f {
    public static /* synthetic */ long a(long j2, long j3) {
        int iNumberOfLeadingZeros = Long.numberOfLeadingZeros(j2) + Long.numberOfLeadingZeros(~j2) + Long.numberOfLeadingZeros(j3) + Long.numberOfLeadingZeros(~j3);
        if (iNumberOfLeadingZeros > 65) {
            return j2 * j3;
        }
        if (iNumberOfLeadingZeros >= 64) {
            if ((j3 != Long.MIN_VALUE) | (j2 >= 0)) {
                long j4 = j2 * j3;
                if (j2 == 0 || j4 / j2 == j3) {
                    return j4;
                }
            }
        }
        throw new ArithmeticException();
    }
}
