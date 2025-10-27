package com.google.firebase.appindexing.internal;

import cn.hutool.core.date.DatePattern;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.annotation.concurrent.GuardedBy;

/* loaded from: classes4.dex */
public final class zzad {

    @GuardedBy("itself")
    private static final DateFormat zzfz = new SimpleDateFormat(DatePattern.UTC_WITH_ZONE_OFFSET_PATTERN, Locale.US);

    public static String zza(Calendar calendar) {
        String str;
        DateFormat dateFormat = zzfz;
        synchronized (dateFormat) {
            dateFormat.setTimeZone(calendar.getTimeZone());
            str = dateFormat.format(calendar.getTime());
        }
        return str;
    }
}
