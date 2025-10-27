package com.android.volley;

import android.os.SystemClock;
import android.util.Log;
import androidx.camera.core.CameraInfo;
import cn.hutool.core.text.StrPool;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class VolleyLog {
    public static boolean DEBUG = Log.isLoggable("Volley", 2);
    public static String TAG = "Volley";

    public static class MarkerLog {
        public static final boolean ENABLED = VolleyLog.DEBUG;
        private static final long MIN_DURATION_FOR_LOGGING_MS = 0;
        private final List<Marker> mMarkers = new ArrayList();
        private boolean mFinished = false;

        public static class Marker {
            public final String name;
            public final long thread;
            public final long time;

            public Marker(String str, long j2, long j3) {
                this.name = str;
                this.thread = j2;
                this.time = j3;
            }
        }

        private long getTotalDuration() {
            if (this.mMarkers.size() == 0) {
                return 0L;
            }
            return this.mMarkers.get(r2.size() - 1).time - this.mMarkers.get(0).time;
        }

        public synchronized void add(String str, long j2) {
            if (this.mFinished) {
                throw new IllegalStateException("Marker added to finished log");
            }
            this.mMarkers.add(new Marker(str, j2, SystemClock.elapsedRealtime()));
        }

        public void finalize() throws Throwable {
            if (this.mFinished) {
                return;
            }
            finish("Request on the loose");
            VolleyLog.e("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
        }

        public synchronized void finish(String str) {
            this.mFinished = true;
            long totalDuration = getTotalDuration();
            if (totalDuration <= 0) {
                return;
            }
            long j2 = this.mMarkers.get(0).time;
            VolleyLog.d("(%-4d ms) %s", Long.valueOf(totalDuration), str);
            for (Marker marker : this.mMarkers) {
                long j3 = marker.time;
                VolleyLog.d("(+%-4d) [%2d] %s", Long.valueOf(j3 - j2), Long.valueOf(marker.thread), marker.name);
                j2 = j3;
            }
        }
    }

    private static String buildMessage(String str, Object... objArr) {
        String str2;
        if (objArr != null) {
            str = String.format(Locale.US, str, objArr);
        }
        StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        int i2 = 2;
        while (true) {
            if (i2 >= stackTrace.length) {
                str2 = CameraInfo.IMPLEMENTATION_TYPE_UNKNOWN;
                break;
            }
            if (!stackTrace[i2].getClass().equals(VolleyLog.class)) {
                String className = stackTrace[i2].getClassName();
                String strSubstring = className.substring(className.lastIndexOf(46) + 1);
                str2 = String.valueOf(strSubstring.substring(strSubstring.lastIndexOf(36) + 1)) + StrPool.DOT + stackTrace[i2].getMethodName();
                break;
            }
            i2++;
        }
        return String.format(Locale.US, "[%d] %s: %s", Long.valueOf(Thread.currentThread().getId()), str2, str);
    }

    public static void d(String str, Object... objArr) {
        Log.d(TAG, buildMessage(str, objArr));
    }

    public static void e(String str, Object... objArr) {
        Log.e(TAG, buildMessage(str, objArr));
    }

    public static void setTag(String str) {
        d("Changing log tag to %s", str);
        TAG = str;
        DEBUG = Log.isLoggable(str, 2);
    }

    public static void v(String str, Object... objArr) {
        if (DEBUG) {
            Log.v(TAG, buildMessage(str, objArr));
        }
    }

    public static void wtf(String str, Object... objArr) {
        Log.wtf(TAG, buildMessage(str, objArr));
    }

    public static void e(Throwable th, String str, Object... objArr) {
        Log.e(TAG, buildMessage(str, objArr), th);
    }

    public static void wtf(Throwable th, String str, Object... objArr) {
        Log.wtf(TAG, buildMessage(str, objArr), th);
    }
}
