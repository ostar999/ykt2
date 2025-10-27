package com.necer.calendar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import androidx.core.view.NestedScrollingParent;
import com.luck.picture.lib.config.CustomIntentKey;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.necer.R;
import com.necer.enumeration.CalendarState;
import com.necer.enumeration.CheckModel;
import com.necer.enumeration.MultipleCountModel;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.listener.OnCalendarMultipleChangedListener;
import com.necer.listener.OnCalendarStateChangedListener;
import com.necer.listener.OnClickDisableDateListener;
import com.necer.listener.OnEndAnimatorListener;
import com.necer.painter.CalendarPainter;
import com.necer.utils.NAttrs;
import com.necer.utils.NAttrsUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000Ì\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0015\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010-\u001a\u00020'2\u0006\u0010.\u001a\u00020/H\u0002J\u0010\u00100\u001a\u00020'2\u0006\u00101\u001a\u00020\u000eH\u0002J\u0010\u00102\u001a\u00020'2\u0006\u00101\u001a\u00020\u000eH\u0002J\b\u00103\u001a\u000204H\u0016J\n\u00105\u001a\u0004\u0018\u00010\u0015H\u0016J\u0006\u00106\u001a\u00020\u000eJ\b\u00107\u001a\u000208H\u0016J\u0006\u00109\u001a\u00020\u000eJ\u000e\u0010:\u001a\b\u0012\u0004\u0012\u00020<0;H\u0016J\u000e\u0010=\u001a\b\u0012\u0004\u0012\u00020<0;H\u0016J\u0006\u0010>\u001a\u00020\fJ\u0012\u0010?\u001a\u0004\u0018\u00010\u00152\u0006\u0010@\u001a\u00020\u000eH\u0002J\u000e\u0010A\u001a\b\u0012\u0004\u0012\u00020<0;H\u0016J\u0006\u0010B\u001a\u00020\fJ\u0010\u0010C\u001a\u00020\u00112\u0006\u0010D\u001a\u00020\u000eH\u0002J\b\u0010E\u001a\u00020\u0011H\u0002J \u0010F\u001a\u00020'2\u0006\u0010G\u001a\u00020\f2\u0006\u0010H\u001a\u00020\f2\u0006\u0010I\u001a\u00020\fH\u0016J\u0012\u0010F\u001a\u00020'2\b\u0010J\u001a\u0004\u0018\u00010KH\u0016J\u0018\u0010L\u001a\u00020'2\u0006\u0010G\u001a\u00020\f2\u0006\u0010H\u001a\u00020\fH\u0016J\b\u0010M\u001a\u00020'H\u0016J\b\u0010N\u001a\u00020'H\u0014J\u0010\u0010O\u001a\u00020\u00112\u0006\u0010P\u001a\u00020QH\u0016J0\u0010R\u001a\u00020'2\u0006\u0010S\u001a\u00020\u00112\u0006\u0010T\u001a\u00020\f2\u0006\u0010U\u001a\u00020\f2\u0006\u0010V\u001a\u00020\f2\u0006\u0010W\u001a\u00020\fH\u0014J\u0018\u0010X\u001a\u00020'2\u0006\u0010Y\u001a\u00020\f2\u0006\u0010Z\u001a\u00020\fH\u0014J \u0010[\u001a\u00020\u00112\u0006\u0010\\\u001a\u00020$2\u0006\u0010]\u001a\u00020\u000e2\u0006\u0010^\u001a\u00020\u000eH\u0016J(\u0010_\u001a\u00020'2\u0006\u0010\\\u001a\u00020$2\u0006\u0010`\u001a\u00020\f2\u0006\u00101\u001a\u00020\f2\u0006\u0010a\u001a\u00020bH\u0016J \u0010c\u001a\u00020\u00112\u0006\u0010d\u001a\u00020$2\u0006\u0010\\\u001a\u00020$2\u0006\u0010e\u001a\u00020\fH\u0016J\u0010\u0010f\u001a\u00020'2\u0006\u0010d\u001a\u00020$H\u0016J\u0010\u0010g\u001a\u00020\u00112\u0006\u0010h\u001a\u00020QH\u0016J\u0012\u0010i\u001a\u00020'2\b\u0010j\u001a\u0004\u0018\u000104H\u0016J\u0014\u0010k\u001a\u00020'2\f\u0010l\u001a\b\u0012\u0004\u0012\u00020'0&J\u0010\u0010m\u001a\u00020'2\u0006\u0010n\u001a\u000208H\u0016J\u0016\u0010o\u001a\u00020'2\f\u0010p\u001a\b\u0012\u0004\u0012\u00020K0;H\u0016J\u001c\u0010q\u001a\u00020'2\b\u0010r\u001a\u0004\u0018\u00010K2\b\u0010s\u001a\u0004\u0018\u00010KH\u0016J&\u0010q\u001a\u00020'2\b\u0010r\u001a\u0004\u0018\u00010K2\b\u0010s\u001a\u0004\u0018\u00010K2\b\u0010t\u001a\u0004\u0018\u00010KH\u0016J\u0012\u0010u\u001a\u00020'2\b\u0010t\u001a\u0004\u0018\u00010KH\u0016J\u001a\u0010v\u001a\u00020'2\u0006\u0010w\u001a\u00020\f2\b\u0010x\u001a\u0004\u0018\u00010yH\u0016J\u0012\u0010z\u001a\u00020'2\b\u0010{\u001a\u0004\u0018\u00010|H\u0016J\u0012\u0010}\u001a\u00020'2\b\u0010~\u001a\u0004\u0018\u00010\u007fH\u0016J\u0013\u0010\u0080\u0001\u001a\u00020'2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u0015\u0010\u0081\u0001\u001a\u00020'2\n\u0010\u0082\u0001\u001a\u0005\u0018\u00010\u0083\u0001H\u0016J\u000f\u0010\u0084\u0001\u001a\u00020'2\u0006\u0010 \u001a\u00020\u0011J\u0011\u0010\u0085\u0001\u001a\u00020'2\u0006\u0010\u0013\u001a\u00020\u0011H\u0016J#\u0010\u0086\u0001\u001a\u00020'2\u0007\u0010\u0087\u0001\u001a\u00020\u000e2\u0007\u0010\u0088\u0001\u001a\u00020\u000e2\u0006\u0010.\u001a\u00020/H\u0002J\t\u0010\u0089\u0001\u001a\u00020'H\u0016J\t\u0010\u008a\u0001\u001a\u00020'H\u0016J\t\u0010\u008b\u0001\u001a\u00020'H\u0016J\t\u0010\u008c\u0001\u001a\u00020'H\u0016J\t\u0010\u008d\u0001\u001a\u00020'H\u0016J\t\u0010\u008e\u0001\u001a\u00020'H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020'0&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u000eX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u008f\u0001"}, d2 = {"Lcom/necer/calendar/NCalendar;", "Landroid/widget/LinearLayout;", "Lcom/necer/calendar/ICalendar;", "Landroidx/core/view/NestedScrollingParent;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "childView", "Landroid/widget/FrameLayout;", "directionY", "", "downX", "", "downY", "isFirstMove", "", "isLayout", "isWeekHoldEnable", "lastCalendarState", "Lcom/necer/enumeration/CalendarState;", "lastY", "monthHeight", "monthStateY", "nViewPager", "Lcom/necer/calendar/NViewPager;", "nWeekBar", "Lcom/necer/calendar/NWeekBar;", "onCalendarStateChangedListener", "Lcom/necer/listener/OnCalendarStateChangedListener;", "previousValue", "shouldChangeToWeek", "stretchHeight", "stretchStateY", "targetView", "Landroid/view/View;", "touchBack", "Lkotlin/Function0;", "", "valueAnimator", "Landroid/animation/ValueAnimator;", "verticalY", "weekHeight", "weekStateY", "autoScroll", "duration", "", "calendarTranslate", "dy", "gestureMove", "getCalendarPainter", "Lcom/necer/painter/CalendarPainter;", "getCalendarState", "getCanvasHeight", "getCheckModel", "Lcom/necer/enumeration/CheckModel;", "getChildY", "getCurrPagerCheckDateList", "", "Ljava/time/LocalDate;", "getCurrPagerDateList", "getMonthStateY", "getPreCalendarState", "childY", "getTotalCheckedDateList", "getWeekStateY", "interceptTouch", CustomIntentKey.EXTRA_OFFSET_Y, "isInCalendar", "jumpDate", "year", "month", "day", "formatDate", "", "jumpMonth", "notifyCalendar", "onFinishInflate", "onInterceptTouchEvent", "ev", "Landroid/view/MotionEvent;", "onLayout", "changed", NotifyType.LIGHTS, "t", "r", "b", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "onNestedPreFling", TypedValues.AttributesType.S_TARGET, "velocityX", "velocityY", "onNestedPreScroll", "dx", "consumed", "", "onStartNestedScroll", "child", "nestedScrollAxes", "onStopNestedScroll", "onTouchEvent", NotificationCompat.CATEGORY_EVENT, "setCalendarPainter", "calendarPainter", "setCallBack", "back", "setCheckMode", "checkModel", "setCheckedDates", "dateList", "setDateInterval", "startFormatDate", "endFormatDate", "formatInitializeDate", "setInitializeDate", "setMultipleCount", "multipleCount", "multipleCountModel", "Lcom/necer/enumeration/MultipleCountModel;", "setOnCalendarChangedListener", "onCalendarChangedListener", "Lcom/necer/listener/OnCalendarChangedListener;", "setOnCalendarMultipleChangedListener", "onCalendarMultipleChangedListener", "Lcom/necer/listener/OnCalendarMultipleChangedListener;", "setOnCalendarStateChangedListener", "setOnClickDisableDateListener", "onClickDisableDateListener", "Lcom/necer/listener/OnClickDisableDateListener;", "setShouldChangeToWeek", "setWeekHoldEnable", "startAutoScroll", "startY", "endY", "toLastPager", "toMonth", "toNextPager", "toStretch", "toToday", "toWeek", "ncalendar_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class NCalendar extends LinearLayout implements ICalendar, NestedScrollingParent {

    @NotNull
    private FrameLayout childView;
    private int directionY;
    private float downX;
    private float downY;
    private boolean isFirstMove;
    private boolean isLayout;
    private boolean isWeekHoldEnable;

    @Nullable
    private CalendarState lastCalendarState;
    private float lastY;
    private int monthHeight;
    private int monthStateY;

    @NotNull
    private NViewPager nViewPager;

    @NotNull
    private NWeekBar nWeekBar;

    @Nullable
    private OnCalendarStateChangedListener onCalendarStateChangedListener;
    private float previousValue;
    private boolean shouldChangeToWeek;
    private int stretchHeight;
    private int stretchStateY;

    @Nullable
    private View targetView;

    @NotNull
    private Function0<Unit> touchBack;

    @NotNull
    private ValueAnimator valueAnimator;
    private final float verticalY;
    private int weekHeight;
    private int weekStateY;

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CalendarState.values().length];
            try {
                iArr[CalendarState.WEEK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CalendarState.MONTH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CalendarState.MONTH_STRETCH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NCalendar(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.valueAnimator = new ValueAnimator();
        setOrientation(1);
        setMotionEventSplittingEnabled(false);
        NAttrsUtil.INSTANCE.setAttrs(context, attributeSet);
        NAttrs nAttrs = NAttrs.INSTANCE;
        int calendarHeight = nAttrs.getCalendarHeight();
        this.monthHeight = calendarHeight;
        this.weekHeight = calendarHeight / 5;
        int stretchCalendarHeight = nAttrs.getStretchCalendarHeight();
        this.stretchHeight = stretchCalendarHeight;
        if (stretchCalendarHeight <= this.monthHeight) {
            throw new RuntimeException("拉伸状态的高度必须大于月日历高度");
        }
        this.nWeekBar = new NWeekBar(context, null);
        this.nViewPager = new NViewPager(context, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, nAttrs.getWeekBarHeight() == 0 ? -2 : nAttrs.getWeekBarHeight());
        layoutParams.setMargins((int) nAttrs.getPaddingHorizontalCalendar(), 0, (int) nAttrs.getPaddingHorizontalCalendar(), 0);
        addView(this.nWeekBar, layoutParams);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, this.stretchHeight);
        layoutParams2.setMargins((int) nAttrs.getPaddingHorizontalCalendar(), 0, (int) nAttrs.getPaddingHorizontalCalendar(), 0);
        addView(this.nViewPager, layoutParams2);
        FrameLayout frameLayout = new FrameLayout(context);
        this.childView = frameLayout;
        frameLayout.setClickable(true);
        this.childView.setBackgroundColor(-1);
        addView(this.childView, new LinearLayout.LayoutParams(-1, -1));
        this.verticalY = 30.0f;
        this.isFirstMove = true;
        this.touchBack = new Function0<Unit>() { // from class: com.necer.calendar.NCalendar$touchBack$1
            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003d A[PHI: r3
      0x003d: PHI (r3v5 int) = (r3v2 int), (r3v3 int), (r3v8 int), (r3v9 int) binds: [B:25:0x0064, B:22:0x0055, B:15:0x003b, B:12:0x002b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void autoScroll(long r6) {
        /*
            r5 = this;
            android.animation.ValueAnimator r0 = r5.valueAnimator
            boolean r0 = r0.isRunning()
            if (r0 == 0) goto L9
            return
        L9:
            android.widget.FrameLayout r0 = r5.childView
            float r0 = r0.getY()
            int r1 = r5.directionY
            r2 = 1
            if (r1 != r2) goto L3f
            int r1 = r5.weekStateY
            float r3 = (float) r1
            int r3 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r3 < 0) goto L2e
            int r3 = r5.monthStateY
            float r4 = (float) r3
            int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r4 > 0) goto L2e
            int r4 = r5.weekHeight
            int r4 = r4 / 2
            int r4 = r3 - r4
            float r4 = (float) r4
            int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r4 >= 0) goto L3d
            goto L67
        L2e:
            int r1 = r5.stretchStateY
            int r3 = r5.monthStateY
            int r4 = r1 - r3
            int r4 = r4 / 2
            int r4 = r1 - r4
            float r4 = (float) r4
            int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r4 >= 0) goto L67
        L3d:
            r1 = r3
            goto L67
        L3f:
            int r1 = r5.weekStateY
            float r3 = (float) r1
            int r3 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r3 < 0) goto L58
            int r3 = r5.monthStateY
            float r4 = (float) r3
            int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r4 > 0) goto L58
            int r4 = r5.weekHeight
            int r4 = r4 / 2
            int r4 = r4 + r1
            float r4 = (float) r4
            int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r4 <= 0) goto L67
            goto L3d
        L58:
            int r1 = r5.monthStateY
            int r3 = r5.stretchStateY
            int r4 = r3 - r1
            int r4 = r4 / 2
            int r4 = r4 + r1
            float r4 = (float) r4
            int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r4 <= 0) goto L67
            goto L3d
        L67:
            float r1 = (float) r1
            com.necer.enumeration.CalendarState r3 = r5.getPreCalendarState(r1)
            int r4 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r4 != 0) goto L71
            goto L72
        L71:
            r2 = 0
        L72:
            if (r2 == 0) goto L79
            com.necer.enumeration.CalendarState r2 = r5.lastCalendarState
            if (r2 != r3) goto L79
            return
        L79:
            r5.startAutoScroll(r0, r1, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.necer.calendar.NCalendar.autoScroll(long):void");
    }

    private final void calendarTranslate(float dy) {
        if (Math.round(dy) == 0) {
            return;
        }
        this.nViewPager.offsetView(dy * (this.nViewPager.getHoldHeight() / (this.monthHeight - this.weekHeight)));
    }

    private final void gestureMove(float dy) throws Resources.NotFoundException {
        if (this.valueAnimator.isRunning()) {
            this.valueAnimator.cancel();
        }
        if (Math.round(this.childView.getY()) == this.weekStateY && this.isWeekHoldEnable) {
            return;
        }
        float y2 = this.childView.getY() - dy;
        int i2 = NAttrs.INSTANCE.getStretchCalendarEnable() ? this.stretchStateY : this.monthStateY;
        int i3 = this.weekStateY;
        if (y2 <= i3) {
            y2 = i3;
        } else {
            float f2 = i2;
            if (y2 >= f2) {
                y2 = f2;
            }
        }
        this.childView.setY(y2);
        calendarTranslate(dy);
        CalendarState preCalendarState = getPreCalendarState(this.childView.getY());
        if (preCalendarState != null) {
            this.nViewPager.setCalendarState(preCalendarState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CalendarState getPreCalendarState(float childY) {
        int iRound = Math.round(childY);
        if (iRound == this.weekStateY) {
            return CalendarState.WEEK;
        }
        if (iRound == this.monthStateY) {
            return CalendarState.MONTH;
        }
        if (iRound == this.stretchStateY) {
            return CalendarState.MONTH_STRETCH;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x000a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean interceptTouch(float r5) {
        /*
            r4 = this;
            float r5 = java.lang.Math.abs(r5)
            android.view.View r0 = r4.targetView
            r1 = 1
            r2 = 0
            if (r0 != 0) goto Lc
        La:
            r0 = r1
            goto L20
        Lc:
            boolean r0 = r4.isInCalendar()
            if (r0 == 0) goto L1f
            android.view.View r0 = r4.targetView
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            r3 = -1
            boolean r0 = r0.canScrollVertically(r3)
            if (r0 != 0) goto L1f
            goto La
        L1f:
            r0 = r2
        L20:
            if (r0 == 0) goto L29
            float r0 = r4.verticalY
            int r5 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r5 <= 0) goto L29
            goto L2a
        L29:
            r1 = r2
        L2a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.necer.calendar.NCalendar.interceptTouch(float):boolean");
    }

    private final boolean isInCalendar() {
        CalendarState calendarState = this.nViewPager.getCalendarState();
        return calendarState == CalendarState.MONTH ? this.downY <= ((float) this.monthStateY) : calendarState == CalendarState.WEEK ? this.downY <= ((float) this.weekStateY) : calendarState == CalendarState.MONTH_STRETCH && this.downY <= ((float) this.stretchStateY);
    }

    private final void startAutoScroll(float startY, float endY, long duration) {
        ValueAnimator valueAnimator = new ValueAnimator();
        this.valueAnimator = valueAnimator;
        valueAnimator.setDuration(duration);
        this.valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.necer.calendar.a
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                NCalendar.startAutoScroll$lambda$0(this.f10655c, valueAnimator2);
            }
        });
        this.valueAnimator.addListener(new OnEndAnimatorListener() { // from class: com.necer.calendar.NCalendar.startAutoScroll.2
            @Override // com.necer.listener.OnEndAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(@NotNull Animator animation) throws Resources.NotFoundException {
                Intrinsics.checkNotNullParameter(animation, "animation");
                super.onAnimationEnd(animation);
                NCalendar.this.previousValue = 0.0f;
                NCalendar nCalendar = NCalendar.this;
                CalendarState preCalendarState = nCalendar.getPreCalendarState(nCalendar.childView.getY());
                if (preCalendarState != null) {
                    NCalendar.this.nViewPager.setCalendarState(preCalendarState);
                    OnCalendarStateChangedListener onCalendarStateChangedListener = NCalendar.this.onCalendarStateChangedListener;
                    if (onCalendarStateChangedListener != null) {
                        onCalendarStateChangedListener.onCalendarStateChange(preCalendarState);
                    }
                    NCalendar.this.lastCalendarState = preCalendarState;
                }
            }
        });
        this.valueAnimator.setFloatValues(startY, endY);
        this.valueAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startAutoScroll$lambda$0(NCalendar this$0, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(animation, "animation");
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float fFloatValue = ((Float) animatedValue).floatValue();
        float f2 = this$0.previousValue;
        if (!(f2 == 0.0f)) {
            float f3 = f2 - fFloatValue;
            this$0.calendarTranslate(f3);
            FrameLayout frameLayout = this$0.childView;
            frameLayout.setY(frameLayout.getY() - f3);
        }
        this$0.previousValue = fFloatValue;
    }

    @Override // com.necer.calendar.ICalendar
    @NotNull
    public CalendarPainter getCalendarPainter() {
        return this.nViewPager.getCalendarPainter();
    }

    @Override // com.necer.calendar.ICalendar
    @Nullable
    public CalendarState getCalendarState() {
        return this.nViewPager.getCalendarState();
    }

    public final float getCanvasHeight() {
        return (this.childView.getChildCount() == 0 || this.childView.getY() <= ((float) this.monthStateY)) ? this.monthHeight : this.childView.getY() - this.nWeekBar.getMeasuredHeight();
    }

    @Override // com.necer.calendar.ICalendar
    @NotNull
    public CheckModel getCheckModel() {
        return this.nViewPager.getCheckMode();
    }

    public final float getChildY() {
        return this.childView.getY();
    }

    @Override // com.necer.calendar.ICalendar
    @NotNull
    public List<LocalDate> getCurrPagerCheckDateList() {
        return this.nViewPager.getCurrPagerCheckedDateList();
    }

    @Override // com.necer.calendar.ICalendar
    @NotNull
    public List<LocalDate> getCurrPagerDateList() {
        return this.nViewPager.getCurrPagerDateList();
    }

    public final int getMonthStateY() {
        return this.monthStateY;
    }

    @Override // com.necer.calendar.ICalendar
    @NotNull
    public List<LocalDate> getTotalCheckedDateList() {
        return this.nViewPager.getTotalCheckedDateList();
    }

    public final int getWeekStateY() {
        return this.weekStateY;
    }

    @Override // com.necer.calendar.ICalendar
    public void jumpDate(@Nullable String formatDate) {
        LocalDate localDate = LocalDate.parse(formatDate, DateTimeFormatter.ofPattern("yyyy-M-d"));
        NViewPager nViewPager = this.nViewPager;
        Intrinsics.checkNotNullExpressionValue(localDate, "localDate");
        nViewPager.jumpDate(localDate, true);
    }

    @Override // com.necer.calendar.ICalendar
    public void jumpMonth(int year, int month) throws Resources.NotFoundException {
        LocalDate localDate = LocalDate.of(year, month, 1);
        NViewPager nViewPager = this.nViewPager;
        Intrinsics.checkNotNullExpressionValue(localDate, "localDate");
        nViewPager.jumpDate(localDate, false);
    }

    @Override // com.necer.calendar.ICalendar
    public void notifyCalendar() {
        this.nViewPager.notifyAllView();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 4) {
            throw new RuntimeException(getContext().getString(R.string.N_NCalendar_child_num));
        }
        if (getChildCount() == 4) {
            View childAt = getChildAt(3);
            ViewParent parent = childAt.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type android.view.ViewGroup");
            ((ViewGroup) parent).removeView(childAt);
            this.childView.addView(childAt, new LinearLayout.LayoutParams(-1, -1));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x004c  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(@org.jetbrains.annotations.NotNull android.view.MotionEvent r4) {
        /*
            r3 = this;
            java.lang.String r0 = "ev"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            android.widget.FrameLayout r0 = r3.childView
            int r0 = r0.getChildCount()
            if (r0 != 0) goto L12
            boolean r4 = super.onInterceptTouchEvent(r4)
            return r4
        L12:
            android.animation.ValueAnimator r0 = r3.valueAnimator
            boolean r0 = r0.isRunning()
            if (r0 == 0) goto L1f
            android.animation.ValueAnimator r0 = r3.valueAnimator
            r0.cancel()
        L1f:
            int r0 = r4.getAction()
            if (r0 == 0) goto L6c
            r1 = 1
            if (r0 == r1) goto L4c
            r2 = 2
            if (r0 == r2) goto L2f
            r2 = 3
            if (r0 == r2) goto L4c
            goto L7c
        L2f:
            com.necer.calendar.NViewPager r0 = r3.nViewPager
            com.necer.enumeration.CalendarState r0 = r0.getCalendarState()
            if (r0 != 0) goto L38
            return r1
        L38:
            float r0 = r4.getY()
            float r1 = r3.downY
            float r1 = r1 - r0
            com.necer.utils.NAttrs r0 = com.necer.utils.NAttrs.INSTANCE
            boolean r0 = r0.isDailyCalendarStyle()
            if (r0 != 0) goto L7c
            boolean r4 = r3.interceptTouch(r1)
            return r4
        L4c:
            float r0 = r3.downY
            int r2 = r3.weekStateY
            float r2 = (float) r2
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto L7c
            android.widget.FrameLayout r2 = r3.childView
            float r2 = r2.getY()
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 >= 0) goto L7c
            r3.isFirstMove = r1
            com.necer.utils.NAttrs r0 = com.necer.utils.NAttrs.INSTANCE
            int r0 = r0.getAnimationDuration()
            long r0 = (long) r0
            r3.autoScroll(r0)
            goto L7c
        L6c:
            float r0 = r4.getY()
            r3.downY = r0
            float r0 = r4.getX()
            r3.downX = r0
            float r0 = r3.downY
            r3.lastY = r0
        L7c:
            boolean r4 = super.onInterceptTouchEvent(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.necer.calendar.NCalendar.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        super.onLayout(changed, l2, t2, r2, b3);
        if (this.isLayout) {
            return;
        }
        CalendarState calendarState = getCalendarState();
        int i2 = calendarState == null ? -1 : WhenMappings.$EnumSwitchMapping$0[calendarState.ordinal()];
        this.childView.setY(i2 != 1 ? i2 != 2 ? i2 != 3 ? 0 : this.stretchStateY : this.monthStateY : this.weekStateY);
        this.isLayout = true;
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.isLayout) {
            return;
        }
        if (this.childView.getChildCount() != 0) {
            this.childView.getLayoutParams().height = (getMeasuredHeight() - this.weekHeight) - this.nWeekBar.getMeasuredHeight();
            this.monthStateY = this.nWeekBar.getMeasuredHeight() + this.monthHeight;
            this.weekStateY = this.nWeekBar.getMeasuredHeight() + this.weekHeight;
            this.stretchStateY = this.nWeekBar.getMeasuredHeight() + this.stretchHeight;
            return;
        }
        CalendarState calendarState = getCalendarState();
        int i2 = calendarState == null ? -1 : WhenMappings.$EnumSwitchMapping$0[calendarState.ordinal()];
        int i3 = i2 != 1 ? i2 != 2 ? i2 != 3 ? 0 : this.stretchHeight : this.monthHeight : this.weekHeight;
        this.nViewPager.getLayoutParams().height = i3;
        getLayoutParams().height = i3 + this.nWeekBar.getMeasuredHeight();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedPreFling(@NotNull View target, float velocityX, float velocityY) {
        Intrinsics.checkNotNullParameter(target, "target");
        if (velocityY > 6000.0f || velocityY < -6000.0f) {
            autoScroll(50L);
        }
        return Math.round(this.childView.getY()) != this.weekStateY;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedPreScroll(@NotNull View target, int dx, int dy, @NotNull int[] consumed) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(consumed, "consumed");
        super.onNestedPreScroll(target, dx, dy, consumed);
        if (NAttrs.INSTANCE.isDailyCalendarStyle()) {
            this.directionY = dy > 0 ? 1 : -1;
            boolean z2 = ((int) this.childView.getY()) > this.weekStateY;
            boolean z3 = !target.canScrollVertically(-1);
            if (z2) {
                if (dy > 0 || z3) {
                    consumed[1] = dy;
                    gestureMove(dy);
                    return;
                }
                return;
            }
            return;
        }
        int y2 = (int) this.childView.getY();
        this.directionY = dy > 0 ? 1 : -1;
        if (y2 > this.weekStateY && y2 <= this.stretchStateY) {
            consumed[1] = dy;
            gestureMove(dy);
        } else if (dy < 0) {
            View view = this.targetView;
            Intrinsics.checkNotNull(view);
            if (view.canScrollVertically(-1)) {
                return;
            }
            consumed[1] = dy;
            gestureMove(dy);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onStartNestedScroll(@NotNull View child, @NotNull View target, int nestedScrollAxes) {
        Intrinsics.checkNotNullParameter(child, "child");
        Intrinsics.checkNotNullParameter(target, "target");
        this.targetView = target;
        return (this.isWeekHoldEnable && getCalendarState() == CalendarState.WEEK) ? false : true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onStopNestedScroll(@NotNull View child) {
        Intrinsics.checkNotNullParameter(child, "child");
        super.onStopNestedScroll(child);
        this.targetView = null;
        autoScroll(NAttrs.INSTANCE.getAnimationDuration());
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0046  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(@org.jetbrains.annotations.NotNull android.view.MotionEvent r6) throws android.content.res.Resources.NotFoundException {
        /*
            r5 = this;
            java.lang.String r0 = "event"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            int r0 = r6.getAction()
            r1 = 1
            if (r0 == r1) goto L46
            r2 = 2
            if (r0 == r2) goto L13
            r2 = 3
            if (r0 == r2) goto L46
            goto L52
        L13:
            float r0 = r6.getY()
            float r2 = r5.lastY
            float r2 = r2 - r0
            boolean r3 = r5.isFirstMove
            if (r3 == 0) goto L2f
            float r3 = r5.verticalY
            int r4 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r4 < 0) goto L26
            float r2 = r2 - r3
            goto L2c
        L26:
            float r4 = -r3
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 >= 0) goto L2c
            float r2 = r2 + r3
        L2c:
            r3 = 0
            r5.isFirstMove = r3
        L2f:
            r3 = 0
            int r4 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r4 <= 0) goto L35
            goto L36
        L35:
            r1 = -1
        L36:
            r5.directionY = r1
            float r1 = java.lang.Math.abs(r2)
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 <= 0) goto L52
            r5.gestureMove(r2)
            r5.lastY = r0
            goto L52
        L46:
            r5.isFirstMove = r1
            com.necer.utils.NAttrs r0 = com.necer.utils.NAttrs.INSTANCE
            int r0 = r0.getAnimationDuration()
            long r0 = (long) r0
            r5.autoScroll(r0)
        L52:
            boolean r6 = super.onTouchEvent(r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.necer.calendar.NCalendar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // com.necer.calendar.ICalendar
    public void setCalendarPainter(@Nullable CalendarPainter calendarPainter) {
        this.nViewPager.setCalendarPainter(calendarPainter);
    }

    public final void setCallBack(@NotNull Function0<Unit> back) {
        Intrinsics.checkNotNullParameter(back, "back");
        this.touchBack = back;
    }

    @Override // com.necer.calendar.ICalendar
    public void setCheckMode(@NotNull CheckModel checkModel) {
        Intrinsics.checkNotNullParameter(checkModel, "checkModel");
        this.nViewPager.setCheckMode(checkModel);
    }

    @Override // com.necer.calendar.ICalendar
    public void setCheckedDates(@NotNull List<String> dateList) {
        Intrinsics.checkNotNullParameter(dateList, "dateList");
        this.nViewPager.setCheckedDates(dateList);
    }

    @Override // com.necer.calendar.ICalendar
    public void setDateInterval(@Nullable String startFormatDate, @Nullable String endFormatDate, @Nullable String formatInitializeDate) throws Resources.NotFoundException {
        this.nViewPager.setDateInterval(startFormatDate, endFormatDate, formatInitializeDate);
    }

    @Override // com.necer.calendar.ICalendar
    public void setInitializeDate(@Nullable String formatInitializeDate) throws Resources.NotFoundException {
        NViewPager nViewPager = this.nViewPager;
        LocalDate localDate = LocalDate.parse(formatInitializeDate, DateTimeFormatter.ofPattern("yyyy-M-dd"));
        Intrinsics.checkNotNullExpressionValue(localDate, "parse(\n                f…yyyy-M-dd\")\n            )");
        nViewPager.setInitializeDate(localDate);
    }

    @Override // com.necer.calendar.ICalendar
    public void setMultipleCount(int multipleCount, @Nullable MultipleCountModel multipleCountModel) {
        this.nViewPager.setMultipleCount(multipleCount, multipleCountModel);
    }

    @Override // com.necer.calendar.ICalendar
    public void setOnCalendarChangedListener(@Nullable OnCalendarChangedListener onCalendarChangedListener) {
        this.nViewPager.setOnCalendarChangedListener(onCalendarChangedListener);
    }

    @Override // com.necer.calendar.ICalendar
    public void setOnCalendarMultipleChangedListener(@Nullable OnCalendarMultipleChangedListener onCalendarMultipleChangedListener) {
        this.nViewPager.setOnCalendarMultipleChangedListener(onCalendarMultipleChangedListener);
    }

    @Override // com.necer.calendar.ICalendar
    public void setOnCalendarStateChangedListener(@Nullable OnCalendarStateChangedListener onCalendarStateChangedListener) {
        this.onCalendarStateChangedListener = onCalendarStateChangedListener;
    }

    @Override // com.necer.calendar.ICalendar
    public void setOnClickDisableDateListener(@Nullable OnClickDisableDateListener onClickDisableDateListener) {
        this.nViewPager.setOnClickDisableDateListener(onClickDisableDateListener);
    }

    public final void setShouldChangeToWeek(boolean shouldChangeToWeek) {
        this.shouldChangeToWeek = shouldChangeToWeek;
    }

    @Override // com.necer.calendar.ICalendar
    public void setWeekHoldEnable(boolean isWeekHoldEnable) {
        this.isWeekHoldEnable = isWeekHoldEnable;
    }

    @Override // com.necer.calendar.ICalendar
    public void toLastPager() {
        this.nViewPager.toLastPager();
    }

    @Override // com.necer.calendar.ICalendar
    public void toMonth() {
        startAutoScroll(this.childView.getY(), this.monthStateY, NAttrs.INSTANCE.getAnimationDuration());
    }

    @Override // com.necer.calendar.ICalendar
    public void toNextPager() {
        this.nViewPager.toNextPager();
    }

    @Override // com.necer.calendar.ICalendar
    public void toStretch() {
        if (NAttrs.INSTANCE.getStretchCalendarEnable()) {
            startAutoScroll(this.childView.getY(), this.stretchStateY, r0.getAnimationDuration());
        }
    }

    @Override // com.necer.calendar.ICalendar
    public void toToday() {
        this.nViewPager.toToday();
    }

    @Override // com.necer.calendar.ICalendar
    public void toWeek() {
        startAutoScroll(this.childView.getY(), this.weekStateY, NAttrs.INSTANCE.getAnimationDuration());
    }

    @Override // com.necer.calendar.ICalendar
    public void setDateInterval(@Nullable String startFormatDate, @Nullable String endFormatDate) throws Resources.NotFoundException {
        this.nViewPager.setDateInterval(startFormatDate, endFormatDate);
    }

    @Override // com.necer.calendar.ICalendar
    public void jumpDate(int year, int month, int day) throws Resources.NotFoundException {
        LocalDate localDate = LocalDate.of(year, month, day);
        NViewPager nViewPager = this.nViewPager;
        Intrinsics.checkNotNullExpressionValue(localDate, "localDate");
        nViewPager.jumpDate(localDate, true);
    }
}
