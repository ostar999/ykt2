package com.tencent.open.log;

import android.text.format.Time;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.tencent.rtmp.sharp.jni.QLog;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes6.dex */
public final class g {

    /* renamed from: a, reason: collision with root package name */
    public static final g f20642a = new g();

    public final String a(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 4 ? i2 != 8 ? i2 != 16 ? i2 != 32 ? "-" : ExifInterface.GPS_MEASUREMENT_IN_PROGRESS : "E" : "W" : "I" : QLog.TAG_REPORTLEVEL_DEVELOPER : ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
    }

    public String a(int i2, Thread thread, long j2, String str, String str2, Throwable th) {
        long j3 = j2 % 1000;
        Time time = new Time();
        time.set(j2);
        StringBuilder sb = new StringBuilder();
        sb.append(a(i2));
        sb.append('/');
        sb.append(time.format("%Y-%m-%d %H:%M:%S"));
        sb.append('.');
        if (j3 < 10) {
            sb.append(TarConstants.VERSION_POSIX);
        } else if (j3 < 100) {
            sb.append('0');
        }
        sb.append(j3);
        sb.append(' ');
        sb.append('[');
        if (thread == null) {
            sb.append("N/A");
        } else {
            sb.append(thread.getName());
        }
        sb.append(']');
        sb.append('[');
        sb.append(str);
        sb.append(']');
        sb.append(' ');
        sb.append(str2);
        sb.append('\n');
        if (th != null) {
            sb.append("* Exception : \n");
            sb.append(Log.getStackTraceString(th));
            sb.append('\n');
        }
        return sb.toString();
    }
}
