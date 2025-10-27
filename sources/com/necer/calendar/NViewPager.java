package com.necer.calendar;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.Toast;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.necer.R;
import com.necer.calendar.NViewPager;
import com.necer.enumeration.CalendarState;
import com.necer.enumeration.CheckModel;
import com.necer.enumeration.DateChangeBehavior;
import com.necer.enumeration.MultipleCountModel;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.listener.OnCalendarMultipleChangedListener;
import com.necer.listener.OnClickDisableDateListener;
import com.necer.painter.CalendarPainter;
import com.necer.painter.InnerPainter;
import com.necer.utils.NAttrs;
import com.necer.utils.NDateUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0015\n\u0002\u0010\u000e\n\u0002\b\u000f\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010,\u001a\u00020-H\u0002J\u000e\u0010.\u001a\u00020\u00102\u0006\u0010/\u001a\u00020\u0012J\u0012\u00100\u001a\u00020\u00102\b\u00101\u001a\u0004\u0018\u000102H\u0016J\u0010\u00103\u001a\u00020\u00122\u0006\u0010/\u001a\u00020\u0012H\u0002J\u0006\u00104\u001a\u00020\bJ\b\u00105\u001a\u0004\u0018\u00010\nJ\u0006\u00106\u001a\u000207J\u0006\u00108\u001a\u00020\fJ\u0006\u00109\u001a\u000207J\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00120;J\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00120;J\u0006\u0010=\u001a\u000207J\u0006\u0010>\u001a\u00020\u001eJ\u0006\u0010?\u001a\u00020\u001eJ\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00120;J\u0006\u0010A\u001a\u00020\u001eJ \u0010B\u001a\u00020-2\u0006\u0010(\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u0012H\u0002J\u000e\u0010C\u001a\u00020\u00102\u0006\u0010/\u001a\u00020\u0012J \u0010D\u001a\u00020-2\u0006\u0010/\u001a\u00020\u00122\u0006\u0010E\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0016\u0010F\u001a\u00020-2\u0006\u0010/\u001a\u00020\u00122\u0006\u0010E\u001a\u00020\u0010J\u0006\u0010G\u001a\u00020-J\u000e\u0010H\u001a\u00020-2\u0006\u0010I\u001a\u000207J\u0012\u0010J\u001a\u00020\u00102\b\u00101\u001a\u0004\u0018\u000102H\u0016J\u0012\u0010K\u001a\u00020\u00102\b\u00101\u001a\u0004\u0018\u000102H\u0016J\u0010\u0010L\u001a\u00020-2\b\u0010\u0007\u001a\u0004\u0018\u00010\bJ\u000e\u0010M\u001a\u00020-2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010N\u001a\u00020-2\u0006\u0010\u000b\u001a\u00020\fJ\u0014\u0010O\u001a\u00020-2\f\u0010P\u001a\b\u0012\u0004\u0012\u00020Q0;J\u000e\u0010R\u001a\u00020-2\u0006\u0010S\u001a\u00020\u0012J\u001a\u0010T\u001a\u00020-2\b\u0010U\u001a\u0004\u0018\u00010Q2\b\u0010V\u001a\u0004\u0018\u00010QJ$\u0010T\u001a\u00020-2\b\u0010U\u001a\u0004\u0018\u00010Q2\b\u0010V\u001a\u0004\u0018\u00010Q2\b\u0010W\u001a\u0004\u0018\u00010QJ\u000e\u0010X\u001a\u00020-2\u0006\u0010/\u001a\u00020\u0012J\u0018\u0010Y\u001a\u00020-2\u0006\u0010\u001f\u001a\u00020\u001e2\b\u0010 \u001a\u0004\u0018\u00010!J\u0010\u0010Z\u001a\u00020-2\b\u0010\"\u001a\u0004\u0018\u00010#J\u0010\u0010[\u001a\u00020-2\b\u0010$\u001a\u0004\u0018\u00010%J\u0010\u0010\\\u001a\u00020-2\b\u0010&\u001a\u0004\u0018\u00010'J\u0006\u0010]\u001a\u00020-J\u0006\u0010^\u001a\u00020-J\u0006\u0010_\u001a\u00020-R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n \u0013*\u0004\u0018\u00010\u00120\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0014\u001a\n \u0013*\u0004\u0018\u00010\u00120\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\u0012X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010%X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010'X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u001e\u0010)\u001a\u0012\u0012\u0004\u0012\u00020\u00120*j\b\u0012\u0004\u0012\u00020\u0012`+X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006`"}, d2 = {"Lcom/necer/calendar/NViewPager;", "Landroidx/viewpager/widget/ViewPager;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "calendarPainter", "Lcom/necer/painter/CalendarPainter;", "calendarState", "Lcom/necer/enumeration/CalendarState;", "checkModel", "Lcom/necer/enumeration/CheckModel;", "dateChangeBehavior", "Lcom/necer/enumeration/DateChangeBehavior;", "defaultCheckedFirstDate", "", "defaultEndDateDate", "Ljava/time/LocalDate;", "kotlin.jvm.PlatformType", "defaultStartDate", com.heytap.mcssdk.constant.b.f7195t, "fulcrumDate", "getFulcrumDate", "()Ljava/time/LocalDate;", "setFulcrumDate", "(Ljava/time/LocalDate;)V", "initDate", "lastCallbackDate", "monthHeight", "", "multipleCount", "multipleCountModel", "Lcom/necer/enumeration/MultipleCountModel;", "onCalendarChangedListener", "Lcom/necer/listener/OnCalendarChangedListener;", "onCalendarMultipleChangedListener", "Lcom/necer/listener/OnCalendarMultipleChangedListener;", "onClickDisableDateListener", "Lcom/necer/listener/OnClickDisableDateListener;", com.heytap.mcssdk.constant.b.f7194s, "totalCheckedDateList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "dateChangedCallBack", "", "dateInTaskList", "localDate", "dispatchTouchEvent", "ev", "Landroid/view/MotionEvent;", "getAvailableDate", "getCalendarPainter", "getCalendarState", "getCanvasHeight", "", "getCheckMode", "getChildY", "getCurrPagerCheckedDateList", "", "getCurrPagerDateList", "getHoldHeight", "getMonthHeight", "getMonthStateY", "getTotalCheckedDateList", "getWeekStateY", "init", "isAvailable", "jump", "isCheck", "jumpDate", "notifyAllView", "offsetView", "dy", "onInterceptTouchEvent", "onTouchEvent", "setCalendarPainter", "setCalendarState", "setCheckMode", "setCheckedDates", "dateList", "", "setClickDate", "checkedDate", "setDateInterval", "startFormatDate", "endFormatDate", "formatInitializeDate", "setInitializeDate", "setMultipleCount", "setOnCalendarChangedListener", "setOnCalendarMultipleChangedListener", "setOnClickDisableDateListener", "toLastPager", "toNextPager", "toToday", "ncalendar_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nNViewPager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NViewPager.kt\ncom/necer/calendar/NViewPager\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,498:1\n1#2:499\n*E\n"})
/* loaded from: classes4.dex */
public final class NViewPager extends ViewPager {

    @Nullable
    private CalendarPainter calendarPainter;

    @Nullable
    private CalendarState calendarState;
    private CheckModel checkModel;

    @NotNull
    private DateChangeBehavior dateChangeBehavior;
    private boolean defaultCheckedFirstDate;
    private final LocalDate defaultEndDateDate;
    private final LocalDate defaultStartDate;
    private LocalDate endDate;
    public LocalDate fulcrumDate;
    private LocalDate initDate;

    @Nullable
    private LocalDate lastCallbackDate;
    private int monthHeight;
    private int multipleCount;

    @Nullable
    private MultipleCountModel multipleCountModel;

    @Nullable
    private OnCalendarChangedListener onCalendarChangedListener;

    @Nullable
    private OnCalendarMultipleChangedListener onCalendarMultipleChangedListener;

    @Nullable
    private OnClickDisableDateListener onClickDisableDateListener;
    private LocalDate startDate;

    @NotNull
    private final ArrayList<LocalDate> totalCheckedDateList;

    @Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0005H\u0016¨\u0006\b"}, d2 = {"com/necer/calendar/NViewPager$1", "Landroidx/viewpager/widget/ViewPager$SimpleOnPageChangeListener;", "onPageScrollStateChanged", "", "state", "", "onPageSelected", "position", "ncalendar_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.necer.calendar.NViewPager$1, reason: invalid class name */
    public static final class AnonymousClass1 extends ViewPager.SimpleOnPageChangeListener {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onPageSelected$lambda$0(NViewPager this$0) {
            LocalDate localDate;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            View viewFindViewWithTag = this$0.findViewWithTag(Integer.valueOf(this$0.getCurrentItem()));
            CheckModel checkModel = null;
            NCalendarView nCalendarView = viewFindViewWithTag instanceof NCalendarView ? (NCalendarView) viewFindViewWithTag : null;
            LocalDate pagerInitDate = nCalendarView != null ? nCalendarView.getPagerInitDate() : null;
            if (this$0.calendarState == CalendarState.WEEK) {
                NDateUtil nDateUtil = NDateUtil.INSTANCE;
                LocalDate fulcrumDate = this$0.getFulcrumDate();
                Intrinsics.checkNotNull(pagerInitDate);
                localDate = this$0.getFulcrumDate().plusWeeks(nDateUtil.getIntervalWeek(fulcrumDate, pagerInitDate, NAttrs.INSTANCE.getFirstDayOfWeek()));
            } else {
                NDateUtil nDateUtil2 = NDateUtil.INSTANCE;
                LocalDate fulcrumDate2 = this$0.getFulcrumDate();
                Intrinsics.checkNotNull(pagerInitDate);
                localDate = this$0.getFulcrumDate().plusMonths(nDateUtil2.getIntervalMonths(fulcrumDate2, pagerInitDate));
            }
            Intrinsics.checkNotNullExpressionValue(localDate, "localDate");
            LocalDate availableDate = this$0.getAvailableDate(localDate);
            CheckModel checkModel2 = this$0.checkModel;
            if (checkModel2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("checkModel");
            } else {
                checkModel = checkModel2;
            }
            if (checkModel == CheckModel.SINGLE_DEFAULT_CHECKED) {
                if (this$0.defaultCheckedFirstDate) {
                    availableDate = nCalendarView.getFirstLocalDate();
                }
                this$0.totalCheckedDateList.clear();
                this$0.totalCheckedDateList.add(availableDate);
            }
            List<LocalDate> currPageCheckedDateList = nCalendarView.getCurrPageCheckedDateList();
            if (currPageCheckedDateList.isEmpty()) {
                Intrinsics.checkNotNullExpressionValue(availableDate, "{\n                      …ate\n                    }");
            } else {
                availableDate = currPageCheckedDateList.get(0);
            }
            this$0.setFulcrumDate(availableDate);
            this$0.notifyAllView();
            this$0.dateChangedCallBack();
        }

        @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
            if (state == 1) {
                NViewPager.this.dateChangeBehavior = DateChangeBehavior.PAGE;
            }
        }

        @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            final NViewPager nViewPager = NViewPager.this;
            nViewPager.post(new Runnable() { // from class: com.necer.calendar.f
                @Override // java.lang.Runnable
                public final void run() {
                    NViewPager.AnonymousClass1.onPageSelected$lambda$0(nViewPager);
                }
            });
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

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
            int[] iArr2 = new int[CheckModel.values().length];
            try {
                iArr2[CheckModel.SINGLE_DEFAULT_CHECKED.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[CheckModel.SINGLE_DEFAULT_UNCHECKED.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[CheckModel.MULTIPLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NViewPager(@NotNull Context context, @Nullable AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.totalCheckedDateList = new ArrayList<>();
        this.dateChangeBehavior = DateChangeBehavior.INITIALIZE;
        LocalDate startDate = LocalDate.parse("1901-01-01");
        this.defaultStartDate = startDate;
        LocalDate endDate = LocalDate.parse("2099-12-31");
        this.defaultEndDateDate = endDate;
        addOnPageChangeListener(new AnonymousClass1());
        CalendarState.Companion companion = CalendarState.INSTANCE;
        NAttrs nAttrs = NAttrs.INSTANCE;
        this.calendarState = companion.valueOf(nAttrs.getDefaultCalendar());
        this.monthHeight = nAttrs.getCalendarHeight();
        this.defaultCheckedFirstDate = nAttrs.getDefaultCheckedFirstDate();
        LocalDate localDateNow = LocalDate.now();
        Intrinsics.checkNotNullExpressionValue(localDateNow, "now()");
        Intrinsics.checkNotNullExpressionValue(startDate, "startDate");
        Intrinsics.checkNotNullExpressionValue(endDate, "endDate");
        init(startDate, endDate, localDateNow);
        setCheckMode(CheckModel.SINGLE_DEFAULT_CHECKED);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dateChangedCallBack() {
        CheckModel checkModel = this.checkModel;
        if (checkModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("checkModel");
            checkModel = null;
        }
        int i2 = WhenMappings.$EnumSwitchMapping$1[checkModel.ordinal()];
        if (i2 == 1) {
            LocalDate localDate = this.totalCheckedDateList.get(0);
            Intrinsics.checkNotNullExpressionValue(localDate, "totalCheckedDateList[0]");
            LocalDate localDate2 = localDate;
            if (Intrinsics.areEqual(this.lastCallbackDate, localDate2)) {
                return;
            }
            OnCalendarChangedListener onCalendarChangedListener = this.onCalendarChangedListener;
            if (onCalendarChangedListener != null) {
                onCalendarChangedListener.onCalendarChange(localDate2.getYear(), localDate2.getMonthValue(), localDate2, this.dateChangeBehavior);
            }
            this.lastCallbackDate = localDate2;
            return;
        }
        if (i2 == 2) {
            View viewFindViewWithTag = findViewWithTag(Integer.valueOf(getCurrentItem()));
            Intrinsics.checkNotNull(viewFindViewWithTag, "null cannot be cast to non-null type com.necer.calendar.NCalendarView");
            NCalendarView nCalendarView = (NCalendarView) viewFindViewWithTag;
            LocalDate pagerInitDate = nCalendarView.getPagerInitDate();
            List<LocalDate> currPageCheckedDateList = nCalendarView.getCurrPageCheckedDateList();
            LocalDate localDate3 = currPageCheckedDateList.isEmpty() ? null : currPageCheckedDateList.get(0);
            OnCalendarChangedListener onCalendarChangedListener2 = this.onCalendarChangedListener;
            if (onCalendarChangedListener2 != null) {
                onCalendarChangedListener2.onCalendarChange(pagerInitDate.getYear(), pagerInitDate.getMonthValue(), localDate3, this.dateChangeBehavior);
                return;
            }
            return;
        }
        if (i2 != 3) {
            return;
        }
        View viewFindViewWithTag2 = findViewWithTag(Integer.valueOf(getCurrentItem()));
        Intrinsics.checkNotNull(viewFindViewWithTag2, "null cannot be cast to non-null type com.necer.calendar.NCalendarView");
        NCalendarView nCalendarView2 = (NCalendarView) viewFindViewWithTag2;
        LocalDate pagerInitDate2 = nCalendarView2.getPagerInitDate();
        List<LocalDate> currPageCheckedDateList2 = nCalendarView2.getCurrPageCheckedDateList();
        OnCalendarMultipleChangedListener onCalendarMultipleChangedListener = this.onCalendarMultipleChangedListener;
        if (onCalendarMultipleChangedListener != null) {
            onCalendarMultipleChangedListener.onCalendarChange(pagerInitDate2.getYear(), pagerInitDate2.getMonthValue(), currPageCheckedDateList2, this.totalCheckedDateList, this.dateChangeBehavior);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final LocalDate getAvailableDate(LocalDate localDate) {
        LocalDate localDate2 = this.startDate;
        if (localDate2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.heytap.mcssdk.constant.b.f7194s);
            localDate2 = null;
        }
        if (localDate.isBefore(localDate2)) {
            LocalDate localDate3 = this.startDate;
            if (localDate3 != null) {
                return localDate3;
            }
            Intrinsics.throwUninitializedPropertyAccessException(com.heytap.mcssdk.constant.b.f7194s);
        } else {
            LocalDate localDate4 = this.endDate;
            if (localDate4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(com.heytap.mcssdk.constant.b.f7195t);
                localDate4 = null;
            }
            if (!localDate.isAfter(localDate4)) {
                return localDate;
            }
            LocalDate localDate5 = this.endDate;
            if (localDate5 != null) {
                return localDate5;
            }
            Intrinsics.throwUninitializedPropertyAccessException(com.heytap.mcssdk.constant.b.f7195t);
        }
        return null;
    }

    private final void init(LocalDate startDate, LocalDate endDate, LocalDate initDate) throws Resources.NotFoundException {
        this.startDate = startDate;
        this.endDate = endDate;
        LocalDate availableDate = getAvailableDate(initDate);
        this.initDate = availableDate;
        if (availableDate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("initDate");
            availableDate = null;
        }
        setFulcrumDate(availableDate);
        if (!(!startDate.isAfter(endDate))) {
            String string = getContext().getString(R.string.N_start_after_end);
            Intrinsics.checkNotNullExpressionValue(string, "context.getString(R.string.N_start_after_end)");
            throw new IllegalArgumentException(string.toString());
        }
        if (!(!startDate.isBefore(this.defaultStartDate))) {
            String string2 = getContext().getString(R.string.N_start_before_19010101);
            Intrinsics.checkNotNullExpressionValue(string2, "context.getString(R.stri….N_start_before_19010101)");
            throw new IllegalArgumentException(string2.toString());
        }
        if (!(!endDate.isAfter(this.defaultEndDateDate))) {
            String string3 = getContext().getString(R.string.N_end_after_20991231);
            Intrinsics.checkNotNullExpressionValue(string3, "context.getString(R.string.N_end_after_20991231)");
            throw new IllegalArgumentException(string3.toString());
        }
        int i2 = 0;
        if (!((startDate.isAfter(initDate) || endDate.isBefore(initDate)) ? false : true)) {
            String string4 = getContext().getString(R.string.N_initialize_date_illegal);
            Intrinsics.checkNotNullExpressionValue(string4, "context.getString(R.stri…_initialize_date_illegal)");
            throw new IllegalArgumentException(string4.toString());
        }
        NDateUtil nDateUtil = NDateUtil.INSTANCE;
        int intervalMonths = nDateUtil.getIntervalMonths(startDate, endDate) + 1;
        int intervalWeek = nDateUtil.getIntervalWeek(startDate, endDate, NAttrs.INSTANCE.getFirstDayOfWeek()) + 1;
        CalendarState calendarState = this.calendarState;
        int i3 = calendarState == null ? -1 : WhenMappings.$EnumSwitchMapping$0[calendarState.ordinal()];
        if (i3 == 1) {
            i2 = intervalWeek;
        } else if (i3 == 2 || i3 == 3) {
            i2 = intervalMonths;
        }
        setAdapter(new NPagerAdapter(initDate, this.calendarState, i2, nDateUtil.getIntervalMonths(startDate, initDate)));
        setCurrentItem(nDateUtil.getIntervalMonths(startDate, initDate));
    }

    private final void jump(LocalDate localDate, boolean isCheck, DateChangeBehavior dateChangeBehavior) throws Resources.NotFoundException {
        if (!isAvailable(localDate)) {
            OnClickDisableDateListener onClickDisableDateListener = this.onClickDisableDateListener;
            if (onClickDisableDateListener == null) {
                Context context = getContext();
                NAttrs nAttrs = NAttrs.INSTANCE;
                Toast.makeText(context, TextUtils.isEmpty(nAttrs.getDisabledString()) ? getResources().getString(R.string.N_disabledString) : nAttrs.getDisabledString(), 0).show();
                return;
            } else {
                if (onClickDisableDateListener != null) {
                    onClickDisableDateListener.onClickDisableDate(localDate);
                    return;
                }
                return;
            }
        }
        View viewFindViewWithTag = findViewWithTag(Integer.valueOf(getCurrentItem()));
        Intrinsics.checkNotNull(viewFindViewWithTag, "null cannot be cast to non-null type com.necer.calendar.NCalendarView");
        NCalendarView nCalendarView = (NCalendarView) viewFindViewWithTag;
        LocalDate pagerInitDate = nCalendarView.getPagerInitDate();
        int intervalWeek = this.calendarState == CalendarState.WEEK ? NDateUtil.INSTANCE.getIntervalWeek(localDate, pagerInitDate, NAttrs.INSTANCE.getFirstDayOfWeek()) : NDateUtil.INSTANCE.getIntervalMonths(localDate, pagerInitDate);
        if (intervalWeek != 0 && !NAttrs.INSTANCE.getHorizontalScrollEnable()) {
            Toast.makeText(getContext(), getResources().getString(R.string.N_horizontalScrollString), 0).show();
            return;
        }
        setFulcrumDate(localDate);
        this.dateChangeBehavior = dateChangeBehavior;
        if (isCheck) {
            CheckModel checkModel = this.checkModel;
            if (checkModel == null) {
                Intrinsics.throwUninitializedPropertyAccessException("checkModel");
                checkModel = null;
            }
            if (checkModel != CheckModel.MULTIPLE) {
                this.totalCheckedDateList.clear();
                this.totalCheckedDateList.add(localDate);
            } else if (this.totalCheckedDateList.contains(localDate)) {
                this.totalCheckedDateList.remove(localDate);
            } else {
                if (this.totalCheckedDateList.size() == this.multipleCount && this.multipleCountModel == MultipleCountModel.FULL_CLEAR) {
                    this.totalCheckedDateList.clear();
                } else if (this.totalCheckedDateList.size() == this.multipleCount && this.multipleCountModel == MultipleCountModel.FULL_REMOVE_FIRST) {
                    this.totalCheckedDateList.remove(0);
                }
                this.totalCheckedDateList.add(localDate);
            }
        }
        if (intervalWeek != 0) {
            setCurrentItem(getCurrentItem() - intervalWeek, Math.abs(intervalWeek) == 1);
        } else {
            nCalendarView.invalidate();
            dateChangedCallBack();
        }
    }

    public final boolean dateInTaskList(@NotNull LocalDate localDate) {
        Intrinsics.checkNotNullParameter(localDate, "localDate");
        CalendarPainter calendarPainter = this.calendarPainter;
        Intrinsics.checkNotNull(calendarPainter, "null cannot be cast to non-null type com.necer.painter.InnerPainter");
        return ((InnerPainter) calendarPainter).getDailyTaskDateList().contains(localDate);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(@Nullable MotionEvent ev) {
        if (this.calendarState == null) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @NotNull
    public final CalendarPainter getCalendarPainter() {
        if (this.calendarPainter == null) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            ViewParent parent = getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type com.necer.calendar.NCalendar");
            this.calendarPainter = new InnerPainter(context, (NCalendar) parent);
        }
        CalendarPainter calendarPainter = this.calendarPainter;
        Intrinsics.checkNotNull(calendarPainter);
        return calendarPainter;
    }

    @Nullable
    public final CalendarState getCalendarState() {
        return this.calendarState;
    }

    public final float getCanvasHeight() {
        ViewParent parent = getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type com.necer.calendar.NCalendar");
        return ((NCalendar) parent).getCanvasHeight();
    }

    @NotNull
    public final CheckModel getCheckMode() {
        CheckModel checkModel = this.checkModel;
        if (checkModel != null) {
            return checkModel;
        }
        Intrinsics.throwUninitializedPropertyAccessException("checkModel");
        return null;
    }

    public final float getChildY() {
        ViewParent parent = getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type com.necer.calendar.NCalendar");
        return ((NCalendar) parent).getChildY();
    }

    @NotNull
    public final List<LocalDate> getCurrPagerCheckedDateList() {
        View viewFindViewWithTag = findViewWithTag(Integer.valueOf(getCurrentItem()));
        Intrinsics.checkNotNull(viewFindViewWithTag, "null cannot be cast to non-null type com.necer.calendar.NCalendarView");
        return ((NCalendarView) viewFindViewWithTag).getCurrPageCheckedDateList();
    }

    @NotNull
    public final List<LocalDate> getCurrPagerDateList() {
        View viewFindViewWithTag = findViewWithTag(Integer.valueOf(getCurrentItem()));
        Intrinsics.checkNotNull(viewFindViewWithTag, "null cannot be cast to non-null type com.necer.calendar.NCalendarView");
        return ((NCalendarView) viewFindViewWithTag).getCurrPagerDateList();
    }

    @NotNull
    public final LocalDate getFulcrumDate() {
        LocalDate localDate = this.fulcrumDate;
        if (localDate != null) {
            return localDate;
        }
        Intrinsics.throwUninitializedPropertyAccessException("fulcrumDate");
        return null;
    }

    public final float getHoldHeight() {
        return ((NCalendarView) findViewWithTag(Integer.valueOf(getCurrentItem()))).getHoldHeight();
    }

    public final int getMonthHeight() {
        return this.monthHeight;
    }

    public final int getMonthStateY() {
        ViewParent parent = getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type com.necer.calendar.NCalendar");
        return ((NCalendar) parent).getMonthStateY();
    }

    @NotNull
    public final List<LocalDate> getTotalCheckedDateList() {
        return this.totalCheckedDateList;
    }

    public final int getWeekStateY() {
        ViewParent parent = getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type com.necer.calendar.NCalendar");
        return ((NCalendar) parent).getWeekStateY();
    }

    public final boolean isAvailable(@NotNull LocalDate localDate) {
        Intrinsics.checkNotNullParameter(localDate, "localDate");
        LocalDate localDate2 = this.startDate;
        LocalDate localDate3 = null;
        if (localDate2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.heytap.mcssdk.constant.b.f7194s);
            localDate2 = null;
        }
        if (!localDate.isBefore(localDate2)) {
            LocalDate localDate4 = this.endDate;
            if (localDate4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(com.heytap.mcssdk.constant.b.f7195t);
            } else {
                localDate3 = localDate4;
            }
            if (!localDate.isAfter(localDate3)) {
                return true;
            }
        }
        return false;
    }

    public final void jumpDate(@NotNull LocalDate localDate, boolean isCheck) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(localDate, "localDate");
        jump(localDate, isCheck, DateChangeBehavior.API);
    }

    public final void notifyAllView() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type com.necer.calendar.NCalendarView");
            ((NCalendarView) childAt).invalidate();
        }
    }

    public final void offsetView(float dy) {
        this.calendarState = null;
        ((NCalendarView) findViewWithTag(Integer.valueOf(getCurrentItem()))).offsetCanvas(dy);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(@Nullable MotionEvent ev) {
        if (NAttrs.INSTANCE.getHorizontalScrollEnable()) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(@Nullable MotionEvent ev) {
        if (NAttrs.INSTANCE.getHorizontalScrollEnable()) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    public final void setCalendarPainter(@Nullable CalendarPainter calendarPainter) {
        this.calendarPainter = calendarPainter;
    }

    public final void setCalendarState(@NotNull CalendarState calendarState) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(calendarState, "calendarState");
        this.calendarState = calendarState;
        LocalDate localDate = null;
        if (calendarState != CalendarState.WEEK) {
            NDateUtil nDateUtil = NDateUtil.INSTANCE;
            LocalDate localDate2 = this.startDate;
            if (localDate2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(com.heytap.mcssdk.constant.b.f7194s);
                localDate2 = null;
            }
            int intervalMonths = nDateUtil.getIntervalMonths(localDate2, getFulcrumDate());
            LocalDate localDate3 = this.startDate;
            if (localDate3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(com.heytap.mcssdk.constant.b.f7194s);
                localDate3 = null;
            }
            LocalDate localDate4 = this.endDate;
            if (localDate4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(com.heytap.mcssdk.constant.b.f7195t);
            } else {
                localDate = localDate4;
            }
            int intervalMonths2 = nDateUtil.getIntervalMonths(localDate3, localDate) + 1;
            PagerAdapter adapter = getAdapter();
            Intrinsics.checkNotNull(adapter, "null cannot be cast to non-null type com.necer.calendar.NPagerAdapter");
            ((NPagerAdapter) adapter).setCalendarState(getFulcrumDate(), calendarState, intervalMonths2, intervalMonths);
            setCurrentItem(intervalMonths, false);
            return;
        }
        NDateUtil nDateUtil2 = NDateUtil.INSTANCE;
        LocalDate localDate5 = this.startDate;
        if (localDate5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.heytap.mcssdk.constant.b.f7194s);
            localDate5 = null;
        }
        LocalDate fulcrumDate = getFulcrumDate();
        NAttrs nAttrs = NAttrs.INSTANCE;
        int intervalWeek = nDateUtil2.getIntervalWeek(localDate5, fulcrumDate, nAttrs.getFirstDayOfWeek());
        LocalDate localDate6 = this.startDate;
        if (localDate6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.heytap.mcssdk.constant.b.f7194s);
            localDate6 = null;
        }
        LocalDate localDate7 = this.endDate;
        if (localDate7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.heytap.mcssdk.constant.b.f7195t);
        } else {
            localDate = localDate7;
        }
        int intervalWeek2 = nDateUtil2.getIntervalWeek(localDate6, localDate, nAttrs.getFirstDayOfWeek()) + 1;
        PagerAdapter adapter2 = getAdapter();
        Intrinsics.checkNotNull(adapter2, "null cannot be cast to non-null type com.necer.calendar.NPagerAdapter");
        ((NPagerAdapter) adapter2).setCalendarState(getFulcrumDate(), calendarState, intervalWeek2, intervalWeek);
        setCurrentItem(intervalWeek, false);
    }

    public final void setCheckMode(@NotNull CheckModel checkModel) {
        Intrinsics.checkNotNullParameter(checkModel, "checkModel");
        this.checkModel = checkModel;
        this.totalCheckedDateList.clear();
        if (checkModel == CheckModel.SINGLE_DEFAULT_CHECKED) {
            ArrayList<LocalDate> arrayList = this.totalCheckedDateList;
            LocalDate localDate = this.initDate;
            if (localDate == null) {
                Intrinsics.throwUninitializedPropertyAccessException("initDate");
                localDate = null;
            }
            arrayList.add(localDate);
        }
    }

    public final void setCheckedDates(@NotNull List<String> dateList) {
        Intrinsics.checkNotNullParameter(dateList, "dateList");
        CheckModel checkModel = this.checkModel;
        if (checkModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("checkModel");
            checkModel = null;
        }
        if (checkModel != CheckModel.MULTIPLE) {
            throw new RuntimeException(getContext().getString(R.string.N_set_checked_dates_illegal));
        }
        if (this.multipleCountModel != null && dateList.size() > this.multipleCount) {
            throw new RuntimeException(getContext().getString(R.string.N_set_checked_dates_count_illegal));
        }
        this.totalCheckedDateList.clear();
        DateTimeFormatter dateTimeFormatterOfPattern = DateTimeFormatter.ofPattern("yyyy-M-d");
        try {
            int size = dateList.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.totalCheckedDateList.add(LocalDate.parse(dateList.get(i2), dateTimeFormatterOfPattern));
            }
        } catch (Exception unused) {
            throw new IllegalArgumentException(getContext().getString(R.string.N_date_format_illegal));
        }
    }

    public final void setClickDate(@NotNull LocalDate checkedDate) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(checkedDate, "checkedDate");
        NAttrs nAttrs = NAttrs.INSTANCE;
        if (!nAttrs.isDailyCalendarStyle() || dateInTaskList(checkedDate)) {
            if (this.calendarState == CalendarState.WEEK) {
                jump(checkedDate, true, DateChangeBehavior.CLICK);
                return;
            }
            View viewFindViewWithTag = findViewWithTag(Integer.valueOf(getCurrentItem()));
            Intrinsics.checkNotNull(viewFindViewWithTag, "null cannot be cast to non-null type com.necer.calendar.NCalendarView");
            if (NDateUtil.INSTANCE.isEqualsMonth(checkedDate, ((NCalendarView) viewFindViewWithTag).getPagerInitDate()) || !nAttrs.getHorizontalScrollEnable()) {
                jump(checkedDate, true, DateChangeBehavior.CLICK);
            } else if (nAttrs.getClickNotCurrentPageMonthCanJump()) {
                jump(checkedDate, true, DateChangeBehavior.CLICK_PAGE);
            }
        }
    }

    public final void setDateInterval(@Nullable String startFormatDate, @Nullable String endFormatDate, @Nullable String formatInitializeDate) throws Resources.NotFoundException {
        DateTimeFormatter dateTimeFormatterOfPattern = DateTimeFormatter.ofPattern("yyyy-M-d");
        LocalDate localDate = LocalDate.parse(startFormatDate, dateTimeFormatterOfPattern);
        Intrinsics.checkNotNullExpressionValue(localDate, "parse(startFormatDate,dateFormatter)");
        LocalDate localDate2 = LocalDate.parse(endFormatDate, dateTimeFormatterOfPattern);
        Intrinsics.checkNotNullExpressionValue(localDate2, "parse(endFormatDate,dateFormatter)");
        LocalDate localDate3 = LocalDate.parse(formatInitializeDate);
        Intrinsics.checkNotNullExpressionValue(localDate3, "parse(formatInitializeDate)");
        init(localDate, localDate2, localDate3);
    }

    public final void setFulcrumDate(@NotNull LocalDate localDate) {
        Intrinsics.checkNotNullParameter(localDate, "<set-?>");
        this.fulcrumDate = localDate;
    }

    public final void setInitializeDate(@NotNull LocalDate localDate) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(localDate, "localDate");
        LocalDate defaultStartDate = this.defaultStartDate;
        Intrinsics.checkNotNullExpressionValue(defaultStartDate, "defaultStartDate");
        LocalDate defaultEndDateDate = this.defaultEndDateDate;
        Intrinsics.checkNotNullExpressionValue(defaultEndDateDate, "defaultEndDateDate");
        init(defaultStartDate, defaultEndDateDate, localDate);
    }

    public final void setMultipleCount(int multipleCount, @Nullable MultipleCountModel multipleCountModel) {
        setCheckMode(CheckModel.MULTIPLE);
        this.multipleCount = multipleCount;
        this.multipleCountModel = multipleCountModel;
    }

    public final void setOnCalendarChangedListener(@Nullable OnCalendarChangedListener onCalendarChangedListener) {
        this.onCalendarChangedListener = onCalendarChangedListener;
    }

    public final void setOnCalendarMultipleChangedListener(@Nullable OnCalendarMultipleChangedListener onCalendarMultipleChangedListener) {
        this.onCalendarMultipleChangedListener = onCalendarMultipleChangedListener;
    }

    public final void setOnClickDisableDateListener(@Nullable OnClickDisableDateListener onClickDisableDateListener) {
        this.onClickDisableDateListener = onClickDisableDateListener;
    }

    public final void toLastPager() throws Resources.NotFoundException {
        if (!NAttrs.INSTANCE.getHorizontalScrollEnable()) {
            Toast.makeText(getContext(), getResources().getString(R.string.N_horizontalScrollString), 0).show();
        } else {
            this.dateChangeBehavior = DateChangeBehavior.API;
            setCurrentItem(getCurrentItem() - 1);
        }
    }

    public final void toNextPager() throws Resources.NotFoundException {
        if (!NAttrs.INSTANCE.getHorizontalScrollEnable()) {
            Toast.makeText(getContext(), getResources().getString(R.string.N_horizontalScrollString), 0).show();
        } else {
            this.dateChangeBehavior = DateChangeBehavior.API;
            setCurrentItem(getCurrentItem() + 1);
        }
    }

    public final void toToday() throws Resources.NotFoundException {
        LocalDate localDateNow = LocalDate.now();
        Intrinsics.checkNotNullExpressionValue(localDateNow, "now()");
        jump(localDateNow, true, DateChangeBehavior.API);
    }

    public final void setDateInterval(@Nullable String startFormatDate, @Nullable String endFormatDate) throws Resources.NotFoundException {
        DateTimeFormatter dateTimeFormatterOfPattern = DateTimeFormatter.ofPattern("yyyy-M-d");
        LocalDate localDate = LocalDate.parse(startFormatDate, dateTimeFormatterOfPattern);
        Intrinsics.checkNotNullExpressionValue(localDate, "parse(startFormatDate,dateFormatter)");
        LocalDate localDate2 = LocalDate.parse(endFormatDate, dateTimeFormatterOfPattern);
        Intrinsics.checkNotNullExpressionValue(localDate2, "parse(endFormatDate,dateFormatter)");
        LocalDate localDateNow = LocalDate.now();
        Intrinsics.checkNotNullExpressionValue(localDateNow, "now()");
        init(localDate, localDate2, localDateNow);
    }
}
