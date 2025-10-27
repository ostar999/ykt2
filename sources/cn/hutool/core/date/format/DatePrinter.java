package cn.hutool.core.date.format;

import java.util.Calendar;
import java.util.Date;

/* loaded from: classes.dex */
public interface DatePrinter extends DateBasic {
    <B extends Appendable> B format(long j2, B b3);

    <B extends Appendable> B format(Calendar calendar, B b3);

    <B extends Appendable> B format(Date date, B b3);

    String format(long j2);

    String format(Calendar calendar);

    String format(Date date);
}
