package cn.hutool.core.date.format;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Tuple;
import cn.hutool.core.map.SafeConcurrentHashMap;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes.dex */
abstract class FormatCache<F extends Format> {
    private static final ConcurrentMap<Tuple, String> C_DATE_TIME_INSTANCE_CACHE = new SafeConcurrentHashMap(7);
    static final int NONE = -1;
    private final ConcurrentMap<Tuple, F> cInstanceCache = new SafeConcurrentHashMap(7);

    public static String getPatternForStyle(Integer num, Integer num2, Locale locale) {
        Tuple tuple = new Tuple(num, num2, locale);
        ConcurrentMap<Tuple, String> concurrentMap = C_DATE_TIME_INSTANCE_CACHE;
        String str = concurrentMap.get(tuple);
        if (str != null) {
            return str;
        }
        try {
            String pattern = ((SimpleDateFormat) (num == null ? DateFormat.getTimeInstance(num2.intValue(), locale) : num2 == null ? DateFormat.getDateInstance(num.intValue(), locale) : DateFormat.getDateTimeInstance(num.intValue(), num2.intValue(), locale))).toPattern();
            String strPutIfAbsent = concurrentMap.putIfAbsent(tuple, pattern);
            return strPutIfAbsent != null ? strPutIfAbsent : pattern;
        } catch (ClassCastException unused) {
            throw new IllegalArgumentException("No date time pattern for locale: " + locale);
        }
    }

    public abstract F createInstance(String str, TimeZone timeZone, Locale locale);

    public F getDateInstance(int i2, TimeZone timeZone, Locale locale) {
        return (F) getDateTimeInstance(Integer.valueOf(i2), null, timeZone, locale);
    }

    public F getDateTimeInstance(Integer num, Integer num2, TimeZone timeZone, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return (F) getInstance(getPatternForStyle(num, num2, locale), timeZone, locale);
    }

    public F getInstance() {
        return (F) getDateTimeInstance(3, 3, null, null);
    }

    public F getTimeInstance(int i2, TimeZone timeZone, Locale locale) {
        return (F) getDateTimeInstance(null, Integer.valueOf(i2), timeZone, locale);
    }

    public F getInstance(String str, TimeZone timeZone, Locale locale) throws IllegalArgumentException {
        Assert.notBlank(str, "pattern must not be blank", new Object[0]);
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        Tuple tuple = new Tuple(str, timeZone, locale);
        F f2 = this.cInstanceCache.get(tuple);
        if (f2 != null) {
            return f2;
        }
        F f3 = (F) createInstance(str, timeZone, locale);
        F f4 = (F) this.cInstanceCache.putIfAbsent(tuple, f3);
        return f4 != null ? f4 : f3;
    }
}
