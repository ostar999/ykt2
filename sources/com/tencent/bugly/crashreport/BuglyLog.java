package com.tencent.bugly.crashreport;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.tencent.bugly.proguard.ao;
import com.tencent.bugly.proguard.p;
import com.tencent.rtmp.sharp.jni.QLog;

/* loaded from: classes6.dex */
public class BuglyLog {
    public static void d(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (p.f17851c) {
            Log.d(str, str2);
        }
        ao.a(QLog.TAG_REPORTLEVEL_DEVELOPER, str, str2);
    }

    public static void e(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (p.f17851c) {
            Log.e(str, str2);
        }
        ao.a("E", str, str2);
    }

    public static void i(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (p.f17851c) {
            Log.i(str, str2);
        }
        ao.a("I", str, str2);
    }

    public static void setCache(int i2) {
        ao.a(i2);
    }

    public static void v(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (p.f17851c) {
            Log.v(str, str2);
        }
        ao.a(ExifInterface.GPS_MEASUREMENT_INTERRUPTED, str, str2);
    }

    public static void w(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (p.f17851c) {
            Log.w(str, str2);
        }
        ao.a("W", str, str2);
    }

    public static void e(String str, String str2, Throwable th) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (p.f17851c) {
            Log.e(str, str2, th);
        }
        ao.a("E", str, th);
    }
}
