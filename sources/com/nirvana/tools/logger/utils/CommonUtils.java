package com.nirvana.tools.logger.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/* loaded from: classes4.dex */
public class CommonUtils {
    public static String getLimitIntervalIndex(int i2) {
        if (i2 == 0) {
            return getTodayData();
        }
        return getTodayData() + "-" + i2 + "-" + (Calendar.getInstance().get(11) / i2);
    }

    public static String getTodayData() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
