package master.flame.danmaku.danmaku.util;

/* loaded from: classes8.dex */
public class SystemClock {
    public static final void sleep(long j2) {
        android.os.SystemClock.sleep(j2);
    }

    public static final long uptimeMillis() {
        return android.os.SystemClock.elapsedRealtime();
    }
}
