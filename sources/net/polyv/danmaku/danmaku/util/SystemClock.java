package net.polyv.danmaku.danmaku.util;

/* loaded from: classes9.dex */
public class SystemClock {
    public static final void sleep(long j2) {
        android.os.SystemClock.sleep(j2);
    }

    public static final long uptimeMillis() {
        return android.os.SystemClock.elapsedRealtime();
    }
}
