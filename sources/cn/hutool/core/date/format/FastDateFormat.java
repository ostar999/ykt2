package cn.hutool.core.date.format;

import cn.hutool.core.text.StrPool;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class FastDateFormat extends Format implements DateParser, DatePrinter {
    private static final FormatCache<FastDateFormat> CACHE = new FormatCache<FastDateFormat>() { // from class: cn.hutool.core.date.format.FastDateFormat.1
        @Override // cn.hutool.core.date.format.FormatCache
        public FastDateFormat createInstance(String str, TimeZone timeZone, Locale locale) {
            return new FastDateFormat(str, timeZone, locale);
        }
    };
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    private static final long serialVersionUID = 8097890768636183236L;
    private final FastDateParser parser;
    private final FastDatePrinter printer;

    public FastDateFormat(String str, TimeZone timeZone, Locale locale) {
        this(str, timeZone, locale, null);
    }

    public static FastDateFormat getDateInstance(int i2) {
        return (FastDateFormat) CACHE.getDateInstance(i2, null, null);
    }

    public static FastDateFormat getDateTimeInstance(int i2, int i3) {
        return (FastDateFormat) CACHE.getDateTimeInstance(Integer.valueOf(i2), Integer.valueOf(i3), null, null);
    }

    public static FastDateFormat getInstance() {
        return (FastDateFormat) CACHE.getInstance();
    }

    public static FastDateFormat getTimeInstance(int i2) {
        return (FastDateFormat) CACHE.getTimeInstance(i2, null, null);
    }

    public boolean equals(Object obj) {
        if (obj instanceof FastDateFormat) {
            return this.printer.equals(((FastDateFormat) obj).printer);
        }
        return false;
    }

    @Override // java.text.Format
    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        stringBuffer.append(this.printer.format(obj));
        return stringBuffer;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        DateTimeFormatter dateTimeFormatterOfPattern = DateTimeFormatter.ofPattern(getPattern());
        if (getLocale() != null) {
            dateTimeFormatterOfPattern = dateTimeFormatterOfPattern.withLocale(getLocale());
        }
        return getTimeZone() != null ? dateTimeFormatterOfPattern.withZone(getTimeZone().toZoneId()) : dateTimeFormatterOfPattern;
    }

    @Override // cn.hutool.core.date.format.DateBasic
    public Locale getLocale() {
        return this.printer.getLocale();
    }

    public int getMaxLengthEstimate() {
        return this.printer.getMaxLengthEstimate();
    }

    @Override // cn.hutool.core.date.format.DateBasic
    public String getPattern() {
        return this.printer.getPattern();
    }

    @Override // cn.hutool.core.date.format.DateBasic
    public TimeZone getTimeZone() {
        return this.printer.getTimeZone();
    }

    public int hashCode() {
        return this.printer.hashCode();
    }

    @Override // cn.hutool.core.date.format.DateParser
    public Date parse(String str) throws ParseException {
        return this.parser.parse(str);
    }

    @Override // java.text.Format, cn.hutool.core.date.format.DateParser
    public Object parseObject(String str, ParsePosition parsePosition) {
        return this.parser.parseObject(str, parsePosition);
    }

    public String toString() {
        return "FastDateFormat[" + this.printer.getPattern() + "," + this.printer.getLocale() + "," + this.printer.getTimeZone().getID() + StrPool.BRACKET_END;
    }

    public FastDateFormat(String str, TimeZone timeZone, Locale locale, Date date) {
        this.printer = new FastDatePrinter(str, timeZone, locale);
        this.parser = new FastDateParser(str, timeZone, locale, date);
    }

    public static FastDateFormat getDateInstance(int i2, Locale locale) {
        return (FastDateFormat) CACHE.getDateInstance(i2, null, locale);
    }

    public static FastDateFormat getDateTimeInstance(int i2, int i3, Locale locale) {
        return (FastDateFormat) CACHE.getDateTimeInstance(Integer.valueOf(i2), Integer.valueOf(i3), null, locale);
    }

    public static FastDateFormat getInstance(String str) {
        return (FastDateFormat) CACHE.getInstance(str, null, null);
    }

    public static FastDateFormat getTimeInstance(int i2, Locale locale) {
        return (FastDateFormat) CACHE.getTimeInstance(i2, null, locale);
    }

    @Override // cn.hutool.core.date.format.DatePrinter
    public String format(long j2) {
        return this.printer.format(j2);
    }

    @Override // cn.hutool.core.date.format.DateParser
    public Date parse(String str, ParsePosition parsePosition) {
        return this.parser.parse(str, parsePosition);
    }

    public static FastDateFormat getDateInstance(int i2, TimeZone timeZone) {
        return (FastDateFormat) CACHE.getDateInstance(i2, timeZone, null);
    }

    public static FastDateFormat getDateTimeInstance(int i2, int i3, TimeZone timeZone) {
        return getDateTimeInstance(i2, i3, timeZone, null);
    }

    public static FastDateFormat getInstance(String str, TimeZone timeZone) {
        return (FastDateFormat) CACHE.getInstance(str, timeZone, null);
    }

    public static FastDateFormat getTimeInstance(int i2, TimeZone timeZone) {
        return (FastDateFormat) CACHE.getTimeInstance(i2, timeZone, null);
    }

    @Override // cn.hutool.core.date.format.DatePrinter
    public String format(Date date) {
        return this.printer.format(date);
    }

    @Override // cn.hutool.core.date.format.DateParser
    public boolean parse(String str, ParsePosition parsePosition, Calendar calendar) {
        return this.parser.parse(str, parsePosition, calendar);
    }

    public static FastDateFormat getDateInstance(int i2, TimeZone timeZone, Locale locale) {
        return (FastDateFormat) CACHE.getDateInstance(i2, timeZone, locale);
    }

    public static FastDateFormat getDateTimeInstance(int i2, int i3, TimeZone timeZone, Locale locale) {
        return (FastDateFormat) CACHE.getDateTimeInstance(Integer.valueOf(i2), Integer.valueOf(i3), timeZone, locale);
    }

    public static FastDateFormat getInstance(String str, Locale locale) {
        return (FastDateFormat) CACHE.getInstance(str, null, locale);
    }

    public static FastDateFormat getTimeInstance(int i2, TimeZone timeZone, Locale locale) {
        return (FastDateFormat) CACHE.getTimeInstance(i2, timeZone, locale);
    }

    @Override // cn.hutool.core.date.format.DatePrinter
    public String format(Calendar calendar) {
        return this.printer.format(calendar);
    }

    public static FastDateFormat getInstance(String str, TimeZone timeZone, Locale locale) {
        return (FastDateFormat) CACHE.getInstance(str, timeZone, locale);
    }

    @Override // cn.hutool.core.date.format.DatePrinter
    public <B extends Appendable> B format(long j2, B b3) {
        return (B) this.printer.format(j2, (long) b3);
    }

    @Override // cn.hutool.core.date.format.DatePrinter
    public <B extends Appendable> B format(Date date, B b3) {
        return (B) this.printer.format(date, (Date) b3);
    }

    @Override // cn.hutool.core.date.format.DatePrinter
    public <B extends Appendable> B format(Calendar calendar, B b3) {
        return (B) this.printer.format(calendar, (Calendar) b3);
    }
}
