package com.tencent.smtt.sdk.a;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes6.dex */
public class a {
    public static String a(long j2) {
        return String.format(Locale.getDefault(), "%d(%s)", Long.valueOf(j2), new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(new Date(j2)));
    }
}
