package com.hyphenate.chat;

import android.annotation.SuppressLint;
import cn.hutool.core.text.StrPool;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes4.dex */
class EMCollector {
    static boolean collectorEnabled = false;

    public static String getTagPrefix(String str) {
        return StrPool.BRACKET_START + str + StrPool.BRACKET_END;
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String timeToString(long j2) {
        return new SimpleDateFormat("mm:ss:SSS").format(new Date(j2));
    }

    public void enableCollector(boolean z2) {
        collectorEnabled = z2;
    }
}
