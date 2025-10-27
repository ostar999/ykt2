package cn.hutool.core.date.format;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.SafeConcurrentHashMap;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/* loaded from: classes.dex */
public class GlobalCustomFormat {
    public static final String FORMAT_MILLISECONDS = "#SSS";
    public static final String FORMAT_SECONDS = "#sss";
    private static final Map<CharSequence, Function<Date, String>> formatterMap = new SafeConcurrentHashMap();
    private static final Map<CharSequence, Function<CharSequence, Date>> parserMap = new SafeConcurrentHashMap();

    static {
        putFormatter(FORMAT_SECONDS, new Function() { // from class: cn.hutool.core.date.format.g
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return GlobalCustomFormat.lambda$static$0((Date) obj);
            }
        });
        putParser(FORMAT_SECONDS, new Function() { // from class: cn.hutool.core.date.format.h
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return GlobalCustomFormat.lambda$static$1((CharSequence) obj);
            }
        });
        putFormatter(FORMAT_MILLISECONDS, new Function() { // from class: cn.hutool.core.date.format.i
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return GlobalCustomFormat.lambda$static$2((Date) obj);
            }
        });
        putParser(FORMAT_MILLISECONDS, new Function() { // from class: cn.hutool.core.date.format.j
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return GlobalCustomFormat.lambda$static$3((CharSequence) obj);
            }
        });
    }

    public static String format(Date date, CharSequence charSequence) {
        Function<Date, String> function;
        Map<CharSequence, Function<Date, String>> map = formatterMap;
        if (map == null || (function = map.get(charSequence)) == null) {
            return null;
        }
        return (String) function.apply(date);
    }

    public static boolean isCustomFormat(String str) {
        return formatterMap.containsKey(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$static$0(Date date) {
        return String.valueOf(e.a(date.getTime(), 1000L));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Date lambda$static$1(CharSequence charSequence) {
        return DateUtil.date(f.a(Long.parseLong(charSequence.toString()), 1000L));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$static$2(Date date) {
        return String.valueOf(date.getTime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Date lambda$static$3(CharSequence charSequence) {
        return DateUtil.date(Long.parseLong(charSequence.toString()));
    }

    public static Date parse(CharSequence charSequence, String str) {
        Function<CharSequence, Date> function;
        Map<CharSequence, Function<CharSequence, Date>> map = parserMap;
        if (map == null || (function = map.get(str)) == null) {
            return null;
        }
        return (Date) function.apply(charSequence);
    }

    public static void putFormatter(String str, Function<Date, String> function) throws IllegalArgumentException {
        Assert.notNull(str, "Format must be not null !", new Object[0]);
        Assert.notNull(function, "Function must be not null !", new Object[0]);
        formatterMap.put(str, function);
    }

    public static void putParser(String str, Function<CharSequence, Date> function) throws IllegalArgumentException {
        Assert.notNull(str, "Format must be not null !", new Object[0]);
        Assert.notNull(function, "Function must be not null !", new Object[0]);
        parserMap.put(str, function);
    }

    public static String format(TemporalAccessor temporalAccessor, CharSequence charSequence) {
        return format(DateUtil.date(temporalAccessor), charSequence);
    }
}
