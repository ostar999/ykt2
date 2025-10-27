package t;

/* loaded from: classes.dex */
public final /* synthetic */ class y {
    public static /* synthetic */ int a(int i2, int i3) {
        int i4 = i2 / i3;
        return (i2 - (i3 * i4) != 0 && (((i2 ^ i3) >> 31) | 1) < 0) ? i4 - 1 : i4;
    }
}
