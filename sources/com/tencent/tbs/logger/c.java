package com.tencent.tbs.logger;

import androidx.exifinterface.media.ExifInterface;
import com.tencent.rtmp.sharp.jni.QLog;

/* loaded from: classes6.dex */
public enum c {
    VERBOSE(2),
    DEBUG(3),
    INFO(4),
    WARN(5),
    ERROR(6),
    ALL(Integer.MIN_VALUE),
    NONE(Integer.MAX_VALUE);


    /* renamed from: a, reason: collision with root package name */
    public int f21653a;

    c(int i2) {
        this.f21653a = i2;
    }

    public static String a(c cVar) {
        StringBuilder sb;
        int i2;
        int i3;
        int iOrdinal = cVar.ordinal();
        if (iOrdinal == 0) {
            return ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
        }
        if (iOrdinal == 1) {
            return QLog.TAG_REPORTLEVEL_DEVELOPER;
        }
        if (iOrdinal == 2) {
            return "I";
        }
        if (iOrdinal == 3) {
            return "W";
        }
        if (iOrdinal == 4) {
            return "E";
        }
        int i4 = cVar.f21653a;
        c cVar2 = VERBOSE;
        if (i4 < cVar2.f21653a) {
            sb = new StringBuilder();
            sb.append("V-");
            i2 = cVar2.f21653a;
            i3 = cVar.f21653a;
        } else {
            sb = new StringBuilder();
            sb.append("E+");
            i2 = cVar.f21653a;
            i3 = ERROR.f21653a;
        }
        sb.append(i2 - i3);
        return sb.toString();
    }
}
