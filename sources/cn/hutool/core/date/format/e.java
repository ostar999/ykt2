package cn.hutool.core.date.format;

/* loaded from: classes.dex */
public final /* synthetic */ class e {
    public static /* synthetic */ long a(long j2, long j3) {
        long j4 = j2 / j3;
        return (j2 - (j3 * j4) != 0 && (((j2 ^ j3) >> 63) | 1) < 0) ? j4 - 1 : j4;
    }
}
