package com.ykb.ebook.util;

import com.catchpig.mvvm.utils.DateUtil;
import com.tencent.open.SocialConstants;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007\u001a\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00072\b\b\u0002\u0010\f\u001a\u00020\n\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\r"}, d2 = {"dateFormat", "Ljava/text/SimpleDateFormat;", "getDateFormat", "()Ljava/text/SimpleDateFormat;", "calculateDays", "", "startTime", "", "endTime", "formatLongTimeStampToString", "", SocialConstants.PARAM_SOURCE, "pattern", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class DateUtilKt {

    @NotNull
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat() { // from class: com.ykb.ebook.util.DateUtilKt$dateFormat$1
        @Override // java.text.SimpleDateFormat, java.text.DateFormat
        @NotNull
        public StringBuffer format(@Nullable Date date, @NotNull StringBuffer toAppendTo, @Nullable FieldPosition fieldPosition) {
            Intrinsics.checkNotNullParameter(toAppendTo, "toAppendTo");
            toAppendTo.append("最后更新 ");
            Calendar calendar = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            if (date != null) {
                calendar2.setTime(date);
            }
            if (calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6)) {
                toAppendTo.append("今天 ");
            } else {
                toAppendTo.append(new SimpleDateFormat("M-dd ").format(date));
            }
            toAppendTo.append(new SimpleDateFormat(DateUtil.TIME_FORMAT_WITH_HM).format(date));
            return toAppendTo;
        }
    };

    public static final int calculateDays(long j2, long j3) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j2);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(j3);
        int i2 = calendar.get(1);
        int i3 = calendar2.get(1);
        int i4 = calendar.get(6);
        int i5 = calendar2.get(6);
        int i6 = i2 - i3;
        return i6 > 0 ? (i4 - i5) + calendar2.getActualMaximum(6) : i6 < 0 ? (i4 - i5) - calendar.getActualMaximum(6) : i4 - i5;
    }

    @NotNull
    public static final String formatLongTimeStampToString(long j2, @NotNull String pattern) {
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Date date = new Date();
        date.setTime(j2);
        String str = new SimpleDateFormat(pattern, Locale.CHINA).format(date);
        Intrinsics.checkNotNullExpressionValue(str, "SimpleDateFormat(pattern…ocale.CHINA).format(date)");
        return str;
    }

    public static /* synthetic */ String formatLongTimeStampToString$default(long j2, String str, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str = "yyyy-MM-dd HH:mm:ss";
        }
        return formatLongTimeStampToString(j2, str);
    }

    @NotNull
    public static final SimpleDateFormat getDateFormat() {
        return dateFormat;
    }
}
