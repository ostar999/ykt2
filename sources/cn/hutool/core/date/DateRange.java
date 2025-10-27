package cn.hutool.core.date;

import cn.hutool.core.lang.Range;
import java.util.Date;

/* loaded from: classes.dex */
public class DateRange extends Range<DateTime> {
    private static final long serialVersionUID = 1;

    public DateRange(Date date, Date date2, DateField dateField) {
        this(date, date2, dateField, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ DateTime lambda$new$0(Date date, DateField dateField, int i2, DateTime dateTime, DateTime dateTime2, int i3) {
        DateTime dateTimeOffsetNew = DateUtil.date(date).offsetNew(dateField, (i3 + 1) * i2);
        if (dateTimeOffsetNew.isAfter(dateTime2)) {
            return null;
        }
        return dateTimeOffsetNew;
    }

    public DateRange(Date date, Date date2, DateField dateField, int i2) {
        this(date, date2, dateField, i2, true, true);
    }

    public DateRange(final Date date, Date date2, final DateField dateField, final int i2, boolean z2, boolean z3) {
        super(DateUtil.date(date), DateUtil.date(date2), new Range.Stepper() { // from class: cn.hutool.core.date.h
            @Override // cn.hutool.core.lang.Range.Stepper
            public final Object step(Object obj, Object obj2, int i3) {
                return DateRange.lambda$new$0(date, dateField, i2, (DateTime) obj, (DateTime) obj2, i3);
            }
        }, z2, z3);
    }
}
