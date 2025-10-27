package com.tencent.tbs.logger;

import android.os.Process;
import cn.hutool.core.date.DatePattern;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static final DateFormat f21640a = new SimpleDateFormat(DatePattern.NORM_DATETIME_MS_PATTERN, Locale.CHINA);

    public static String a(b bVar) {
        if (bVar == null) {
            return "";
        }
        return f21640a.format(new Date(bVar.f21641a)) + " " + Process.myPid() + "-" + Process.myTid() + " " + c.a(bVar.f21642b) + "/" + bVar.f21643c + ": " + bVar.f21644d + "\n";
    }
}
