package com.necer.calendar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.app.NotificationCompat;
import com.necer.drawable.TextDrawable;
import com.necer.enumeration.CalendarState;
import com.necer.painter.CalendarPainter;
import com.necer.utils.NAttrs;
import com.necer.utils.NDateUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004B!\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tB\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0002J\u0010\u0010+\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0002J\u0010\u0010,\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0002J\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\b0\u0018J\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\b0\u0018J\u0006\u0010/\u001a\u00020\bJ\u0006\u00100\u001a\u00020\u0014J\b\u00101\u001a\u00020\u001cH\u0002J\u0018\u00102\u001a\u00020\u001a2\u0006\u00103\u001a\u00020\u001c2\u0006\u00104\u001a\u00020\u001cH\u0002J\u001c\u00105\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00182\f\u00106\u001a\b\u0012\u0004\u0012\u00020\b0\u0018H\u0002J\u000e\u00107\u001a\u00020(2\u0006\u00108\u001a\u00020\u0014J\u0010\u00109\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0014J\u0010\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=H\u0016J.\u0010>\u001a\u00020\u001a2\u0006\u0010?\u001a\u00020\u001a2\u0006\u00103\u001a\u00020\u001c2\u0006\u00104\u001a\u00020\u001c2\f\u00106\u001a\b\u0012\u0004\u0012\u00020\b0\u0018H\u0002R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0018X\u0082.¢\u0006\u0002\n\u0000R\u0016\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u001f\u001a\u00020\bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\b0\u0018X\u0082.¢\u0006\u0002\n\u0000R\u0016\u0010%\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006@"}, d2 = {"Lcom/necer/calendar/NCalendarView;", "Landroid/view/View;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "viewGroup", "Landroid/view/ViewGroup;", "localDate", "Ljava/time/LocalDate;", "(Landroid/content/Context;Landroid/view/ViewGroup;Ljava/time/LocalDate;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "backgroundRect", "Landroid/graphics/Rect;", "bgDrawable", "Landroid/graphics/drawable/Drawable;", "calendarPainter", "Lcom/necer/painter/CalendarPainter;", "canvasOffset", "", "mGestureDetector", "Landroid/view/GestureDetector;", "monthDateList", "", "monthRectFList", "Landroid/graphics/RectF;", "monthStateY", "", "nViewPager", "Lcom/necer/calendar/NViewPager;", "pagerInitDate", "getPagerInitDate", "()Ljava/time/LocalDate;", "setPagerInitDate", "(Ljava/time/LocalDate;)V", "weekDateList", "weekRectFList", "weekStateY", "drawBgDrawable", "", "canvas", "Landroid/graphics/Canvas;", "drawMonthDate", "drawWeekData", "getCurrPageCheckedDateList", "getCurrPagerDateList", "getFirstLocalDate", "getHoldHeight", "getHoldLine", "getRealRectF", "lineIndex", "columnIndex", "getRectFList", "dateList", "offsetCanvas", "canvasIncreaseOffset", "onDraw", "onTouchEvent", "", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "resetRectFSize", "rectF", "ncalendar_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nNCalendarView.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NCalendarView.kt\ncom/necer/calendar/NCalendarView\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,329:1\n1855#2,2:330\n*S KotlinDebug\n*F\n+ 1 NCalendarView.kt\ncom/necer/calendar/NCalendarView\n*L\n312#1:330,2\n*E\n"})
/* loaded from: classes4.dex */
public final class NCalendarView extends View {

    @NotNull
    private Rect backgroundRect;

    @Nullable
    private Drawable bgDrawable;
    private CalendarPainter calendarPainter;
    private float canvasOffset;
    private GestureDetector mGestureDetector;
    private List<LocalDate> monthDateList;

    @Nullable
    private List<? extends RectF> monthRectFList;
    private int monthStateY;
    private NViewPager nViewPager;
    public LocalDate pagerInitDate;
    private List<LocalDate> weekDateList;

    @Nullable
    private List<? extends RectF> weekRectFList;
    private int weekStateY;

    public NCalendarView(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.canvasOffset = -1.0f;
        this.backgroundRect = new Rect();
    }

    private final void drawBgDrawable(Canvas canvas) {
        if (this.bgDrawable == null) {
            NAttrs nAttrs = NAttrs.INSTANCE;
            this.bgDrawable = nAttrs.getCalendarBackground() != null ? nAttrs.getCalendarBackground() : nAttrs.getShowNumberBackground() ? new TextDrawable() : null;
        }
        if (this.bgDrawable != null) {
            NViewPager nViewPager = this.nViewPager;
            if (nViewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
                nViewPager = null;
            }
            this.backgroundRect.set(0, 0, getWidth(), (int) nViewPager.getCanvasHeight());
            NViewPager nViewPager2 = this.nViewPager;
            if (nViewPager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
                nViewPager2 = null;
            }
            float childY = (nViewPager2.getChildY() - this.weekStateY) / (this.monthStateY - r2);
            if (childY > 1.0f) {
                childY = 1.0f;
            }
            LocalDate localDateNow = LocalDate.now();
            Drawable drawable = this.bgDrawable;
            TextDrawable textDrawable = drawable instanceof TextDrawable ? (TextDrawable) drawable : null;
            if (textDrawable != null) {
                textDrawable.initBoldText();
            }
            Drawable drawable2 = this.bgDrawable;
            TextDrawable textDrawable2 = drawable2 instanceof TextDrawable ? (TextDrawable) drawable2 : null;
            if (textDrawable2 != null) {
                textDrawable2.setText(String.valueOf(localDateNow.getDayOfMonth()));
            }
            Drawable drawable3 = this.bgDrawable;
            if (drawable3 != null) {
                drawable3.setBounds(this.backgroundRect);
            }
            Drawable drawable4 = this.bgDrawable;
            if (drawable4 != null) {
                drawable4.setAlpha((int) (childY * NAttrs.INSTANCE.getBackgroundAlphaColor()));
            }
            Drawable drawable5 = this.bgDrawable;
            if (drawable5 != null) {
                drawable5.draw(canvas);
            }
        }
    }

    private final void drawMonthDate(Canvas canvas) {
        List<LocalDate> list = this.monthDateList;
        if (list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monthDateList");
            list = null;
        }
        int size = list.size() / 7;
        for (int i2 = 0; i2 < size; i2++) {
            for (int i3 = 0; i3 < 7; i3++) {
                RectF realRectF = getRealRectF(i2, i3);
                int i4 = (i2 * 7) + i3;
                NViewPager nViewPager = this.nViewPager;
                if (nViewPager == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
                    nViewPager = null;
                }
                List<LocalDate> totalCheckedDateList = nViewPager.getTotalCheckedDateList();
                List<LocalDate> list2 = this.monthDateList;
                if (list2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("monthDateList");
                    list2 = null;
                }
                LocalDate localDate = list2.get(i4);
                NViewPager nViewPager2 = this.nViewPager;
                if (nViewPager2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
                    nViewPager2 = null;
                }
                if (nViewPager2.isAvailable(localDate)) {
                    NDateUtil nDateUtil = NDateUtil.INSTANCE;
                    if (nDateUtil.isLastMonth(getPagerInitDate(), localDate) || nDateUtil.isNextMonth(getPagerInitDate(), localDate)) {
                        CalendarPainter calendarPainter = this.calendarPainter;
                        if (calendarPainter == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("calendarPainter");
                            calendarPainter = null;
                        }
                        calendarPainter.onDrawLastOrNextMonth(canvas, realRectF, localDate, totalCheckedDateList);
                    } else if (nDateUtil.isToday(localDate)) {
                        CalendarPainter calendarPainter2 = this.calendarPainter;
                        if (calendarPainter2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("calendarPainter");
                            calendarPainter2 = null;
                        }
                        calendarPainter2.onDrawToday(canvas, realRectF, localDate, totalCheckedDateList);
                    } else {
                        CalendarPainter calendarPainter3 = this.calendarPainter;
                        if (calendarPainter3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("calendarPainter");
                            calendarPainter3 = null;
                        }
                        calendarPainter3.onDrawCurrentMonthOrWeek(canvas, realRectF, localDate, totalCheckedDateList);
                    }
                } else {
                    CalendarPainter calendarPainter4 = this.calendarPainter;
                    if (calendarPainter4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("calendarPainter");
                        calendarPainter4 = null;
                    }
                    calendarPainter4.onDrawDisableDate(canvas, realRectF, localDate);
                }
            }
        }
    }

    private final void drawWeekData(Canvas canvas) {
        for (int i2 = 0; i2 < 7; i2++) {
            RectF realRectF = getRealRectF(0, i2);
            int i3 = i2 + 0;
            NViewPager nViewPager = this.nViewPager;
            CalendarPainter calendarPainter = null;
            if (nViewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
                nViewPager = null;
            }
            List<LocalDate> totalCheckedDateList = nViewPager.getTotalCheckedDateList();
            List<LocalDate> list = this.weekDateList;
            if (list == null) {
                Intrinsics.throwUninitializedPropertyAccessException("weekDateList");
                list = null;
            }
            LocalDate localDate = list.get(i3);
            NViewPager nViewPager2 = this.nViewPager;
            if (nViewPager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
                nViewPager2 = null;
            }
            if (!nViewPager2.isAvailable(localDate)) {
                CalendarPainter calendarPainter2 = this.calendarPainter;
                if (calendarPainter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("calendarPainter");
                } else {
                    calendarPainter = calendarPainter2;
                }
                calendarPainter.onDrawDisableDate(canvas, realRectF, localDate);
            } else if (NDateUtil.INSTANCE.isToday(localDate)) {
                CalendarPainter calendarPainter3 = this.calendarPainter;
                if (calendarPainter3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("calendarPainter");
                } else {
                    calendarPainter = calendarPainter3;
                }
                calendarPainter.onDrawToday(canvas, realRectF, localDate, totalCheckedDateList);
            } else {
                CalendarPainter calendarPainter4 = this.calendarPainter;
                if (calendarPainter4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("calendarPainter");
                } else {
                    calendarPainter = calendarPainter4;
                }
                calendarPainter.onDrawCurrentMonthOrWeek(canvas, realRectF, localDate, totalCheckedDateList);
            }
        }
    }

    private final int getHoldLine() {
        NViewPager nViewPager = this.nViewPager;
        List<LocalDate> list = null;
        if (nViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
            nViewPager = null;
        }
        LocalDate fulcrumDate = nViewPager.getFulcrumDate();
        List<LocalDate> list2 = this.monthDateList;
        if (list2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monthDateList");
        } else {
            list = list2;
        }
        return list.indexOf(fulcrumDate) / 7;
    }

    private final RectF getRealRectF(int lineIndex, int columnIndex) {
        int i2 = (lineIndex * 7) + columnIndex;
        NViewPager nViewPager = this.nViewPager;
        List<LocalDate> list = null;
        if (nViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
            nViewPager = null;
        }
        if (nViewPager.getCalendarState() == CalendarState.WEEK) {
            if (this.weekRectFList == null) {
                List<LocalDate> list2 = this.weekDateList;
                if (list2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("weekDateList");
                } else {
                    list = list2;
                }
                this.weekRectFList = getRectFList(list);
            }
            List<? extends RectF> list3 = this.weekRectFList;
            Intrinsics.checkNotNull(list3);
            return list3.get(i2);
        }
        if (this.monthRectFList == null) {
            List<LocalDate> list4 = this.monthDateList;
            if (list4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("monthDateList");
                list4 = null;
            }
            this.monthRectFList = getRectFList(list4);
        }
        NViewPager nViewPager2 = this.nViewPager;
        if (nViewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
            nViewPager2 = null;
        }
        float canvasHeight = nViewPager2.getCanvasHeight();
        NViewPager nViewPager3 = this.nViewPager;
        if (nViewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
            nViewPager3 = null;
        }
        if (canvasHeight <= nViewPager3.getMonthHeight()) {
            List<? extends RectF> list5 = this.monthRectFList;
            Intrinsics.checkNotNull(list5);
            return list5.get(i2);
        }
        List<? extends RectF> list6 = this.monthRectFList;
        Intrinsics.checkNotNull(list6);
        RectF rectF = list6.get(i2);
        List<LocalDate> list7 = this.monthDateList;
        if (list7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monthDateList");
        } else {
            list = list7;
        }
        return resetRectFSize(rectF, lineIndex, columnIndex, list);
    }

    private final List<RectF> getRectFList(List<LocalDate> dateList) {
        ArrayList arrayList = new ArrayList();
        int size = dateList.size() / 7;
        for (int i2 = 0; i2 < size; i2++) {
            for (int i3 = 0; i3 < 7; i3++) {
                arrayList.add(resetRectFSize(new RectF(), i2, i3, dateList));
            }
        }
        return arrayList;
    }

    private final RectF resetRectFSize(RectF rectF, int lineIndex, int columnIndex, List<LocalDate> dateList) {
        int size = dateList.size() / 7;
        NViewPager nViewPager = this.nViewPager;
        if (nViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
            nViewPager = null;
        }
        float canvasHeight = nViewPager.getCanvasHeight();
        if (size == 1 || size == 5) {
            float f2 = canvasHeight / 5;
            float f3 = lineIndex * f2;
            rectF.set((getWidth() * columnIndex) / 7, f3, ((columnIndex * getWidth()) / 7) + (getWidth() / 7), f2 + f3);
        } else if (size == 6) {
            float f4 = 5;
            float f5 = canvasHeight / f4;
            float f6 = (4 * f5) / f4;
            float f7 = lineIndex * f6;
            float f8 = (f5 - f6) / 2;
            rectF.set((getWidth() * columnIndex) / 7, f7 + f8, ((columnIndex * getWidth()) / 7) + (getWidth() / 7), f7 + f6 + f8);
        }
        return rectF;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0038 A[PHI: r2
      0x0038: PHI (r2v4 java.util.List<java.time.LocalDate>) = (r2v2 java.util.List<java.time.LocalDate>), (r2v6 java.util.List<java.time.LocalDate>) binds: [B:14:0x0030, B:11:0x0026] A[DONT_GENERATE, DONT_INLINE]] */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List<java.time.LocalDate> getCurrPageCheckedDateList() {
        /*
            r5 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            com.necer.calendar.NViewPager r1 = r5.nViewPager
            java.lang.String r2 = "nViewPager"
            r3 = 0
            if (r1 != 0) goto L10
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            r1 = r3
        L10:
            java.util.List r1 = r1.getTotalCheckedDateList()
            com.necer.calendar.NViewPager r4 = r5.nViewPager
            if (r4 != 0) goto L1c
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            r4 = r3
        L1c:
            com.necer.enumeration.CalendarState r2 = r4.getCalendarState()
            com.necer.enumeration.CalendarState r4 = com.necer.enumeration.CalendarState.WEEK
            if (r2 != r4) goto L2e
            java.util.List<java.time.LocalDate> r2 = r5.weekDateList
            if (r2 != 0) goto L38
            java.lang.String r2 = "weekDateList"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            goto L39
        L2e:
            java.util.List<java.time.LocalDate> r2 = r5.monthDateList
            if (r2 != 0) goto L38
            java.lang.String r2 = "monthDateList"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            goto L39
        L38:
            r3 = r2
        L39:
            java.lang.Iterable r3 = (java.lang.Iterable) r3
            java.util.Iterator r2 = r3.iterator()
        L3f:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L55
            java.lang.Object r3 = r2.next()
            java.time.LocalDate r3 = (java.time.LocalDate) r3
            boolean r4 = r1.contains(r3)
            if (r4 == 0) goto L3f
            r0.add(r3)
            goto L3f
        L55:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.necer.calendar.NCalendarView.getCurrPageCheckedDateList():java.util.List");
    }

    @NotNull
    public final List<LocalDate> getCurrPagerDateList() {
        List<LocalDate> list;
        NViewPager nViewPager = this.nViewPager;
        if (nViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
            nViewPager = null;
        }
        if (nViewPager.getCalendarState() == CalendarState.WEEK) {
            list = this.weekDateList;
            if (list == null) {
                Intrinsics.throwUninitializedPropertyAccessException("weekDateList");
                return null;
            }
        } else {
            list = this.monthDateList;
            if (list == null) {
                Intrinsics.throwUninitializedPropertyAccessException("monthDateList");
                return null;
            }
        }
        return list;
    }

    @NotNull
    public final LocalDate getFirstLocalDate() {
        NViewPager nViewPager = this.nViewPager;
        List<LocalDate> list = null;
        if (nViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
            nViewPager = null;
        }
        if (nViewPager.getCalendarState() != CalendarState.WEEK) {
            LocalDate localDateOf = LocalDate.of(getPagerInitDate().getYear(), getPagerInitDate().getMonthValue(), 1);
            Intrinsics.checkNotNullExpressionValue(localDateOf, "{\n            LocalDate.….monthValue, 1)\n        }");
            return localDateOf;
        }
        List<LocalDate> list2 = this.weekDateList;
        if (list2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("weekDateList");
        } else {
            list = list2;
        }
        return list.get(0);
    }

    public final float getHoldHeight() {
        float f2;
        int holdLine;
        NViewPager nViewPager = this.nViewPager;
        List<LocalDate> list = null;
        if (nViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
            nViewPager = null;
        }
        float monthHeight = nViewPager.getMonthHeight();
        List<LocalDate> list2 = this.monthDateList;
        if (list2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monthDateList");
        } else {
            list = list2;
        }
        if (list.size() / 7 == 5) {
            f2 = monthHeight / 5;
            holdLine = getHoldLine();
        } else {
            float f3 = 5;
            f2 = ((monthHeight / f3) * 4) / f3;
            holdLine = getHoldLine();
        }
        return holdLine * f2;
    }

    @NotNull
    public final LocalDate getPagerInitDate() {
        LocalDate localDate = this.pagerInitDate;
        if (localDate != null) {
            return localDate;
        }
        Intrinsics.throwUninitializedPropertyAccessException("pagerInitDate");
        return null;
    }

    public final void offsetCanvas(float canvasIncreaseOffset) {
        NViewPager nViewPager = this.nViewPager;
        NViewPager nViewPager2 = null;
        if (nViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
            nViewPager = null;
        }
        int iRound = Math.round(nViewPager.getChildY());
        if ((this.canvasOffset == -1.0f) && canvasIncreaseOffset < 0.0f && iRound >= this.weekStateY && iRound < this.monthStateY) {
            this.canvasOffset = getHoldHeight();
        }
        NViewPager nViewPager3 = this.nViewPager;
        if (nViewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
            nViewPager3 = null;
        }
        float canvasHeight = nViewPager3.getCanvasHeight();
        NViewPager nViewPager4 = this.nViewPager;
        if (nViewPager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
        } else {
            nViewPager2 = nViewPager4;
        }
        if (canvasHeight == ((float) nViewPager2.getMonthHeight())) {
            float f2 = this.canvasOffset + canvasIncreaseOffset;
            this.canvasOffset = f2;
            if (f2 < 0.0f) {
                this.canvasOffset = 0.0f;
            } else if (f2 > getHoldHeight()) {
                this.canvasOffset = getHoldHeight();
            }
        } else {
            this.canvasOffset = 0.0f;
        }
        invalidate();
    }

    @Override // android.view.View
    public void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        NViewPager nViewPager = null;
        if (this.monthStateY == 0) {
            NViewPager nViewPager2 = this.nViewPager;
            if (nViewPager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
                nViewPager2 = null;
            }
            this.monthStateY = nViewPager2.getMonthStateY();
            NViewPager nViewPager3 = this.nViewPager;
            if (nViewPager3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
                nViewPager3 = null;
            }
            this.weekStateY = nViewPager3.getWeekStateY();
        }
        NViewPager nViewPager4 = this.nViewPager;
        if (nViewPager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
        } else {
            nViewPager = nViewPager4;
        }
        if (nViewPager.getCalendarState() == CalendarState.WEEK) {
            drawWeekData(canvas);
            return;
        }
        canvas.translate(0.0f, -this.canvasOffset);
        drawBgDrawable(canvas);
        drawMonthDate(canvas);
    }

    @Override // android.view.View
    public boolean onTouchEvent(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        GestureDetector gestureDetector = this.mGestureDetector;
        if (gestureDetector == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mGestureDetector");
            gestureDetector = null;
        }
        return gestureDetector.onTouchEvent(event);
    }

    public final void setPagerInitDate(@NotNull LocalDate localDate) {
        Intrinsics.checkNotNullParameter(localDate, "<set-?>");
        this.pagerInitDate = localDate;
    }

    public NCalendarView(@Nullable Context context) {
        this(context, null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NCalendarView(@Nullable Context context, @NotNull ViewGroup viewGroup, @NotNull LocalDate localDate) {
        this(context, null);
        Intrinsics.checkNotNullParameter(viewGroup, "viewGroup");
        Intrinsics.checkNotNullParameter(localDate, "localDate");
        NViewPager nViewPager = null;
        setPagerInitDate(localDate);
        this.nViewPager = (NViewPager) viewGroup;
        NDateUtil nDateUtil = NDateUtil.INSTANCE;
        LocalDate pagerInitDate = getPagerInitDate();
        NAttrs nAttrs = NAttrs.INSTANCE;
        this.monthDateList = nDateUtil.getMonthDate(pagerInitDate, nAttrs.getFirstDayOfWeek(), nAttrs.getAllMonthSixLine());
        this.weekDateList = nDateUtil.getWeekDate(getPagerInitDate(), nAttrs.getFirstDayOfWeek());
        NViewPager nViewPager2 = this.nViewPager;
        if (nViewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
        } else {
            nViewPager = nViewPager2;
        }
        this.calendarPainter = nViewPager.getCalendarPainter();
        this.mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.necer.calendar.NCalendarView.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(@NotNull MotionEvent e2) {
                Intrinsics.checkNotNullParameter(e2, "e");
                NViewPager nViewPager3 = NCalendarView.this.nViewPager;
                if (nViewPager3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
                    nViewPager3 = null;
                }
                return nViewPager3.getCalendarState() != null;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(@NotNull MotionEvent e2) throws Resources.NotFoundException {
                List list;
                List list2;
                Intrinsics.checkNotNullParameter(e2, "e");
                NViewPager nViewPager3 = NCalendarView.this.nViewPager;
                NViewPager nViewPager4 = null;
                if (nViewPager3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
                    nViewPager3 = null;
                }
                if (nViewPager3.getCalendarState() == CalendarState.WEEK) {
                    list = NCalendarView.this.weekRectFList;
                    Intrinsics.checkNotNull(list);
                    list2 = NCalendarView.this.weekDateList;
                    if (list2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("weekDateList");
                        list2 = null;
                    }
                } else {
                    list = NCalendarView.this.monthRectFList;
                    Intrinsics.checkNotNull(list);
                    list2 = NCalendarView.this.monthDateList;
                    if (list2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("monthDateList");
                        list2 = null;
                    }
                }
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (((RectF) list.get(i2)).contains(e2.getX(), e2.getY())) {
                        LocalDate localDate2 = (LocalDate) list2.get(i2);
                        NViewPager nViewPager5 = NCalendarView.this.nViewPager;
                        if (nViewPager5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("nViewPager");
                        } else {
                            nViewPager4 = nViewPager5;
                        }
                        nViewPager4.setClickDate(localDate2);
                        return true;
                    }
                }
                return true;
            }
        });
    }
}
