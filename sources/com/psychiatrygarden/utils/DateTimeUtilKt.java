package com.psychiatrygarden.utils;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0010\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001¨\u0006\u0007"}, d2 = {"getTimeFromInt", "", CrashHianalyticsData.TIME, "", "timeWithinHalfAnHour", "", "startTime", "xizongapp_ykbRelease"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class DateTimeUtilKt {
    @NotNull
    public static final String getTimeFromInt(long j2) {
        String string;
        String string2;
        String string3;
        if (j2 <= 0) {
            return "0天0小时0分钟0秒";
        }
        long j3 = j2 / 86400;
        long j4 = (j2 / 3600) % 24;
        long j5 = 60;
        long j6 = (j2 / j5) % j5;
        long j7 = j2 % j5;
        if (j4 < 10) {
            StringBuilder sb = new StringBuilder();
            sb.append('0');
            sb.append(j4);
            string = sb.toString();
        } else {
            string = j4 + "";
        }
        if (j6 < 10) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append('0');
            sb2.append(j6);
            string2 = sb2.toString();
        } else {
            string2 = j6 + "";
        }
        if (j7 < 10) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append('0');
            sb3.append(j7);
            string3 = sb3.toString();
        } else {
            string3 = j7 + "";
        }
        return j3 + (char) 22825 + string + "小时" + string2 + "分钟" + string3 + (char) 31186;
    }

    public static final boolean timeWithinHalfAnHour(@Nullable String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return (Long.parseLong(str) * ((long) 1000)) - System.currentTimeMillis() <= ((long) 1800000);
    }
}
