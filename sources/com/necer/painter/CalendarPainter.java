package com.necer.painter;

import android.graphics.Canvas;
import android.graphics.RectF;
import java.time.LocalDate;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J.\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000bH&J \u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J.\u0010\r\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000bH&J.\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000bH&¨\u0006\u000f"}, d2 = {"Lcom/necer/painter/CalendarPainter;", "", "onDrawCurrentMonthOrWeek", "", "canvas", "Landroid/graphics/Canvas;", "rectF", "Landroid/graphics/RectF;", "localDate", "Ljava/time/LocalDate;", "checkedDateList", "", "onDrawDisableDate", "onDrawLastOrNextMonth", "onDrawToday", "ncalendar_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public interface CalendarPainter {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class DefaultImpls {
        public static void onDrawDisableDate(@NotNull CalendarPainter calendarPainter, @NotNull Canvas canvas, @NotNull RectF rectF, @NotNull LocalDate localDate) {
            Intrinsics.checkNotNullParameter(canvas, "canvas");
            Intrinsics.checkNotNullParameter(rectF, "rectF");
            Intrinsics.checkNotNullParameter(localDate, "localDate");
        }
    }

    void onDrawCurrentMonthOrWeek(@NotNull Canvas canvas, @NotNull RectF rectF, @NotNull LocalDate localDate, @NotNull List<LocalDate> checkedDateList);

    void onDrawDisableDate(@NotNull Canvas canvas, @NotNull RectF rectF, @NotNull LocalDate localDate);

    void onDrawLastOrNextMonth(@NotNull Canvas canvas, @NotNull RectF rectF, @NotNull LocalDate localDate, @NotNull List<LocalDate> checkedDateList);

    void onDrawToday(@NotNull Canvas canvas, @NotNull RectF rectF, @NotNull LocalDate localDate, @NotNull List<LocalDate> checkedDateList);
}
