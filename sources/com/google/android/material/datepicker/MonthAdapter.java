package com.google.android.material.datepicker;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes3.dex */
class MonthAdapter extends BaseAdapter {
    static final int MAXIMUM_WEEKS = UtcDates.getUtcCalendar().getMaximum(4);
    final CalendarConstraints calendarConstraints;
    CalendarStyle calendarStyle;
    final DateSelector<?> dateSelector;
    final Month month;
    private Collection<Long> previouslySelectedDates;

    public MonthAdapter(Month month, DateSelector<?> dateSelector, CalendarConstraints calendarConstraints) {
        this.month = month;
        this.dateSelector = dateSelector;
        this.calendarConstraints = calendarConstraints;
        this.previouslySelectedDates = dateSelector.getSelectedDays();
    }

    private void initializeStyles(Context context) {
        if (this.calendarStyle == null) {
            this.calendarStyle = new CalendarStyle(context);
        }
    }

    private boolean isSelected(long j2) {
        Iterator<Long> it = this.dateSelector.getSelectedDays().iterator();
        while (it.hasNext()) {
            if (UtcDates.canonicalYearMonthDay(j2) == UtcDates.canonicalYearMonthDay(it.next().longValue())) {
                return true;
            }
        }
        return false;
    }

    private void updateSelectedState(@Nullable TextView textView, long j2) {
        CalendarItemStyle calendarItemStyle;
        if (textView == null) {
            return;
        }
        if (this.calendarConstraints.getDateValidator().isValid(j2)) {
            textView.setEnabled(true);
            calendarItemStyle = isSelected(j2) ? this.calendarStyle.selectedDay : UtcDates.getTodayCalendar().getTimeInMillis() == j2 ? this.calendarStyle.todayDay : this.calendarStyle.day;
        } else {
            textView.setEnabled(false);
            calendarItemStyle = this.calendarStyle.invalidDay;
        }
        calendarItemStyle.styleItem(textView);
    }

    private void updateSelectedStateForDate(MaterialCalendarGridView materialCalendarGridView, long j2) {
        if (Month.create(j2).equals(this.month)) {
            updateSelectedState((TextView) materialCalendarGridView.getChildAt(materialCalendarGridView.getAdapter().dayToPosition(this.month.getDayOfMonth(j2)) - materialCalendarGridView.getFirstVisiblePosition()), j2);
        }
    }

    public int dayToPosition(int i2) {
        return firstPositionInMonth() + (i2 - 1);
    }

    public int firstPositionInMonth() {
        return this.month.daysFromStartOfWeekToFirstOfMonth();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.month.daysInMonth + firstPositionInMonth();
    }

    @Override // android.widget.Adapter
    public long getItemId(int i2) {
        return i2 / this.month.daysInWeek;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return true;
    }

    public boolean isFirstInRow(int i2) {
        return i2 % this.month.daysInWeek == 0;
    }

    public boolean isLastInRow(int i2) {
        return (i2 + 1) % this.month.daysInWeek == 0;
    }

    public int lastPositionInMonth() {
        return (this.month.daysFromStartOfWeekToFirstOfMonth() + this.month.daysInMonth) - 1;
    }

    public int positionToDay(int i2) {
        return (i2 - this.month.daysFromStartOfWeekToFirstOfMonth()) + 1;
    }

    public void updateSelectedStates(MaterialCalendarGridView materialCalendarGridView) {
        Iterator<Long> it = this.previouslySelectedDates.iterator();
        while (it.hasNext()) {
            updateSelectedStateForDate(materialCalendarGridView, it.next().longValue());
        }
        DateSelector<?> dateSelector = this.dateSelector;
        if (dateSelector != null) {
            Iterator<Long> it2 = dateSelector.getSelectedDays().iterator();
            while (it2.hasNext()) {
                updateSelectedStateForDate(materialCalendarGridView, it2.next().longValue());
            }
            this.previouslySelectedDates = this.dateSelector.getSelectedDays();
        }
    }

    public boolean withinMonth(int i2) {
        return i2 >= firstPositionInMonth() && i2 <= lastPositionInMonth();
    }

    @Override // android.widget.Adapter
    @Nullable
    public Long getItem(int i2) {
        if (i2 < this.month.daysFromStartOfWeekToFirstOfMonth() || i2 > lastPositionInMonth()) {
            return null;
        }
        return Long.valueOf(this.month.getDay(positionToDay(i2)));
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0075  */
    @Override // android.widget.Adapter
    @androidx.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.widget.TextView getView(int r6, @androidx.annotation.Nullable android.view.View r7, @androidx.annotation.NonNull android.view.ViewGroup r8) {
        /*
            r5 = this;
            android.content.Context r0 = r8.getContext()
            r5.initializeStyles(r0)
            r0 = r7
            android.widget.TextView r0 = (android.widget.TextView) r0
            r1 = 0
            if (r7 != 0) goto L1e
            android.content.Context r7 = r8.getContext()
            android.view.LayoutInflater r7 = android.view.LayoutInflater.from(r7)
            int r0 = com.google.android.material.R.layout.mtrl_calendar_day
            android.view.View r7 = r7.inflate(r0, r8, r1)
            r0 = r7
            android.widget.TextView r0 = (android.widget.TextView) r0
        L1e:
            int r7 = r5.firstPositionInMonth()
            int r7 = r6 - r7
            if (r7 < 0) goto L75
            com.google.android.material.datepicker.Month r8 = r5.month
            int r2 = r8.daysInMonth
            if (r7 < r2) goto L2d
            goto L75
        L2d:
            r2 = 1
            int r7 = r7 + r2
            r0.setTag(r8)
            android.content.res.Resources r8 = r0.getResources()
            android.content.res.Configuration r8 = r8.getConfiguration()
            java.util.Locale r8 = r8.locale
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.Integer r4 = java.lang.Integer.valueOf(r7)
            r3[r1] = r4
            java.lang.String r4 = "%d"
            java.lang.String r8 = java.lang.String.format(r8, r4, r3)
            r0.setText(r8)
            com.google.android.material.datepicker.Month r8 = r5.month
            long r7 = r8.getDay(r7)
            com.google.android.material.datepicker.Month r3 = r5.month
            int r3 = r3.year
            com.google.android.material.datepicker.Month r4 = com.google.android.material.datepicker.Month.current()
            int r4 = r4.year
            if (r3 != r4) goto L67
            java.lang.String r7 = com.google.android.material.datepicker.DateStrings.getMonthDayOfWeekDay(r7)
            r0.setContentDescription(r7)
            goto L6e
        L67:
            java.lang.String r7 = com.google.android.material.datepicker.DateStrings.getYearMonthDayOfWeekDay(r7)
            r0.setContentDescription(r7)
        L6e:
            r0.setVisibility(r1)
            r0.setEnabled(r2)
            goto L7d
        L75:
            r7 = 8
            r0.setVisibility(r7)
            r0.setEnabled(r1)
        L7d:
            java.lang.Long r6 = r5.getItem(r6)
            if (r6 != 0) goto L84
            return r0
        L84:
            long r6 = r6.longValue()
            r5.updateSelectedState(r0, r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.datepicker.MonthAdapter.getView(int, android.view.View, android.view.ViewGroup):android.widget.TextView");
    }
}
