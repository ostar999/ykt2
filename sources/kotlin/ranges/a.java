package kotlin.ranges;

/* loaded from: classes8.dex */
public final /* synthetic */ class a {
    public static /* synthetic */ int a(double d3) {
        long jDoubleToLongBits = Double.doubleToLongBits(d3);
        return (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
    }
}
