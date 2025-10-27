package cn.hutool.core.date;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.lang.Assert;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/* loaded from: classes.dex */
public class DateBetween implements Serializable {
    private static final long serialVersionUID = 1;
    private final Date begin;
    private final Date end;

    public DateBetween(Date date, Date date2) {
        this(date, date2, true);
    }

    public static DateBetween create(Date date, Date date2) {
        return new DateBetween(date, date2);
    }

    public long between(DateUnit dateUnit) {
        return (this.end.getTime() - this.begin.getTime()) / dateUnit.getMillis();
    }

    public long betweenMonth(boolean z2) {
        Calendar calendar = CalendarUtil.calendar(this.begin);
        Calendar calendar2 = CalendarUtil.calendar(this.end);
        int i2 = ((calendar2.get(1) - calendar.get(1)) * 12) + (calendar2.get(2) - calendar.get(2));
        if (!z2) {
            calendar2.set(1, calendar.get(1));
            calendar2.set(2, calendar.get(2));
            if (calendar2.getTimeInMillis() - calendar.getTimeInMillis() < 0) {
                return i2 - 1;
            }
        }
        return i2;
    }

    public long betweenYear(boolean z2) {
        Calendar calendar = CalendarUtil.calendar(this.begin);
        Calendar calendar2 = CalendarUtil.calendar(this.end);
        int i2 = calendar2.get(1) - calendar.get(1);
        if (!z2) {
            if (1 == calendar.get(2) && 1 == calendar2.get(2) && calendar.get(5) == calendar.getActualMaximum(5) && calendar2.get(5) == calendar2.getActualMaximum(5)) {
                calendar.set(5, 1);
                calendar2.set(5, 1);
            }
            calendar2.set(1, calendar.get(1));
            if (calendar2.getTimeInMillis() - calendar.getTimeInMillis() < 0) {
                return i2 - 1;
            }
        }
        return i2;
    }

    public String toString(DateUnit dateUnit, BetweenFormatter.Level level) {
        return DateUtil.formatBetween(between(dateUnit), level);
    }

    public DateBetween(Date date, Date date2, boolean z2) throws IllegalArgumentException {
        Assert.notNull(date, "Begin date is null !", new Object[0]);
        Assert.notNull(date2, "End date is null !", new Object[0]);
        if (z2 && date.after(date2)) {
            this.begin = date2;
            this.end = date;
        } else {
            this.begin = date;
            this.end = date2;
        }
    }

    public static DateBetween create(Date date, Date date2, boolean z2) {
        return new DateBetween(date, date2, z2);
    }

    public String toString(BetweenFormatter.Level level) {
        return toString(DateUnit.MS, level);
    }

    public String toString() {
        return toString(BetweenFormatter.Level.MILLISECOND);
    }
}
