package com.necer.painter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.necer.R;
import com.necer.calendar.ICalendar;
import com.necer.utils.NAttrs;
import com.necer.utils.hutool.ChineseDate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\u0010\u0007\n\u0002\b\u0010\n\u0002\u0010$\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010&\u001a\u00020'2\u000e\u0010(\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180)J.\u0010*\u001a\u00020'2\b\u0010+\u001a\u0004\u0018\u00010,2\b\u0010-\u001a\u0004\u0018\u00010\f2\b\u0010.\u001a\u0004\u0018\u00010/2\u0006\u00100\u001a\u00020\u0016H\u0002J4\u00101\u001a\u00020'2\b\u0010+\u001a\u0004\u0018\u00010,2\b\u0010.\u001a\u0004\u0018\u00010/2\u0006\u00100\u001a\u00020\u00162\u0006\u00102\u001a\u00020\u00122\u0006\u00103\u001a\u000204H\u0002JL\u00105\u001a\u00020'2\u0006\u0010+\u001a\u00020,2\u0006\u0010.\u001a\u00020/2\u0006\u00102\u001a\u00020\u00122\b\u00106\u001a\u0004\u0018\u00010\f2\b\u00107\u001a\u0004\u0018\u00010\f2\u0006\u00108\u001a\u00020\u00162\u0006\u00109\u001a\u00020\u00162\u0006\u00100\u001a\u00020\u0016H\u0002J4\u0010:\u001a\u00020'2\b\u0010+\u001a\u0004\u0018\u00010,2\b\u0010.\u001a\u0004\u0018\u00010/2\u0006\u00102\u001a\u00020\u00122\u0006\u0010;\u001a\u00020\u00162\u0006\u00100\u001a\u00020\u0016H\u0002J8\u0010<\u001a\u00020'2\b\u0010+\u001a\u0004\u0018\u00010,2\b\u0010.\u001a\u0004\u0018\u00010/2\b\u0010=\u001a\u0004\u0018\u00010\u00122\b\u0010-\u001a\u0004\u0018\u00010\f2\u0006\u00100\u001a\u00020\u0016H\u0002J8\u0010>\u001a\u00020'2\b\u0010+\u001a\u0004\u0018\u00010,2\b\u0010.\u001a\u0004\u0018\u00010/2\b\u0010=\u001a\u0004\u0018\u00010\u00122\b\u0010-\u001a\u0004\u0018\u00010\f2\u0006\u00100\u001a\u00020\u0016H\u0002JF\u0010?\u001a\u00020'2\b\u0010+\u001a\u0004\u0018\u00010,2\b\u0010.\u001a\u0004\u0018\u00010/2\b\u0010=\u001a\u0004\u0018\u00010\u00122\b\u0010-\u001a\u0004\u0018\u00010\f2\u0006\u00100\u001a\u00020\u00162\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00120)H\u0002JF\u0010A\u001a\u00020'2\b\u0010+\u001a\u0004\u0018\u00010,2\b\u0010.\u001a\u0004\u0018\u00010/2\b\u0010=\u001a\u0004\u0018\u00010\u00122\u0006\u0010;\u001a\u00020\u00162\u0006\u00100\u001a\u00020\u00162\u000e\b\u0002\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00120)H\u0002J6\u0010B\u001a\u00020'2\b\u0010+\u001a\u0004\u0018\u00010,2\b\u0010.\u001a\u0004\u0018\u00010/2\b\u0010=\u001a\u0004\u0018\u00010\u00122\u0006\u0010;\u001a\u00020\u00162\u0006\u00100\u001a\u00020\u0016H\u0002J.\u0010C\u001a\u00020'2\b\u0010+\u001a\u0004\u0018\u00010,2\b\u0010.\u001a\u0004\u0018\u00010/2\u0006\u00100\u001a\u00020\u00162\b\u00102\u001a\u0004\u0018\u00010\u0012H\u0002J,\u0010D\u001a\u00020'2\b\u0010+\u001a\u0004\u0018\u00010,2\b\u0010.\u001a\u0004\u0018\u00010/2\u0006\u0010;\u001a\u00020\u00162\u0006\u00100\u001a\u00020\u0016H\u0002J\f\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00120)J\"\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020\u00162\u0006\u0010I\u001a\u00020\u00162\b\u0010-\u001a\u0004\u0018\u00010\fH\u0002J\u0018\u0010J\u001a\u00020K2\u0006\u0010H\u001a\u00020L2\u0006\u0010I\u001a\u00020LH\u0002J\u0010\u0010M\u001a\u00020L2\u0006\u0010I\u001a\u00020LH\u0002J.\u0010N\u001a\u00020'2\u0006\u0010+\u001a\u00020,2\u0006\u0010.\u001a\u00020/2\u0006\u00102\u001a\u00020\u00122\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00120)H\u0016J \u0010O\u001a\u00020'2\u0006\u0010+\u001a\u00020,2\u0006\u0010.\u001a\u00020/2\u0006\u00102\u001a\u00020\u0012H\u0016J.\u0010P\u001a\u00020'2\u0006\u0010+\u001a\u00020,2\u0006\u0010.\u001a\u00020/2\u0006\u00102\u001a\u00020\u00122\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00120)H\u0016J.\u0010Q\u001a\u00020'2\u0006\u0010+\u001a\u00020,2\u0006\u0010.\u001a\u00020/2\u0006\u00102\u001a\u00020\u00122\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00120)H\u0016JF\u0010R\u001a\u00020'2\u000e\u0010S\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180)2\u000e\u0010T\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180)2\u000e\u0010U\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180)2\u000e\u0010V\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180)J&\u0010W\u001a\u00020'2\u000e\u0010X\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180)2\u000e\u0010Y\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180)J\u0016\u0010Z\u001a\u00020'2\u000e\u0010(\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180)J\u001e\u0010[\u001a\u00020'2\u0016\u0010\\\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00160]J\u001e\u0010^\u001a\u00020'2\u0016\u0010_\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00180]J\u001a\u0010`\u001a\u00020'2\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00180]R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0019\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0016X\u0082D¢\u0006\u0002\n\u0000¨\u0006b"}, d2 = {"Lcom/necer/painter/InnerPainter;", "Lcom/necer/painter/CalendarPainter;", "mContext", "Landroid/content/Context;", "iCalendar", "Lcom/necer/calendar/ICalendar;", "(Landroid/content/Context;Lcom/necer/calendar/ICalendar;)V", "getICalendar", "()Lcom/necer/calendar/ICalendar;", "mDateFormatter", "Ljava/time/format/DateTimeFormatter;", "mDefaultCheckedBackground", "Landroid/graphics/drawable/Drawable;", "mDefaultCheckedPoint", "mDefaultUnCheckedBackground", "mDefaultUnCheckedPoint", "mHolidayList", "", "Ljava/time/LocalDate;", "mPointList", "mReplaceLunarColorMap", "", "", "mReplaceLunarStrMap", "", "mStretchStrMap", "mTaskFailList", "mTaskFinishList", "mTaskList", "mTaskTodoList", "mTextPaint", "Landroid/graphics/Paint;", "mTodayCheckedBackground", "mTodayCheckedPoint", "mTodayUnCheckedBackground", "mTodayUnCheckedPoint", "mWorkdayList", "noAlphaColor", "addPointList", "", "list", "", "drawCheckedBackground", "canvas", "Landroid/graphics/Canvas;", "drawable", "rectF", "Landroid/graphics/RectF;", "alphaColor", "drawCheckedBackgroundDailyStyle", "localDate", "checked", "", "drawHolidayWorkday", "holidayDrawable", "workdayDrawable", "holidayTextColor", "workdayTextColor", "drawLunar", "color", "drawPoint", "date", "drawPointDailyStyle", "drawPointNew", "checkedDateList", "drawSolar", "drawSolarDailyStyle", "drawStretchText", "drawTodayText", "getDailyTaskDateList", "getDrawableBounds", "Landroid/graphics/Rect;", "centerX", "centerY", "getHolidayWorkdayLocation", "", "", "getTextBaseLineY", "onDrawCurrentMonthOrWeek", "onDrawDisableDate", "onDrawLastOrNextMonth", "onDrawToday", "setDailyTaskDataList", "listTask", "listTaskFinish", "listTaskFail", "listTaskTodo", "setLegalHolidayList", "holidayList", "workdayList", "setPointList", "setReplaceLunarColorMap", "replaceLunarColorMap", "", "setReplaceLunarStrMap", "replaceLunarStrMap", "setStretchStrMap", "stretchStrMap", "ncalendar_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class InnerPainter implements CalendarPainter {

    @NotNull
    private final ICalendar iCalendar;

    @NotNull
    private final Context mContext;

    @NotNull
    private final DateTimeFormatter mDateFormatter;

    @Nullable
    private final Drawable mDefaultCheckedBackground;

    @Nullable
    private final Drawable mDefaultCheckedPoint;

    @Nullable
    private final Drawable mDefaultUnCheckedBackground;

    @Nullable
    private final Drawable mDefaultUnCheckedPoint;

    @NotNull
    private final List<LocalDate> mHolidayList;

    @NotNull
    private final List<LocalDate> mPointList;

    @NotNull
    private final Map<LocalDate, Integer> mReplaceLunarColorMap;

    @NotNull
    private final Map<LocalDate, String> mReplaceLunarStrMap;

    @NotNull
    private final Map<LocalDate, String> mStretchStrMap;

    @NotNull
    private final List<LocalDate> mTaskFailList;

    @NotNull
    private final List<LocalDate> mTaskFinishList;

    @NotNull
    private final List<LocalDate> mTaskList;

    @NotNull
    private final List<LocalDate> mTaskTodoList;

    @NotNull
    private final Paint mTextPaint;

    @Nullable
    private final Drawable mTodayCheckedBackground;

    @Nullable
    private final Drawable mTodayCheckedPoint;

    @Nullable
    private final Drawable mTodayUnCheckedBackground;

    @Nullable
    private final Drawable mTodayUnCheckedPoint;

    @NotNull
    private final List<LocalDate> mWorkdayList;
    private final int noAlphaColor;

    public InnerPainter(@NotNull Context mContext, @NotNull ICalendar iCalendar) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Intrinsics.checkNotNullParameter(iCalendar, "iCalendar");
        this.mContext = mContext;
        this.iCalendar = iCalendar;
        Paint paint = new Paint();
        this.mTextPaint = paint;
        this.noAlphaColor = 255;
        DateTimeFormatter dateTimeFormatterOfPattern = DateTimeFormatter.ofPattern("yyyy-M-d");
        Intrinsics.checkNotNullExpressionValue(dateTimeFormatterOfPattern, "ofPattern(\"yyyy-M-d\")");
        this.mDateFormatter = dateTimeFormatterOfPattern;
        this.mTaskList = new ArrayList();
        this.mTaskFinishList = new ArrayList();
        this.mTaskTodoList = new ArrayList();
        this.mTaskFailList = new ArrayList();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        this.mPointList = new ArrayList();
        this.mHolidayList = new ArrayList();
        this.mWorkdayList = new ArrayList();
        this.mReplaceLunarStrMap = new HashMap();
        this.mReplaceLunarColorMap = new HashMap();
        this.mStretchStrMap = new HashMap();
        NAttrs nAttrs = NAttrs.INSTANCE;
        this.mDefaultCheckedBackground = ContextCompat.getDrawable(mContext, nAttrs.getDefaultCheckedBackground());
        this.mDefaultUnCheckedBackground = ContextCompat.getDrawable(mContext, nAttrs.getDefaultUnCheckedBackground());
        this.mTodayCheckedBackground = ContextCompat.getDrawable(mContext, nAttrs.getTodayCheckedBackground());
        this.mTodayUnCheckedBackground = ContextCompat.getDrawable(mContext, nAttrs.getTodayUnCheckedBackground());
        this.mDefaultCheckedPoint = ContextCompat.getDrawable(mContext, nAttrs.getDefaultCheckedPoint());
        this.mDefaultUnCheckedPoint = ContextCompat.getDrawable(mContext, nAttrs.getDefaultUnCheckedPoint());
        this.mTodayCheckedPoint = ContextCompat.getDrawable(mContext, nAttrs.getTodayCheckedPoint());
        this.mTodayUnCheckedPoint = ContextCompat.getDrawable(mContext, nAttrs.getTodayUnCheckedPoint());
    }

    private final void drawCheckedBackground(Canvas canvas, Drawable drawable, RectF rectF, int alphaColor) {
        Intrinsics.checkNotNull(rectF);
        Rect drawableBounds = getDrawableBounds((int) rectF.centerX(), (int) rectF.centerY(), drawable);
        Intrinsics.checkNotNull(drawable);
        drawable.setBounds(drawableBounds);
        drawable.setAlpha(alphaColor);
        Intrinsics.checkNotNull(canvas);
        drawable.draw(canvas);
    }

    private final void drawCheckedBackgroundDailyStyle(Canvas canvas, RectF rectF, int alphaColor, LocalDate localDate, boolean checked) {
        Drawable dailyTaskCalendarToDoBg;
        Drawable dailyTaskCalendarFinishBg = null;
        if (checked) {
            if (LocalDate.now().equals(localDate)) {
                dailyTaskCalendarToDoBg = NAttrs.INSTANCE.getDailyTaskCalendarTodayBg();
            } else if (this.mTaskFinishList.contains(localDate)) {
                dailyTaskCalendarToDoBg = NAttrs.INSTANCE.getDailyTaskCalendarFinishBg();
            } else if (this.mTaskFailList.contains(localDate) || this.mTaskTodoList.contains(localDate)) {
                dailyTaskCalendarToDoBg = NAttrs.INSTANCE.getDailyTaskCalendarToDoBg();
            }
            dailyTaskCalendarFinishBg = dailyTaskCalendarToDoBg;
        } else {
            Log.d("TAG", "drawCheckedBackgroundDailyStyle: date:  " + localDate + ' ');
            if (LocalDate.now().equals(localDate)) {
                dailyTaskCalendarFinishBg = NAttrs.INSTANCE.getDailyTaskCalendarTodayBg();
                Log.d("TAG", "drawCheckedBackgroundDailyStyle: bg:  today ");
            } else if (this.mTaskFailList.contains(localDate) || this.mTaskTodoList.contains(localDate)) {
                dailyTaskCalendarFinishBg = NAttrs.INSTANCE.getDailyTaskCalendarToDoBg();
            } else if (this.mTaskFinishList.contains(localDate)) {
                dailyTaskCalendarFinishBg = NAttrs.INSTANCE.getDailyTaskCalendarFinishBg();
            }
        }
        if (dailyTaskCalendarFinishBg != null) {
            Intrinsics.checkNotNull(rectF);
            dailyTaskCalendarFinishBg.setBounds(getDrawableBounds((int) rectF.centerX(), (int) rectF.centerY(), dailyTaskCalendarFinishBg));
            dailyTaskCalendarFinishBg.setAlpha(alphaColor);
            Intrinsics.checkNotNull(canvas);
            dailyTaskCalendarFinishBg.draw(canvas);
        }
    }

    private final void drawHolidayWorkday(Canvas canvas, RectF rectF, LocalDate localDate, Drawable holidayDrawable, Drawable workdayDrawable, int holidayTextColor, int workdayTextColor, int alphaColor) {
        String workdayText;
        String holidayText;
        NAttrs nAttrs = NAttrs.INSTANCE;
        if (nAttrs.getShowHolidayWorkday()) {
            int[] holidayWorkdayLocation = getHolidayWorkdayLocation(rectF.centerX(), rectF.centerY());
            if (this.mHolidayList.contains(localDate)) {
                if (holidayDrawable != null) {
                    holidayDrawable.setBounds(getDrawableBounds(holidayWorkdayLocation[0], holidayWorkdayLocation[1], holidayDrawable));
                    holidayDrawable.setAlpha(alphaColor);
                    holidayDrawable.draw(canvas);
                    return;
                }
                this.mTextPaint.setTextSize(nAttrs.getHolidayWorkdayTextSize());
                this.mTextPaint.setColor(holidayTextColor);
                this.mTextPaint.setAlpha(alphaColor);
                if (TextUtils.isEmpty(nAttrs.getHolidayText())) {
                    holidayText = this.mContext.getString(R.string.N_holidayText);
                } else {
                    holidayText = nAttrs.getHolidayText();
                    Intrinsics.checkNotNull(holidayText);
                }
                canvas.drawText(holidayText, holidayWorkdayLocation[0], getTextBaseLineY(holidayWorkdayLocation[1]), this.mTextPaint);
                return;
            }
            if (this.mWorkdayList.contains(localDate)) {
                if (workdayDrawable != null) {
                    workdayDrawable.setBounds(getDrawableBounds(holidayWorkdayLocation[0], holidayWorkdayLocation[1], workdayDrawable));
                    workdayDrawable.setAlpha(alphaColor);
                    workdayDrawable.draw(canvas);
                    return;
                }
                this.mTextPaint.setTextSize(nAttrs.getHolidayWorkdayTextSize());
                this.mTextPaint.setColor(workdayTextColor);
                this.mTextPaint.setAlpha(alphaColor);
                this.mTextPaint.setFakeBoldText(nAttrs.getHolidayWorkdayTextBold());
                if (TextUtils.isEmpty(nAttrs.getWorkdayText())) {
                    workdayText = this.mContext.getString(R.string.N_workdayText);
                } else {
                    workdayText = nAttrs.getWorkdayText();
                    Intrinsics.checkNotNull(workdayText);
                }
                canvas.drawText(workdayText, holidayWorkdayLocation[0], getTextBaseLineY(holidayWorkdayLocation[1]), this.mTextPaint);
            }
        }
    }

    private final void drawLunar(Canvas canvas, RectF rectF, LocalDate localDate, int color, int alphaColor) {
        NAttrs nAttrs = NAttrs.INSTANCE;
        if (nAttrs.getShowLunar()) {
            ChineseDate chineseDate = new ChineseDate(localDate);
            String chineseDay = this.mReplaceLunarStrMap.get(localDate);
            if (!TextUtils.isEmpty(chineseDay)) {
                Intrinsics.checkNotNull(chineseDay);
            } else if (!TextUtils.isEmpty(chineseDate.getLunarFestivals())) {
                chineseDay = chineseDate.getLunarFestivals();
                Intrinsics.checkNotNull(chineseDay);
            } else if (!TextUtils.isEmpty(chineseDate.getTerm())) {
                chineseDay = chineseDate.getTerm();
            } else if (TextUtils.isEmpty(chineseDate.getSolarFestivals())) {
                chineseDay = chineseDate.getChineseDay();
                if (Intrinsics.areEqual(chineseDay, "初一")) {
                    chineseDay = chineseDate.getChineseMonthName();
                }
            } else {
                chineseDay = chineseDate.getSolarFestivals();
                Intrinsics.checkNotNull(chineseDay);
            }
            Integer num = this.mReplaceLunarColorMap.get(localDate);
            Paint paint = this.mTextPaint;
            if (num != null) {
                color = num.intValue();
            }
            paint.setColor(color);
            this.mTextPaint.setTextSize(nAttrs.getLunarTextSize());
            this.mTextPaint.setAlpha(alphaColor);
            this.mTextPaint.setFakeBoldText(nAttrs.getLunarTextBold());
            Intrinsics.checkNotNull(canvas);
            Intrinsics.checkNotNull(rectF);
            canvas.drawText(chineseDay, rectF.centerX(), rectF.centerY() + nAttrs.getLunarDistance(), this.mTextPaint);
        }
    }

    private final void drawPoint(Canvas canvas, RectF rectF, LocalDate date, Drawable drawable, int alphaColor) {
        if (CollectionsKt___CollectionsKt.contains(this.mPointList, date)) {
            NAttrs nAttrs = NAttrs.INSTANCE;
            int pointLocation = nAttrs.getPointLocation();
            Intrinsics.checkNotNull(rectF);
            Rect drawableBounds = getDrawableBounds((int) rectF.centerX(), (int) (pointLocation == 201 ? rectF.centerY() + nAttrs.getPointDistance() : rectF.centerY() - nAttrs.getPointDistance()), drawable);
            Intrinsics.checkNotNull(drawable);
            drawable.setBounds(drawableBounds);
            drawable.setAlpha(alphaColor);
            Intrinsics.checkNotNull(canvas);
            drawable.draw(canvas);
        }
    }

    private final void drawPointDailyStyle(Canvas canvas, RectF rectF, LocalDate date, Drawable drawable, int alphaColor) {
        Drawable dailyTaskCalendarToDoPoint;
        if (this.mPointList.size() > 0) {
            LocalDate localDate = this.mPointList.get(0);
            dailyTaskCalendarToDoPoint = this.mTaskFinishList.contains(localDate) ? NAttrs.INSTANCE.getDailyTaskCalendarFinishPoint() : (this.mTaskFailList.contains(localDate) || this.mTaskTodoList.contains(localDate)) ? NAttrs.INSTANCE.getDailyTaskCalendarToDoPoint() : NAttrs.INSTANCE.getDailyTaskCalendarNoTaskPoint();
        } else {
            dailyTaskCalendarToDoPoint = null;
        }
        if (CollectionsKt___CollectionsKt.contains(this.mPointList, date)) {
            NAttrs nAttrs = NAttrs.INSTANCE;
            int pointLocation = nAttrs.getPointLocation();
            Intrinsics.checkNotNull(rectF);
            Rect drawableBounds = getDrawableBounds((int) rectF.centerX(), (int) (pointLocation == 201 ? rectF.centerY() + nAttrs.getPointDistance() : rectF.centerY() - nAttrs.getPointDistance()), dailyTaskCalendarToDoPoint);
            Intrinsics.checkNotNull(dailyTaskCalendarToDoPoint);
            dailyTaskCalendarToDoPoint.setBounds(drawableBounds);
            dailyTaskCalendarToDoPoint.setAlpha(alphaColor);
            Intrinsics.checkNotNull(canvas);
            dailyTaskCalendarToDoPoint.draw(canvas);
        }
    }

    private final void drawPointNew(Canvas canvas, RectF rectF, LocalDate date, Drawable drawable, int alphaColor, List<LocalDate> checkedDateList) {
        if (!NAttrs.INSTANCE.isDailyCalendarStyle()) {
            drawPoint(canvas, rectF, date, drawable, alphaColor);
            return;
        }
        this.mPointList.clear();
        this.mPointList.addAll(checkedDateList);
        drawPointDailyStyle(canvas, rectF, date, drawable, alphaColor);
    }

    private final void drawSolar(Canvas canvas, RectF rectF, LocalDate date, int color, int alphaColor, List<LocalDate> checkedDateList) {
        this.mTextPaint.setColor(color);
        NAttrs nAttrs = NAttrs.INSTANCE;
        if (nAttrs.isDailyCalendarStyle()) {
            if (CollectionsKt___CollectionsKt.contains(this.mTaskFinishList, date) || LocalDate.now().equals(date)) {
                this.mTextPaint.setColor(nAttrs.getTodayCheckedSolarTextColor());
            } else {
                this.mTextPaint.setColor(nAttrs.getTodayUnCheckedSolarTextColor());
            }
        }
        this.mTextPaint.setAlpha(alphaColor);
        this.mTextPaint.setTextSize(nAttrs.getSolarTextSize());
        this.mTextPaint.setFakeBoldText(nAttrs.getSolarTextBold());
        Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
        Intrinsics.checkNotNullExpressionValue(fontMetrics, "mTextPaint.fontMetrics");
        float f2 = fontMetrics.bottom;
        float f3 = ((f2 - fontMetrics.top) / 2) - f2;
        Intrinsics.checkNotNull(canvas);
        StringBuilder sb = new StringBuilder();
        Intrinsics.checkNotNull(date);
        sb.append(date.getDayOfMonth());
        sb.append("");
        String string = sb.toString();
        Intrinsics.checkNotNull(rectF);
        canvas.drawText(string, rectF.centerX(), rectF.centerY() + f3, this.mTextPaint);
    }

    public static /* synthetic */ void drawSolar$default(InnerPainter innerPainter, Canvas canvas, RectF rectF, LocalDate localDate, int i2, int i3, List list, int i4, Object obj) {
        if ((i4 & 32) != 0) {
            list = new ArrayList();
        }
        innerPainter.drawSolar(canvas, rectF, localDate, i2, i3, list);
    }

    private final void drawSolarDailyStyle(Canvas canvas, RectF rectF, LocalDate date, int color, int alphaColor) {
        this.mTextPaint.setColor(color);
        this.mTextPaint.setAlpha(alphaColor);
        Paint paint = this.mTextPaint;
        NAttrs nAttrs = NAttrs.INSTANCE;
        paint.setTextSize(nAttrs.getSolarTextSize());
        this.mTextPaint.setFakeBoldText(nAttrs.getSolarTextBold());
        Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
        Intrinsics.checkNotNullExpressionValue(fontMetrics, "mTextPaint.fontMetrics");
        float f2 = fontMetrics.bottom;
        float f3 = fontMetrics.top;
        float f4 = ((f2 - f3) / 2) - f2;
        float f5 = f2 - f3;
        Intrinsics.checkNotNull(canvas);
        StringBuilder sb = new StringBuilder();
        Intrinsics.checkNotNull(date);
        sb.append(date.getDayOfMonth());
        sb.append("");
        String string = sb.toString();
        Intrinsics.checkNotNull(rectF);
        canvas.drawText(string, rectF.centerX(), ((rectF.bottom - f5) - f4) - 10, this.mTextPaint);
    }

    private final void drawStretchText(Canvas canvas, RectF rectF, int alphaColor, LocalDate localDate) {
        Intrinsics.checkNotNull(rectF);
        float fCenterY = rectF.centerY();
        NAttrs nAttrs = NAttrs.INSTANCE;
        if (fCenterY + nAttrs.getStretchTextDistance() <= rectF.bottom) {
            String str = this.mStretchStrMap.get(localDate);
            if (TextUtils.isEmpty(str)) {
                return;
            }
            this.mTextPaint.setTextSize(nAttrs.getStretchTextSize());
            this.mTextPaint.setColor(nAttrs.getStretchTextColor());
            this.mTextPaint.setAlpha(alphaColor);
            this.mTextPaint.setFakeBoldText(nAttrs.getStretchTextBold());
            Intrinsics.checkNotNull(canvas);
            Intrinsics.checkNotNull(str);
            canvas.drawText(str, rectF.centerX(), rectF.centerY() + nAttrs.getStretchTextDistance(), this.mTextPaint);
        }
    }

    private final void drawTodayText(Canvas canvas, RectF rectF, int color, int alphaColor) {
        NAttrs nAttrs = NAttrs.INSTANCE;
        if (nAttrs.isDailyCalendarStyle()) {
            this.mTextPaint.setColor(nAttrs.getTodayCheckedSolarTextColor());
        } else {
            this.mTextPaint.setColor(color);
        }
        this.mTextPaint.setAlpha(alphaColor);
        this.mTextPaint.setTextSize(nAttrs.getSolarTextSize());
        this.mTextPaint.setFakeBoldText(true);
        Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
        Intrinsics.checkNotNullExpressionValue(fontMetrics, "mTextPaint.fontMetrics");
        float f2 = fontMetrics.bottom;
        float f3 = ((f2 - fontMetrics.top) / 2) - f2;
        Intrinsics.checkNotNull(canvas);
        Intrinsics.checkNotNull(rectF);
        canvas.drawText("今", rectF.centerX() - 4, rectF.centerY() + f3, this.mTextPaint);
    }

    private final Rect getDrawableBounds(int centerX, int centerY, Drawable drawable) {
        return drawable == null ? new Rect() : new Rect(centerX - (drawable.getIntrinsicWidth() / 2), centerY - (drawable.getIntrinsicHeight() / 2), centerX + (drawable.getIntrinsicWidth() / 2), centerY + (drawable.getIntrinsicHeight() / 2));
    }

    private final int[] getHolidayWorkdayLocation(float centerX, float centerY) {
        int[] iArr = new int[2];
        NAttrs nAttrs = NAttrs.INSTANCE;
        switch (nAttrs.getHolidayWorkdayLocation()) {
            case 400:
                iArr[0] = (int) (centerX + nAttrs.getHolidayWorkdayDistance());
                iArr[1] = (int) (centerY - (nAttrs.getHolidayWorkdayDistance() / 2));
                return iArr;
            case 401:
                iArr[0] = (int) (centerX - nAttrs.getHolidayWorkdayDistance());
                iArr[1] = (int) (centerY - (nAttrs.getHolidayWorkdayDistance() / 2));
                return iArr;
            case 402:
                iArr[0] = (int) (centerX + nAttrs.getHolidayWorkdayDistance());
                iArr[1] = (int) (centerY + (nAttrs.getHolidayWorkdayDistance() / 2));
                return iArr;
            case 403:
                iArr[0] = (int) (centerX - nAttrs.getHolidayWorkdayDistance());
                iArr[1] = (int) (centerY + (nAttrs.getHolidayWorkdayDistance() / 2));
                return iArr;
            default:
                iArr[0] = (int) (centerX + nAttrs.getHolidayWorkdayDistance());
                iArr[1] = (int) (centerY - (nAttrs.getHolidayWorkdayDistance() / 2));
                return iArr;
        }
    }

    private final float getTextBaseLineY(float centerY) {
        Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
        float f2 = fontMetrics.bottom;
        float f3 = fontMetrics.top;
        return (centerY - ((f2 - f3) / 2)) - f3;
    }

    public final void addPointList(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            try {
                LocalDate localDate = LocalDate.parse(list.get(i2), this.mDateFormatter);
                if (!CollectionsKt___CollectionsKt.contains(this.mPointList, localDate) && localDate != null) {
                    this.mPointList.add(localDate);
                }
            } catch (Exception unused) {
                throw new RuntimeException("setPointList的参数需要 yyyy-MM-dd 格式的日期");
            }
        }
        this.iCalendar.notifyCalendar();
    }

    @NotNull
    public final List<LocalDate> getDailyTaskDateList() {
        return this.mTaskList;
    }

    @NotNull
    public final ICalendar getICalendar() {
        return this.iCalendar;
    }

    @Override // com.necer.painter.CalendarPainter
    public void onDrawCurrentMonthOrWeek(@NotNull Canvas canvas, @NotNull RectF rectF, @NotNull LocalDate localDate, @NotNull List<LocalDate> checkedDateList) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        Intrinsics.checkNotNullParameter(localDate, "localDate");
        Intrinsics.checkNotNullParameter(checkedDateList, "checkedDateList");
        if (checkedDateList.contains(localDate)) {
            NAttrs nAttrs = NAttrs.INSTANCE;
            if (nAttrs.isDailyCalendarStyle()) {
                drawCheckedBackgroundDailyStyle(canvas, rectF, this.noAlphaColor, localDate, true);
            } else {
                drawCheckedBackground(canvas, this.mDefaultCheckedBackground, rectF, this.noAlphaColor);
            }
            drawSolar(canvas, rectF, localDate, nAttrs.getDefaultCheckedSolarTextColor(), this.noAlphaColor, checkedDateList);
            drawPointNew(canvas, rectF, localDate, this.mDefaultCheckedPoint, this.noAlphaColor, checkedDateList);
            drawHolidayWorkday(canvas, rectF, localDate, nAttrs.getDefaultCheckedHoliday(), nAttrs.getDefaultCheckedWorkday(), nAttrs.getDefaultCheckedHolidayTextColor(), nAttrs.getDefaultCheckedWorkdayTextColor(), this.noAlphaColor);
        } else {
            NAttrs nAttrs2 = NAttrs.INSTANCE;
            if (nAttrs2.isDailyCalendarStyle()) {
                drawCheckedBackgroundDailyStyle(canvas, rectF, this.noAlphaColor, localDate, false);
            } else {
                drawCheckedBackground(canvas, this.mDefaultUnCheckedBackground, rectF, this.noAlphaColor);
            }
            drawSolar(canvas, rectF, localDate, nAttrs2.getDefaultUnCheckedSolarTextColor(), this.noAlphaColor, checkedDateList);
            drawPointNew(canvas, rectF, localDate, this.mDefaultUnCheckedPoint, this.noAlphaColor, checkedDateList);
            drawHolidayWorkday(canvas, rectF, localDate, nAttrs2.getDefaultUnCheckedHoliday(), nAttrs2.getDefaultUnCheckedWorkday(), nAttrs2.getDefaultUnCheckedHolidayTextColor(), nAttrs2.getDefaultUnCheckedWorkdayTextColor(), this.noAlphaColor);
        }
        drawStretchText(canvas, rectF, this.noAlphaColor, localDate);
    }

    @Override // com.necer.painter.CalendarPainter
    public void onDrawDisableDate(@NotNull Canvas canvas, @NotNull RectF rectF, @NotNull LocalDate localDate) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        Intrinsics.checkNotNullParameter(localDate, "localDate");
        NAttrs nAttrs = NAttrs.INSTANCE;
        drawSolar$default(this, canvas, rectF, localDate, nAttrs.getDefaultUnCheckedSolarTextColor(), nAttrs.getDisabledAlphaColor(), null, 32, null);
        drawPoint(canvas, rectF, localDate, this.mDefaultUnCheckedPoint, nAttrs.getDisabledAlphaColor());
        drawHolidayWorkday(canvas, rectF, localDate, nAttrs.getDefaultUnCheckedHoliday(), nAttrs.getDefaultUnCheckedWorkday(), nAttrs.getDefaultUnCheckedHolidayTextColor(), nAttrs.getDefaultUnCheckedWorkdayTextColor(), nAttrs.getDisabledAlphaColor());
        drawStretchText(canvas, rectF, nAttrs.getDisabledAlphaColor(), localDate);
    }

    @Override // com.necer.painter.CalendarPainter
    public void onDrawLastOrNextMonth(@NotNull Canvas canvas, @NotNull RectF rectF, @NotNull LocalDate localDate, @NotNull List<LocalDate> checkedDateList) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        Intrinsics.checkNotNullParameter(localDate, "localDate");
        Intrinsics.checkNotNullParameter(checkedDateList, "checkedDateList");
        if (checkedDateList.contains(localDate)) {
            NAttrs nAttrs = NAttrs.INSTANCE;
            if (nAttrs.isDailyCalendarStyle()) {
                drawCheckedBackgroundDailyStyle(canvas, rectF, this.noAlphaColor, localDate, true);
            } else {
                drawCheckedBackground(canvas, this.mDefaultCheckedBackground, rectF, nAttrs.getLastNextMothAlphaColor());
            }
            drawSolar(canvas, rectF, localDate, nAttrs.getDefaultCheckedSolarTextColor(), nAttrs.getLastNextMothAlphaColor(), checkedDateList);
            drawPointNew(canvas, rectF, localDate, this.mDefaultCheckedPoint, nAttrs.getLastNextMothAlphaColor(), checkedDateList);
            drawHolidayWorkday(canvas, rectF, localDate, nAttrs.getDefaultCheckedHoliday(), nAttrs.getDefaultCheckedWorkday(), nAttrs.getDefaultCheckedHolidayTextColor(), nAttrs.getDefaultCheckedWorkdayTextColor(), nAttrs.getLastNextMothAlphaColor());
        } else {
            NAttrs nAttrs2 = NAttrs.INSTANCE;
            drawSolar(canvas, rectF, localDate, nAttrs2.getDefaultUnCheckedSolarTextColor(), nAttrs2.getLastNextMothAlphaColor(), checkedDateList);
            drawPointNew(canvas, rectF, localDate, this.mDefaultUnCheckedPoint, nAttrs2.getLastNextMothAlphaColor(), checkedDateList);
            drawHolidayWorkday(canvas, rectF, localDate, nAttrs2.getDefaultUnCheckedHoliday(), nAttrs2.getDefaultUnCheckedWorkday(), nAttrs2.getDefaultUnCheckedHolidayTextColor(), nAttrs2.getDefaultUnCheckedWorkdayTextColor(), nAttrs2.getLastNextMothAlphaColor());
        }
        drawStretchText(canvas, rectF, NAttrs.INSTANCE.getLastNextMothAlphaColor(), localDate);
    }

    @Override // com.necer.painter.CalendarPainter
    public void onDrawToday(@NotNull Canvas canvas, @NotNull RectF rectF, @NotNull LocalDate localDate, @NotNull List<LocalDate> checkedDateList) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        Intrinsics.checkNotNullParameter(localDate, "localDate");
        Intrinsics.checkNotNullParameter(checkedDateList, "checkedDateList");
        if (checkedDateList.contains(localDate)) {
            NAttrs nAttrs = NAttrs.INSTANCE;
            if (nAttrs.isDailyCalendarStyle()) {
                drawCheckedBackgroundDailyStyle(canvas, rectF, this.noAlphaColor, localDate, true);
            } else {
                drawCheckedBackground(canvas, this.mTodayCheckedBackground, rectF, this.noAlphaColor);
            }
            drawTodayText(canvas, rectF, nAttrs.getTodayCheckedSolarTextColor(), this.noAlphaColor);
            drawPointNew(canvas, rectF, localDate, this.mTodayCheckedPoint, this.noAlphaColor, checkedDateList);
            drawHolidayWorkday(canvas, rectF, localDate, nAttrs.getTodayCheckedHoliday(), nAttrs.getTodayCheckedWorkday(), nAttrs.getTodayCheckedHolidayTextColor(), nAttrs.getTodayCheckedWorkdayTextColor(), this.noAlphaColor);
        } else {
            NAttrs nAttrs2 = NAttrs.INSTANCE;
            if (nAttrs2.isDailyCalendarStyle()) {
                drawCheckedBackgroundDailyStyle(canvas, rectF, this.noAlphaColor, localDate, false);
            } else {
                drawCheckedBackground(canvas, this.mTodayUnCheckedBackground, rectF, this.noAlphaColor);
            }
            if (nAttrs2.isDailyCalendarStyle() && this.mTaskFinishList.contains(localDate) && nAttrs2.getDailyTaskCalendarFinishTextColor() != null) {
                Integer dailyTaskCalendarFinishTextColor = nAttrs2.getDailyTaskCalendarFinishTextColor();
                Intrinsics.checkNotNull(dailyTaskCalendarFinishTextColor);
                drawTodayText(canvas, rectF, dailyTaskCalendarFinishTextColor.intValue(), this.noAlphaColor);
            } else {
                drawTodayText(canvas, rectF, nAttrs2.getTodayUnCheckedSolarTextColor(), this.noAlphaColor);
            }
            drawPointNew(canvas, rectF, localDate, this.mTodayUnCheckedPoint, this.noAlphaColor, checkedDateList);
            drawHolidayWorkday(canvas, rectF, localDate, nAttrs2.getTodayUnCheckedHoliday(), nAttrs2.getTodayUnCheckedWorkday(), nAttrs2.getTodayUnCheckedHolidayTextColor(), nAttrs2.getTodayUnCheckedWorkdayTextColor(), this.noAlphaColor);
        }
        drawStretchText(canvas, rectF, this.noAlphaColor, localDate);
    }

    public final void setDailyTaskDataList(@NotNull List<String> listTask, @NotNull List<String> listTaskFinish, @NotNull List<String> listTaskFail, @NotNull List<String> listTaskTodo) {
        Intrinsics.checkNotNullParameter(listTask, "listTask");
        Intrinsics.checkNotNullParameter(listTaskFinish, "listTaskFinish");
        Intrinsics.checkNotNullParameter(listTaskFail, "listTaskFail");
        Intrinsics.checkNotNullParameter(listTaskTodo, "listTaskTodo");
        this.mTaskList.clear();
        int size = listTask.size();
        for (int i2 = 0; i2 < size; i2++) {
            try {
                LocalDate localDate = LocalDate.parse(listTask.get(i2), this.mDateFormatter);
                if (localDate != null) {
                    this.mTaskList.add(localDate);
                }
            } catch (Exception unused) {
                throw new RuntimeException("setDailyTaskDataList的参数需要 yyyy-MM-dd 格式的日期");
            }
        }
        this.mTaskFinishList.clear();
        int size2 = listTaskFinish.size();
        for (int i3 = 0; i3 < size2; i3++) {
            try {
                LocalDate localDate2 = LocalDate.parse(listTaskFinish.get(i3), this.mDateFormatter);
                if (localDate2 != null) {
                    this.mTaskFinishList.add(localDate2);
                }
            } catch (Exception unused2) {
                throw new RuntimeException("setDailyTaskDataList的参数需要 yyyy-MM-dd 格式的日期");
            }
        }
        this.mTaskFailList.clear();
        int size3 = listTaskFail.size();
        for (int i4 = 0; i4 < size3; i4++) {
            try {
                LocalDate localDate3 = LocalDate.parse(listTaskFail.get(i4), this.mDateFormatter);
                if (localDate3 != null) {
                    this.mTaskFailList.add(localDate3);
                }
            } catch (Exception unused3) {
                throw new RuntimeException("setDailyTaskDataList的参数需要 yyyy-MM-dd 格式的日期");
            }
        }
        this.mTaskTodoList.clear();
        int size4 = listTaskTodo.size();
        for (int i5 = 0; i5 < size4; i5++) {
            try {
                LocalDate localDate4 = LocalDate.parse(listTaskTodo.get(i5), this.mDateFormatter);
                if (localDate4 != null) {
                    this.mTaskTodoList.add(localDate4);
                }
            } catch (Exception unused4) {
                throw new RuntimeException("setDailyTaskDataList的参数需要 yyyy-MM-dd 格式的日期");
            }
        }
        this.iCalendar.notifyCalendar();
    }

    public final void setLegalHolidayList(@NotNull List<String> holidayList, @NotNull List<String> workdayList) {
        Intrinsics.checkNotNullParameter(holidayList, "holidayList");
        Intrinsics.checkNotNullParameter(workdayList, "workdayList");
        this.mHolidayList.clear();
        this.mWorkdayList.clear();
        int size = holidayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            try {
                LocalDate localDate = LocalDate.parse(holidayList.get(i2), this.mDateFormatter);
                Intrinsics.checkNotNullExpressionValue(localDate, "{\n                LocalD…eFormatter)\n            }");
                this.mHolidayList.add(localDate);
            } catch (Exception unused) {
                throw new RuntimeException("setLegalHolidayList集合中的参数需要 yyyy-MM-dd 格式的日期");
            }
        }
        int size2 = workdayList.size();
        for (int i3 = 0; i3 < size2; i3++) {
            try {
                LocalDate localDate2 = LocalDate.parse(workdayList.get(i3), this.mDateFormatter);
                Intrinsics.checkNotNullExpressionValue(localDate2, "{\n                LocalD…eFormatter)\n            }");
                this.mWorkdayList.add(localDate2);
            } catch (Exception unused2) {
                throw new RuntimeException("setLegalHolidayList集合中的参数需要 yyyy-MM-dd 格式的日期");
            }
        }
        this.iCalendar.notifyCalendar();
    }

    public final void setPointList(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        this.mPointList.clear();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            try {
                LocalDate localDate = LocalDate.parse(list.get(i2), this.mDateFormatter);
                if (localDate != null) {
                    this.mPointList.add(localDate);
                }
            } catch (Exception unused) {
                throw new RuntimeException("setPointList的参数需要 yyyy-MM-dd 格式的日期");
            }
        }
        this.iCalendar.notifyCalendar();
    }

    public final void setReplaceLunarColorMap(@NotNull Map<String, Integer> replaceLunarColorMap) {
        Intrinsics.checkNotNullParameter(replaceLunarColorMap, "replaceLunarColorMap");
        this.mReplaceLunarColorMap.clear();
        for (String str : replaceLunarColorMap.keySet()) {
            try {
                LocalDate localDate = LocalDate.parse(str, this.mDateFormatter);
                Intrinsics.checkNotNullExpressionValue(localDate, "{\n                LocalD…eFormatter)\n            }");
                this.mReplaceLunarColorMap.put(localDate, replaceLunarColorMap.get(str));
            } catch (Exception unused) {
                throw new RuntimeException("setReplaceLunarColorMap的参数需要 yyyy-MM-dd 格式的日期");
            }
        }
        this.iCalendar.notifyCalendar();
    }

    public final void setReplaceLunarStrMap(@NotNull Map<String, String> replaceLunarStrMap) {
        Intrinsics.checkNotNullParameter(replaceLunarStrMap, "replaceLunarStrMap");
        this.mReplaceLunarStrMap.clear();
        for (String str : replaceLunarStrMap.keySet()) {
            try {
                LocalDate localDate = LocalDate.parse(str, this.mDateFormatter);
                Intrinsics.checkNotNullExpressionValue(localDate, "{\n                LocalD…eFormatter)\n            }");
                this.mReplaceLunarStrMap.put(localDate, replaceLunarStrMap.get(str));
            } catch (Exception unused) {
                throw new RuntimeException("setReplaceLunarStrMap的参数需要 yyyy-MM-dd 格式的日期");
            }
        }
        this.iCalendar.notifyCalendar();
    }

    public final void setStretchStrMap(@NotNull Map<String, String> stretchStrMap) {
        Intrinsics.checkNotNullParameter(stretchStrMap, "stretchStrMap");
        this.mStretchStrMap.clear();
        for (String str : stretchStrMap.keySet()) {
            try {
                LocalDate localDate = LocalDate.parse(str, this.mDateFormatter);
                Intrinsics.checkNotNullExpressionValue(localDate, "{\n                LocalD…eFormatter)\n            }");
                this.mStretchStrMap.put(localDate, stretchStrMap.get(str));
            } catch (Exception unused) {
                throw new RuntimeException("setStretchStrMap的参数需要 yyyy-MM-dd 格式的日期");
            }
        }
        this.iCalendar.notifyCalendar();
    }
}
