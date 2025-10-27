package com.necer.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.necer.utils.NAttrs;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0015R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\n¨\u0006\u000f"}, d2 = {"Lcom/necer/calendar/NWeekBar;", "Landroidx/appcompat/widget/AppCompatTextView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "days", "", "", "[Ljava/lang/String;", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "ncalendar_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class NWeekBar extends AppCompatTextView {

    @NotNull
    private String[] days;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NWeekBar(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.days = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    }

    @Override // android.widget.TextView, android.view.View
    @SuppressLint({"DrawAllocation"})
    public void onDraw(@NotNull Canvas canvas) {
        String str;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        NAttrs nAttrs = NAttrs.INSTANCE;
        canvas.drawColor(nAttrs.getWeekBarBackgroundColor());
        getPaint().setTextAlign(Paint.Align.CENTER);
        getPaint().setColor(nAttrs.getWeekBarTextColor());
        getPaint().setTextSize(nAttrs.getWeekBarTextSize());
        int length = this.days.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 + 1;
            Rect rect = new Rect((getWidth() * i2) / this.days.length, 0, (getWidth() * i3) / this.days.length, getHeight());
            Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
            float f2 = 2;
            int iCenterY = (int) ((rect.centerY() - (fontMetrics.top / f2)) - (fontMetrics.bottom / f2));
            if (NAttrs.INSTANCE.getFirstDayOfWeek() == 301) {
                String[] strArr = this.days;
                str = strArr[i3 > strArr.length + (-1) ? 0 : i3];
            } else {
                str = this.days[i2];
            }
            canvas.drawText(str, rect.centerX(), iCenterY, getPaint());
            i2 = i3;
        }
    }
}
