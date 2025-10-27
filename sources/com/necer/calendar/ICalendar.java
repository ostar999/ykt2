package com.necer.calendar;

import com.necer.enumeration.CalendarState;
import com.necer.enumeration.CheckModel;
import com.necer.enumeration.MultipleCountModel;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.listener.OnCalendarMultipleChangedListener;
import com.necer.listener.OnCalendarStateChangedListener;
import com.necer.listener.OnClickDisableDateListener;
import com.necer.painter.CalendarPainter;
import java.time.LocalDate;
import java.util.List;

/* loaded from: classes4.dex */
public interface ICalendar {
    CalendarPainter getCalendarPainter();

    CalendarState getCalendarState();

    CheckModel getCheckModel();

    List<LocalDate> getCurrPagerCheckDateList();

    List<LocalDate> getCurrPagerDateList();

    List<LocalDate> getTotalCheckedDateList();

    void jumpDate(int i2, int i3, int i4);

    void jumpDate(String str);

    void jumpMonth(int i2, int i3);

    void notifyCalendar();

    void setCalendarPainter(CalendarPainter calendarPainter);

    void setCheckMode(CheckModel checkModel);

    void setCheckedDates(List<String> list);

    void setDateInterval(String str, String str2);

    void setDateInterval(String str, String str2, String str3);

    void setInitializeDate(String str);

    void setMultipleCount(int i2, MultipleCountModel multipleCountModel);

    void setOnCalendarChangedListener(OnCalendarChangedListener onCalendarChangedListener);

    void setOnCalendarMultipleChangedListener(OnCalendarMultipleChangedListener onCalendarMultipleChangedListener);

    void setOnCalendarStateChangedListener(OnCalendarStateChangedListener onCalendarStateChangedListener);

    void setOnClickDisableDateListener(OnClickDisableDateListener onClickDisableDateListener);

    void setWeekHoldEnable(boolean z2);

    void toLastPager();

    void toMonth();

    void toNextPager();

    void toStretch();

    void toToday();

    void toWeek();
}
