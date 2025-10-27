package com.necer.calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.necer.enumeration.CalendarState;
import java.time.LocalDate;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.codec.language.bm.Languages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0007H\u0016J\u0010\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0013\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0007H\u0016J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J&\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/necer/calendar/NPagerAdapter;", "Landroidx/viewpager/widget/PagerAdapter;", "localDate", "Ljava/time/LocalDate;", "calendarState", "Lcom/necer/enumeration/CalendarState;", DatabaseManager.SIZE, "", "currPage", "(Ljava/time/LocalDate;Lcom/necer/enumeration/CalendarState;II)V", "destroyItem", "", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "position", Languages.ANY, "", "getCount", "getItemPosition", "instantiateItem", "isViewFromObject", "", "view", "Landroid/view/View;", "setCalendarState", "ncalendar_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class NPagerAdapter extends PagerAdapter {

    @Nullable
    private CalendarState calendarState;
    private int currPage;

    @NotNull
    private LocalDate localDate;
    private int size;

    public NPagerAdapter(@NotNull LocalDate localDate, @Nullable CalendarState calendarState, int i2, int i3) {
        Intrinsics.checkNotNullParameter(localDate, "localDate");
        this.size = Integer.MAX_VALUE;
        CalendarState calendarState2 = CalendarState.MONTH;
        this.localDate = localDate;
        this.calendarState = calendarState;
        this.size = i2;
        this.currPage = i3;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object any) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(any, "any");
        container.removeView((View) any);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    /* renamed from: getCount, reason: from getter */
    public int getSize() {
        return this.size;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(@NotNull Object any) {
        Intrinsics.checkNotNullParameter(any, "any");
        return -2;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    @NotNull
    public Object instantiateItem(@NotNull ViewGroup container, int position) {
        NCalendarView nCalendarView;
        Intrinsics.checkNotNullParameter(container, "container");
        if (this.calendarState == CalendarState.WEEK) {
            Context context = container.getContext();
            LocalDate localDatePlusWeeks = this.localDate.plusWeeks(position - this.currPage);
            Intrinsics.checkNotNullExpressionValue(localDatePlusWeeks, "localDate.plusWeeks((pos…ion - currPage).toLong())");
            nCalendarView = new NCalendarView(context, container, localDatePlusWeeks);
        } else {
            Context context2 = container.getContext();
            LocalDate localDatePlusMonths = this.localDate.plusMonths(position - this.currPage);
            Intrinsics.checkNotNullExpressionValue(localDatePlusMonths, "localDate.plusMonths((po…ion - currPage).toLong())");
            nCalendarView = new NCalendarView(context2, container, localDatePlusMonths);
        }
        nCalendarView.setTag(Integer.valueOf(position));
        container.addView(nCalendarView);
        return nCalendarView;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(@NotNull View view, @NotNull Object any) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(any, "any");
        return Intrinsics.areEqual(view, any);
    }

    public final void setCalendarState(@NotNull LocalDate localDate, @NotNull CalendarState calendarState, int size, int currPage) {
        Intrinsics.checkNotNullParameter(localDate, "localDate");
        Intrinsics.checkNotNullParameter(calendarState, "calendarState");
        this.localDate = localDate;
        this.calendarState = calendarState;
        this.size = size;
        this.currPage = currPage;
        notifyDataSetChanged();
    }
}
